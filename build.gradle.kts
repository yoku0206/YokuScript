import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask

plugins {
    idea
    java
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("dev.s7a.gradle.minecraft.server") version "2.1.0"
}

group = "me.yoku"
version = properties["version"] as String

val mcVersion = "1.19"

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlin:kotlin-main-kts:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-script-util:1.8.0")


}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain(17)
}

tasks {

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    jar {

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    }

    processResources {

        val placeholders = mapOf(
            "name" to rootProject.name,
            "version" to version,
            "apiVersion" to mcVersion,
            "kotlinVersion" to project.properties["kotlinVersion"]
        )

        filesMatching("plugin.yml") {
            expand(placeholders)
        }

    }

    shadowJar {

        archiveBaseName.set("${project.name}-${project.version}")
        archiveClassifier.set("")
        archiveVersion.set("")

//        minimize()

    }

    build {

        dependsOn(shadowJar)

    }

    register<LaunchMinecraftServerTask>("LaunchServer") {

        jarUrl.set(LaunchMinecraftServerTask.JarUrl.Paper("1.19.3"))
        agreeEula.set(true)

        dependsOn(shadowJar)

        doFirst {

            copy {
                from(buildDir.resolve("libs/${project.name}-${project.version}.jar"))
                into(buildDir.resolve("MinecraftServer/plugins"))
            }

        }

    }

}