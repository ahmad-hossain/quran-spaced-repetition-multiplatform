enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal() // For local sqldelight native-driver dependency
        google()
        mavenCentral()
    }
}

rootProject.name = "QuranHifzRevision"
include(":androidApp")
include(":shared")