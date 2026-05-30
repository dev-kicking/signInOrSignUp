package dev.kick.signinorsignup

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("com.google.dagger.hilt.android")
        apply("com.google.devtools.ksp")
    }

    dependencies {
        "implementation"(findLibrary("dagger-hilt-android"))
        "ksp"(findLibrary("dagger-hilt-android-compiler"))
    }
}
