val sqlDelightVersion = "2.0.0"

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.0"
    id("dev.icerock.mobile.multiplatform-resources")
    kotlin("plugin.serialization") version "1.9.0"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            // Fixes 'target compatibility' compile-time error
            tasks.withType(JavaCompile::class.java) {
                targetCompatibility = "11"
                sourceCompatibility = "11"
            }
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            export("dev.icerock.moko:resources:0.23.0")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
                implementation("app.cash.sqldelight:primitive-adapters:$sqlDelightVersion")
                api("dev.icerock.moko:resources:0.23.0")
                api("com.rickclephas.kmm:kmm-viewmodel-core:1.0.0-ALPHA-10")
                implementation("androidx.datastore:datastore-core:1.1.0-alpha02")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                dependsOn(commonMain) // Fixes moko build error due to kotlin 1.9.x version (https://github.com/icerockdev/moko-resources/issues/562)
                implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
    }
}

android {
    namespace = "com.github.ahmad_hossain.quranhifzrevision"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
}

sqldelight {
    databases {
        create("PageDatabase") {
            packageName.set("com.github.ahmad_hossain.quranhifzrevision")
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.github.ahmad_hossain.quranhifzrevision"
    multiplatformResourcesClassName = "SharedRes"
}