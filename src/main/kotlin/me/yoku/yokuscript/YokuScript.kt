package me.yoku.yokuscript

import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.kotlin.mainKts.jsr223.KotlinJsr223MainKtsScriptEngineFactory
import java.io.File
import javax.script.ScriptEngineManager
import kotlin.script.experimental.jsr223.KotlinJsr223DefaultScriptEngineFactory

class YokuScript: JavaPlugin() {

    override fun onEnable() {

        KotlinScriptEngine(this)

    }

    override fun onDisable() {

        logger.info("Yoku Script is Disabled!!")

    }

    /*
    if (!this.dataFolder.exists()) {
            this.dataFolder.mkdir()
            this.dataFolder.setWritable(true)
        }

        val stream = this::class.java.getResourceAsStream("/scripts/test.main.kts");

        if (stream == null) {
            logger.warning("Can't find Scrips")
            return;
        }

        val file = File(this.dataFolder, "scripts/test.main.kts")

        if (!file.exists()) {
            file.parentFile.mkdir();
            file.createNewFile();
        }

        val outputStream = file.outputStream()

        stream.copyTo(outputStream);

        outputStream.close();

        KotlinJsr223MainKtsScriptEngineFactory().extensions.forEach {
            logger.info(it)
        }

        val engine = KotlinJsr223DefaultScriptEngineFactory().scriptEngine

        file.walk().forEach {

            logger.info(it.name)

            if (!it.isFile) return@forEach;

            val reader = it.reader()
            engine.eval(reader)
            reader.close()
        }

        logger.info("Yoku Script Is Loaded!!")
     */
}