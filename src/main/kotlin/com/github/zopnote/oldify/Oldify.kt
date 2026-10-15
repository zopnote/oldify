package com.github.zopnote.oldify

import com.github.zopnote.oldify.decay.DecayEngine
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin

class Oldify : JavaPlugin() {

    override fun onEnable() {
        logger.info("[Oldify] Plugin aktiviert - Zerfallssimulation wird gestartet...")

        // Verarbeite nur die Oberwelt (Environment.NORMAL)
        val overworld = server.worlds.firstOrNull { it.environment == World.Environment.NORMAL }

        if (overworld == null) {
            logger.severe("[Oldify] Keine Oberwelt gefunden! Plugin deaktiviert.")
            server.pluginManager.disablePlugin(this)
            return
        }

        logger.info("[Oldify] Oberwelt gefunden: ${overworld.name}")
        logger.info("[Oldify] Starte Zerfallsprozess in 5 Sekunden...")

        // CI (
        // Kurze Verzögerung damit der Server vollständig hochgefahren ist
        // und alle Chunks/Spieler geladen sind bevor wir beginnen
        // CI )
        server.scheduler.runTaskLater(this, Runnable {
            val engine = DecayEngine(this, overworld)
            engine.start().thenRun {
                logger.info("[Oldify] Zerfallsprozess vollständig abgeschlossen.")
                logger.info("[Oldify] Das Plugin kann nun sicher deaktiviert werden.")
                logger.info("[Oldify] Empfehlung: Welt speichern mit /save-all")
            }
        }, 100L) // 5 Sekunden = 100 Ticks
    }

    override fun onDisable() {
        logger.info("[Oldify] Plugin deaktiviert.")
    }
}
