package dev.kick.signinorsignup

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutineAndroid() {
    dependencies {
        "implementation"(findLibrary("kotlinx-coroutines-android"))
    }
}
