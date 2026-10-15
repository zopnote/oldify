package com.github.zopnote.oldify.decay

import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.block.Block
import java.util.concurrent.ThreadLocalRandom

/**
 * Verarbeitet einen einzelnen Chunk und wendet die Zerfallsalgorithmen an.
 *
 * Algorithmen:
 * 1. 20% Chance: Spinnennetz in angrenzendem Luftblock platzieren
 * 2. 15% Chance: Block komplett entfernen
 * 3. 30% Chance: Block durch Zerfallsprodukt ersetzen
 *    - Bei Blöcken mit Luft darunter: Schwerkraft-Simulation statt Zerfallsprodukt
 *    - Bei Blöcken ohne spezifisches Mapping: 50% entfernen / 50% generischer Zerfall
 */
object DecayProcessor {

    data class ChunkStats(
        val nonNaturalFound: Int,
        val cobwebsPlaced: Int,
        val blocksDestroyed: Int,
        val blocksDecayed: Int,
        val blocksDropped: Int
    )

    fun processChunk(chunk: Chunk): ChunkStats {
        val world = chunk.world
        val minY = world.minHeight
        val maxY = world.maxHeight
        val random = ThreadLocalRandom.current()

        var nonNaturalFound = 0
        var cobwebsPlaced = 0
        var blocksDestroyed = 0
        var blocksDecayed = 0
        var blocksDropped = 0

        // CI (
        // Wir sammeln alle Änderungen zuerst und wenden sie danach an,
        // damit die Zerfallslogik nicht von bereits veränderten Nachbarblöcken
        // beeinflusst wird (ein Durchgang = ein konsistenter Snapshot).
        // CI )
        data class BlockChange(val x: Int, val y: Int, val z: Int, val newMaterial: Material)
        val changes = mutableListOf<BlockChange>()

        val baseX = chunk.x shl 4
        val baseZ = chunk.z shl 4

        for (x in 0 until 16) {
            for (z in 0 until 16) {
                for (y in minY until maxY) {
                    val block = chunk.getBlock(x, y, z)
                    val material = block.type

                    if (!DecayMappings.isNonNatural(material)) continue
                    nonNaturalFound++

                    val worldX = baseX + x
                    val worldZ = baseZ + z

                    // --- Algorithmus 1: 20% Spinnennetz in angrenzendem Luftblock ---
                    if (random.nextDouble() < 0.20) {
                        val adjacentAir = findAdjacentAirBlock(block, minY, maxY)
                        if (adjacentAir != null) {
                            changes.add(BlockChange(adjacentAir.x, adjacentAir.y, adjacentAir.z, Material.COBWEB))
                            cobwebsPlaced++
                        }
                    }

                    // --- Algorithmus 2: 15% Block komplett zerstören ---
                    if (random.nextDouble() < 0.15) {
                        changes.add(BlockChange(worldX, y, worldZ, Material.AIR))
                        blocksDestroyed++
                        continue // Block ist weg, kein weiterer Zerfall nötig
                    }

                    // --- Algorithmus 3: 30% Zerfallsprodukt ---
                    if (random.nextDouble() < 0.30) {
                        // Prüfe ob Luft unter dem Block ist (Schwerkraft-Simulation)
                        val hasAirBelow = hasAirBlockBelow(block, minY)

                        if (hasAirBelow) {
                            // Schwerkraft: Block entfernen und auf dem Boden platzieren
                            val groundY = findGroundLevel(world, worldX, y, worldZ, minY)
                            changes.add(BlockChange(worldX, y, worldZ, Material.AIR))
                            if (groundY != y) {
                                changes.add(BlockChange(worldX, groundY, worldZ, material))
                            }
                            blocksDropped++
                        } else {
                            // Normaler Zerfall
                            val decayProduct = DecayMappings.DECAY_PRODUCTS[material]
                            if (decayProduct != null) {
                                changes.add(BlockChange(worldX, y, worldZ, decayProduct))
                                blocksDecayed++
                            } else {
                                // CI (
                                // Kein spezifisches Zerfallsprodukt vorhanden:
                                // 50% komplett entfernen, 50% generischer Zerfallsblock
                                // CI )
                                if (random.nextBoolean()) {
                                    changes.add(BlockChange(worldX, y, worldZ, Material.AIR))
                                    blocksDestroyed++
                                } else {
                                    changes.add(BlockChange(worldX, y, worldZ, DecayMappings.GENERIC_DECAY_BLOCK))
                                    blocksDecayed++
                                }
                            }
                        }
                    }
                }
            }
        }

        // Änderungen anwenden
        for (change in changes) {
            val block = world.getBlockAt(change.x, change.y, change.z)
            block.setType(change.newMaterial, false)
        }

        return ChunkStats(
            nonNaturalFound = nonNaturalFound,
            cobwebsPlaced = cobwebsPlaced,
            blocksDestroyed = blocksDestroyed,
            blocksDecayed = blocksDecayed,
            blocksDropped = blocksDropped
        )
    }

    /**
     * Findet einen zufälligen angrenzenden Luftblock (6 Richtungen).
     */
    private fun findAdjacentAirBlock(block: Block, minY: Int, maxY: Int): Block? {
        val random = ThreadLocalRandom.current()
        val offsets = listOf(
            Triple(1, 0, 0), Triple(-1, 0, 0),
            Triple(0, 1, 0), Triple(0, -1, 0),
            Triple(0, 0, 1), Triple(0, 0, -1)
        )

        val airBlocks = offsets.mapNotNull { (dx, dy, dz) ->
            val ny = block.y + dy
            if (ny < minY || ny >= maxY) return@mapNotNull null
            val neighbor = block.world.getBlockAt(block.x + dx, ny, block.z + dz)
            if (neighbor.type.isAir) neighbor else null
        }

        return if (airBlocks.isNotEmpty()) airBlocks[random.nextInt(airBlocks.size)] else null
    }

    /**
     * Prüft ob sich mindestens ein Luftblock direkt unter dem Block befindet
     * (zwischen Block und nächstem soliden Block darunter).
     */
    private fun hasAirBlockBelow(block: Block, minY: Int): Boolean {
        val world = block.world
        var y = block.y - 1
        while (y >= minY) {
            val below = world.getBlockAt(block.x, y, block.z)
            if (below.type.isAir) return true
            if (below.type.isSolid) return false
            y--
        }
        return false
    }

    /**
     * Findet den letzten Luftblock vor dem Boden unter der gegebenen Position.
     * Der Block "fällt" nach unten bis er auf etwas Solides trifft.
     */
    private fun findGroundLevel(world: org.bukkit.World, x: Int, startY: Int, z: Int, minY: Int): Int {
        var y = startY - 1
        var lastAirY = startY

        while (y >= minY) {
            val block = world.getBlockAt(x, y, z)
            if (block.type.isAir) {
                lastAirY = y
            } else if (block.type.isSolid) {
                // CI (
                // Der Block soll auf dem letzten Luftblock vor dem Boden landen,
                // also direkt über dem soliden Block
                // CI )
                return y + 1
            }
            y--
        }

        return lastAirY
    }
}
