import org.gradle.internal.os.OperatingSystem
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.farouk-abichou"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(libs.ffmpeg)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutinesCore)

}

val os: OperatingSystem = OperatingSystem.current()

tasks.register<Exec>("makeExecutable") {
    // hedhi btetbadel
    commandLine = listOf("chmod", "+x", "lib/macos/ffmpeg")
}

tasks.named("build"){
    dependsOn("makeExecutable")
}


compose.desktop {
    
    application {
        mainClass = "MainKt"

        nativeDistributions {
            appResourcesRootDir.set(project.layout.projectDirectory.dir("lib"))
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Kapture"
            packageVersion = "1.0.0"
            description = "Kapture is a screen recording app built with Kotlin and Compose Desktop."
            copyright = "© 2023 Softylines. All rights reserved."
            vendor = "Softylines"
            licenseFile.set(project.file("LICENSE.txt"))

            windows {
                iconFile.set(project.file("logo.ico"))
//                upgradeUuid = "f0b0f2a0-0f0f-0f0f-0f0f-f0b0f2a0f0f0"
                shortcut = true
                dirChooser = true
//                menuGroup = "start-menu-group"
                menu = true
            }
            macOS {
                iconFile.set(project.file("logo.icns"))

                jvmArgs(
                    "-Xdock:name=Kapture",
                    "-Dapple.awt.application.appearance=system",
                )
            }
            linux {
                iconFile.set(project.file("icon.png"))
            }
        }
        buildTypes.release.proguard {
            configurationFiles.from("compose-desktop.pro")
//            obfuscate.set(true)
            optimize.set(true)
        }
    }
}

