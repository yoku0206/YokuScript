plugins {
    idea
    java
    kotlin("jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
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
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.0")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.7.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.0")

    shadow("org.jetbrains.kotlin:kotlin-reflect:1.7.0")
    shadow("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")

    implementation("org.jetbrains.kotlin:kotlin-main-kts:1.7.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.7.0")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.7.0")

    runtimeOnly("org.jetbrains.kotlin:kotlin-main-kts:1.7.0")
    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.7.0")

//    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
//    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
//    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies")
//    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven")
//    // coroutines dependency is required for this particular definition
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
//    implementation("org.jetbrains.kotlin:kotlin-script-util:1.8.0")

}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/java")
    }
}

java {

}

kotlin {

}

tasks {

//    wrapper {
//        gradleVersion = "7.4.1"
//        distributionType = Wrapper.DistributionType.ALL
//    }

    compileKotlin {
        kotlinOptions.jvmTarget = "11"
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
        minimize()
        archiveBaseName.set(project.name)
    }

    register("fatJar", Jar::class.java) {
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes("Main-Class" to "me.yoku.yokuscript.YokuScript")
        }
        from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)
    }

    build {
        dependsOn(shadowJar)
        dependsOn("fatJar")
    }
}