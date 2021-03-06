import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin(KotlinPlugins.multiplatform)
    kotlin(KotlinPlugins.cocoapods)
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id(Plugins.androidLibrary)
    id(Plugins.sqlDelight)
}

version = "1.0" // Cocoapods Integration requires version of this project to be specified

android {
    compileSdk = Application.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosFood2Fork/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Kotlinx.datetime)
                implementation(Ktor.core)
                implementation(Ktor.contentNegotiation)
                implementation(Ktor.jsonSerialization)
                implementation(SQLDelight.runtime)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Ktor.android)
                implementation(SQLDelight.androidDriver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Ktor.darwin)
                implementation(SQLDelight.nativeDriver)
            }
        }
    }
}

sqldelight {
    database("RecipeDatabase") {
        packageName = "com.egorshustov.food2forkkmm.datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}