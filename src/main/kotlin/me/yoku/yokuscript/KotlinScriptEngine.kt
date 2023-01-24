package me.yoku.yokuscript

import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.kotlin.mainKts.jsr223.KotlinJsr223MainKtsScriptEngineFactory
import java.io.File

class KotlinScriptEngine(private val plugin: JavaPlugin) {

    init {

        val file = File("${plugin.dataFolder}/scripts", "test.main.kts")

        val scriptEngine = KotlinJsr223MainKtsScriptEngineFactory().scriptEngine

        scriptEngine.eval(file.readText())

    }

}