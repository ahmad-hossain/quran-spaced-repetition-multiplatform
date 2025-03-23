import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.multiplatform.resources)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_18)
                }
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
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = false
            export(libs.moko.resources)
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqldelight.primitive.adapters)
            api(libs.moko.resources)
            api(libs.rickclephas.kmp.viewmodel.core)
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
    }
}

android {
    namespace = "com.github.ahmad_hossain.quranhifzrevision"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}

dependencies {
    ksp(libs.room.compiler)
}

sqldelight {
    databases {
        create("PageDatabase") {
            packageName.set("com.github.ahmad_hossain.quranhifzrevision")
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

multiplatformResources {
    resourcesPackage.set("com.github.ahmad_hossain.quranhifzrevision")
    resourcesClassName.set("SharedRes")
}