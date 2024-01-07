import org.gradle.internal.os.OperatingSystem
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.farouk-abichou"
version = "1.0-SNAPSHOT"

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml", "javafx.media", "javafx.swing", "javafx.web")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://raw.github.com/agomezmoron/screen-recorder/mvn-repo")

    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(libs.kotlinx.serializationJson)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutinesCore)
    // Monte Media Screen Recorder
    implementation("ch.randelshofer:org.monte.media.screenrecorder:17.1")
    implementation("ch.randelshofer:org.monte.media:17.1")
    implementation("ch.randelshofer:org.monte.media.swing:17.1")


    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transition)
    implementation(libs.voyager.koin)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.kotlinx.dateTime)
}

val os: OperatingSystem = OperatingSystem.current()

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
                shortcut = true
                dirChooser = true
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

