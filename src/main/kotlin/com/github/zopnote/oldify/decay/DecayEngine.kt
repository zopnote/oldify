package com.github.zopnote.oldify.decay

import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.CompletableFuture
import java.util.logging.Logger

/**
 * Orchestriert den gesamten Zerfallsprozess:
 * 1. Scannt alle existierenden Chunks
 * 2. Verarbeitet jeden Chunk einzeln
 * 3. Zeigt detaillierte Fortschrittsanzeige im Terminal
 * 4. Berechnet durchschnittliche Verarbeitungszeit und ETA
 */
class DecayEngine(
    private val plugin: JavaPlugin,
    private val world: World
) {
    private val logger: Logger = plugin.logger

    // Konfiguration
    companion object {
        /** Anzahl der Chunks die pro Tick verarbeitet werden */
        const val CHUNKS_PER_TICK = 5

        /** Alle X Chunks wird eine Fortschrittsmeldung ausgegeben */
        const val PROGRESS_INTERVAL = 100

        /** Fortschrittsbalken-Breite in Zeichen */
        const val PROGRESS_BAR_WIDTH = 40
    }

    // Statistiken
    private var totalChunks = 0
    private var processedChunks = 0
    private var skippedChunks = 0
    private var totalNonNatural = 0L
    private var totalCobwebs = 0L
    private var totalDestroyed = 0L
    private var totalDecayed = 0L
    private var totalDropped = 0L
    private var startTime = 0L
    private var chunkTimes = mutableListOf<Long>()

    /**
     * Startet den Zerfallsprozess asynchron.
     * Scannt zuerst alle Regiondateien, dann verarbeitet Chunks in Batches.
     */
    fun start(): CompletableFuture<Void> {
        val future = CompletableFuture<Void>()

        logger.info("╔══════════════════════════════════════════════════════╗")
        logger.info("║           OLDIFY - Weltzerfallssimulation           ║")
        logger.info("╠════════════════════════════════════════════════��═════╣")
        logger.info("║  Welt: ${padRight(world.name, 44)} ║")
        logger.info("║  Phase 1: Regiondateien scannen...                 ║")
        logger.info("╚══════════════════════════════════════════════════════╝")

        // Phase 1: Regiondateien scannen (async im Thread-Pool)
        CompletableFuture.supplyAsync {
            WorldScanner.scanWorld(world, logger)
        }.thenAccept { chunks ->
            // Zurück auf Main Thread für Phase 2
            object : BukkitRunnable() {
                override fun run() {
                    startProcessing(chunks, future)
                }
            }.runTask(plugin)
        }.exceptionally { e ->
            logger.severe("[Oldify] Fehler beim Scannen: ${e.message}")
            future.completeExceptionally(e)
            null
        }

        return future
    }

    private fun startProcessing(chunks: List<WorldScanner.ChunkCoord>, future: CompletableFuture<Void>) {
        totalChunks = chunks.size
        startTime = System.currentTimeMillis()

        logger.info("╔══════════════════════════════════════════════════════╗")
        logger.info("║  Phase 2: Zerfallsprozess starten                  ║")
        logger.info("║  Zu verarbeitende Chunks: ${padRight(formatNumber(totalChunks), 27)} ║")
        logger.info("║  Chunks pro Tick: ${padRight(CHUNKS_PER_TICK.toString(), 34)} ║")
        logger.info("╚══════════════════════════════════════════════════════╝")

        val chunkIterator = chunks.iterator()

        object : BukkitRunnable() {
            override fun run() {
                val tickStart = System.currentTimeMillis()
                var chunksThisTick = 0

                while (chunkIterator.hasNext() && chunksThisTick < CHUNKS_PER_TICK) {
                    val coord = chunkIterator.next()
                    val chunkStart = System.nanoTime()

                    try {
                        processChunkAt(coord)
                    } catch (e: Exception) {
                        logger.warning("[Oldify] Fehler bei Chunk (${coord.x}, ${coord.z}): ${e.message}")
                    }

                    val chunkDuration = (System.nanoTime() - chunkStart) / 1_000_000
                    chunkTimes.add(chunkDuration)
                    // Begrenze gespeicherte Zeiten für Durchschnittsberechnung
                    if (chunkTimes.size > 1000) {
                        chunkTimes = chunkTimes.drop(500).toMutableList()
                    }

                    processedChunks++
                    chunksThisTick++

                    if (processedChunks % PROGRESS_INTERVAL == 0 || processedChunks == totalChunks) {
                        printProgress()
                    }
                }

                if (!chunkIterator.hasNext()) {
                    cancel()
                    printFinalReport()
                    future.complete(null)
                }
            }
        }.runTaskTimer(plugin, 1L, 1L)
    }

    private fun processChunkAt(coord: WorldScanner.ChunkCoord) {
        // Lade den Chunk synchron (wir sind bereits auf dem Main Thread)
        val wasLoaded = world.isChunkLoaded(coord.x, coord.z)
        val chunk = world.getChunkAt(coord.x, coord.z)
        chunk.load(true)

        val stats = DecayProcessor.processChunk(chunk)

        if (stats.nonNaturalFound == 0) {
            skippedChunks++
        }

        totalNonNatural += stats.nonNaturalFound
        totalCobwebs += stats.cobwebsPlaced
        totalDestroyed += stats.blocksDestroyed
        totalDecayed += stats.blocksDecayed
        totalDropped += stats.blocksDropped

        // Chunk wieder entladen wenn er vorher nicht geladen war, um Speicher zu sparen
        if (!wasLoaded) {
            chunk.unload(true)
        }
    }

    private fun printProgress() {
        val elapsed = System.currentTimeMillis() - startTime
        val percent = (processedChunks.toDouble() / totalChunks * 100)
        val avgMs = if (chunkTimes.isNotEmpty()) chunkTimes.average() else 0.0
        val remaining = totalChunks - processedChunks
        val etaMs = (remaining * avgMs).toLong()

        val progressBar = buildProgressBar(percent)
        val elapsedStr = formatDuration(elapsed)
        val etaStr = formatDuration(etaMs)

        logger.info("┌─────────────────────────────────────────────────────────────────────┐")
        logger.info("│ $progressBar ${String.format("%6.2f", percent)}%")
        logger.info("│ Chunks: ${formatNumber(processedChunks)} / ${formatNumber(totalChunks)} | Übersprungen (leer): ${formatNumber(skippedChunks)}")
        logger.info("│ Nicht-natürliche Blöcke: ${formatNumber(totalNonNatural)}")
        logger.info("│ Spinnennetze: +${formatNumber(totalCobwebs)} | Zerstört: ${formatNumber(totalDestroyed)} | Zerfallen: ${formatNumber(totalDecayed)} | Gefallen: ${formatNumber(totalDropped)}")
        logger.info("│ Ø ${String.format("%.1f", avgMs)}ms/Chunk | Vergangen: $elapsedStr | ETA: $etaStr")
        logger.info("└─────────────────────────────────────────────────────────────────────┘")
    }

    private fun printFinalReport() {
        val totalTime = System.currentTimeMillis() - startTime
        val avgMs = if (chunkTimes.isNotEmpty()) chunkTimes.average() else 0.0

        logger.info("")
        logger.info("╔══════════════════════════════════════════════════════════════════════╗")
        logger.info("║                 OLDIFY - PROZESS ABGESCHLOSSEN                      ║")
        logger.info("╠══════════════════════════════════════════════════════════════════════╣")
        logger.info("║  Gesamtzeit:                ${padRight(formatDuration(totalTime), 40)} ║")
        logger.info("║  Verarbeitete Chunks:       ${padRight(formatNumber(totalChunks), 40)} ║")
        logger.info("║  Chunks mit Spielerblöcken: ${padRight(formatNumber(totalChunks - skippedChunks), 40)} ║")
        logger.info("║  Leere Chunks übersprungen: ${padRight(formatNumber(skippedChunks), 40)} ║")
        logger.info("║  Durchschnitt pro Chunk:    ${padRight("${String.format("%.1f", avgMs)}ms", 40)} ║")
        logger.info("╠══════════════════════════════════════════════════════════════════════╣")
        logger.info("║  BLOCKSTATISTIKEN                                                   ║")
        logger.info("║  Nicht-natürliche gefunden: ${padRight(formatNumber(totalNonNatural), 40)} ║")
        logger.info("║  Spinnennetze platziert:    ${padRight(formatNumber(totalCobwebs), 40)} ║")
        logger.info("║  Blöcke zerstört:           ${padRight(formatNumber(totalDestroyed), 40)} ║")
        logger.info("║  Blöcke zerfallen:          ${padRight(formatNumber(totalDecayed), 40)} ║")
        logger.info("║  Blöcke gefallen (Gravit.): ${padRight(formatNumber(totalDropped), 40)} ║")
        logger.info("╚══════════════════════════════════════════════════════════════════════╝")
        logger.info("")
    }

    private fun buildProgressBar(percent: Double): String {
        val filled = (percent / 100 * PROGRESS_BAR_WIDTH).toInt().coerceIn(0, PROGRESS_BAR_WIDTH)
        val empty = PROGRESS_BAR_WIDTH - filled
        return "[" + "█".repeat(filled) + "░".repeat(empty) + "]"
    }

    private fun formatDuration(ms: Long): String {
        if (ms < 0) return "Berechne..."
        val seconds = ms / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days > 0 -> "${days}d ${hours % 24}h ${minutes % 60}m ${seconds % 60}s"
            hours > 0 -> "${hours}h ${minutes % 60}m ${seconds % 60}s"
            minutes > 0 -> "${minutes}m ${seconds % 60}s"
            else -> "${seconds}s"
        }
    }

    private fun formatNumber(n: Number): String {
        return String.format("%,d", n.toLong())
    }

    private fun padRight(str: String, length: Int): String {
        return if (str.length >= length) str.take(length)
        else str + " ".repeat(length - str.length)
    }
}
