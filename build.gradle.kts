plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.multiplatform.kotlin) apply false
}

buildscript {
    dependencies {
        classpath(libs.moko.resources.generator)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
