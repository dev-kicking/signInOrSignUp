package dev.kick.signinorsignup

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeAndroid() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }

    androidExtension.apply {
        buildFeatures {
            compose = true
        }
    }

    dependencies {
        val bom = findLibrary("androidx-compose-bom")
        "implementation"(platform(bom))
        "androidTestImplementation"(platform(bom))

        "implementation"(findLibrary("androidx-compose-material3"))
        "implementation"(findLibrary("androidx-compose-ui"))
        "implementation"(findLibrary("androidx-compose-ui-graphics"))
        "implementation"(findLibrary("androidx-compose-ui-tooling-preview"))
        "androidTestImplementation"(findLibrary("androidx-compose-ui-test-junit4"))
        "debugImplementation"(findLibrary("androidx-compose-ui-test-manifest"))
        "debugImplementation"(findLibrary("androidx-compose-ui-tooling"))
    }
}
