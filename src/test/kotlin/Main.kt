import org.jetbrains.kotlin.mainKts.jsr223.KotlinJsr223MainKtsScriptEngineFactory
import java.io.File

fun main() {

    val file = File("src/test/resources/scripts/", "example.main.kts")

    val scriptEngine = KotlinJsr223MainKtsScriptEngineFactory().scriptEngine

    scriptEngine.eval(file.readText())

}