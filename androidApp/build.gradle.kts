plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.ksp)
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 1

android {
    namespace = "com.github.ahmad_hossain.quranhifzrevision.android"
    compileSdk = 34
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
    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material.icons.extended)

    // Compose Destinations
    implementation(libs.raamcosta.compose.destinations.core)
    ksp(libs.raamcosta.compose.destinations.ksp)

    // Splash Screen Compat
    implementation(libs.androidx.core.splashscreen)

    // Kotlinx datetime
    implementation(libs.kotlinx.datetime)
    coreLibraryDesugaring(libs.android.desugar.jdk.libs)
}