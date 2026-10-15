package com.github.zopnote.oldify.decay

import org.bukkit.World
import java.io.File
import java.io.RandomAccessFile
import java.util.logging.Logger

/**
 * Scannt die Regiondateien einer Welt um alle existierenden Chunk-Koordinaten zu finden.
 * Liest die MCA-Header ohne die Chunks selbst zu laden.
 */
object WorldScanner {

    data class ChunkCoord(val x: Int, val z: Int)

    /**
     * Findet alle existierenden Chunks in der angegebenen Welt durch
     * Scannen der MCA-Regiondateien.
     *
     * MCA-Format: Die ersten 4096 Bytes sind die Location-Tabelle.
     * Jeder Chunk hat einen 4-Byte-Eintrag. Wenn der Eintrag != 0 ist,
     * existiert der Chunk.
     */
    fun scanWorld(world: World, logger: Logger): List<ChunkCoord> {
        val regionDir = File(world.worldFolder, "region")
        if (!regionDir.exists() || !regionDir.isDirectory) {
            logger.warning("[Oldify] Region-Verzeichnis nicht gefunden: ${regionDir.absolutePath}")
            return emptyList()
        }

        val regionFiles = regionDir.listFiles { file ->
            file.name.endsWith(".mca") && file.length() > 0
        } ?: return emptyList()

        logger.info("[Oldify] ${regionFiles.size} Regiondateien gefunden in ${regionDir.absolutePath}")

        val chunks = mutableListOf<ChunkCoord>()

        for (regionFile in regionFiles) {
            val parts = regionFile.nameWithoutExtension.split(".")
            if (parts.size != 3 || parts[0] != "r") continue

            val regionX = parts[1].toIntOrNull() ?: continue
            val regionZ = parts[2].toIntOrNull() ?: continue

            try {
                val regionChunks = scanRegionFile(regionFile, regionX, regionZ)
                chunks.addAll(regionChunks)
            } catch (e: Exception) {
                logger.warning("[Oldify] Fehler beim Scannen von ${regionFile.name}: ${e.message}")
            }
        }

        logger.info("[Oldify] ${chunks.size} Chunks in ${regionFiles.size} Regiondateien gefunden")
        return chunks
    }

    private fun scanRegionFile(file: File, regionX: Int, regionZ: Int): List<ChunkCoord> {
        val chunks = mutableListOf<ChunkCoord>()

        RandomAccessFile(file, "r").use { raf ->
            if (raf.length() < 4096) return chunks

            val header = ByteArray(4096)
            raf.readFully(header)

            for (localZ in 0 until 32) {
                for (localX in 0 until 32) {
                    val index = (localX + localZ * 32) * 4
                    val offset = ((header[index].toInt() and 0xFF) shl 16) or
                            ((header[index + 1].toInt() and 0xFF) shl 8) or
                            (header[index + 2].toInt() and 0xFF)
                    val sectorCount = header[index + 3].toInt() and 0xFF

                    if (offset != 0 && sectorCount != 0) {
                        val chunkX = regionX * 32 + localX
                        val chunkZ = regionZ * 32 + localZ
                        chunks.add(ChunkCoord(chunkX, chunkZ))
                    }
                }
            }
        }

        return chunks
    }
}
