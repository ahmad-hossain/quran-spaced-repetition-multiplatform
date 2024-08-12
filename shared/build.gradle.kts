val sqlDelightVersion = "2.0.0"

plugins {
    alias(libs.plugins.multiplatform.kotlin)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.multiplatform.resources)
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
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.sqldelight.coroutines.extensions)
                implementation(libs.kotlinx.datetime)
                implementation(libs.sqldelight.primitive.adapters)
                api(libs.moko.resources)
                api(libs.rickclephas.kmm.viewmodel.core)
                implementation(libs.androidx.datastore.preferences.core)
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
                implementation(libs.sqldelight.android.driver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }
    }
}

android {
    namespace = "com.github.ahmad_hossain.quranhifzrevision"
    compileSdk = 34
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