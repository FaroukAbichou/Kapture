import org.gradle.internal.os.OperatingSystem
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "com.farouk-abichou"
version = "1.0-SNAPSHOT"


javafx {
    version
repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
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
    implementation("dev.chrisbanes.haze:haze:0.3.0")
    implementation("org.bytedeco:ffmpeg-platform:6.0-1.5.9")

    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transition)
    implementation(libs.voyager.koin)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.kotlinx.dateTime)

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.openjfx:javafx-controls:16")
    implementation("org.openjfx:javafx-media:16")
    implementation("org.openjfx:javafx-swing:16")
    implementation("org.openjfx:javafx-fxml:16")


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

