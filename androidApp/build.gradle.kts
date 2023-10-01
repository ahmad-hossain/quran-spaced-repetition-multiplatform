plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version "1.9.0-1.0.11"
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 1

android {
    namespace = "com.github.ahmad_hossain.quranhifzrevision.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.github.ahmad_hossain.quranspacedrepetition"
        minSdk = 21
        targetSdk = 33
        versionCode = Integer.parseInt("$versionMajor$versionMinor$versionPatch")
        versionName = "$versionMajor.$versionMinor.$versionPatch"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(platform("androidx.compose:compose-bom:2023.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.material:material-icons-extended")

    // Compose Destinations
    implementation("io.github.raamcosta.compose-destinations:core:1.7.17-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.7.17-beta")

    // Splash Screen Compat
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Kotlinx datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    // Material 3 Date & Time Pickers
    implementation("com.marosseleng.android:compose-material3-datetime-pickers:0.6.2")
}