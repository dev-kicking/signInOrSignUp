plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.13.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    implementation("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.0.21")
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "signinorsignup.android.application.compose"
            implementationClass = "dev.kick.signinorsignup.buildlogic.AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "signinorsignup.android.library"
            implementationClass = "dev.kick.signinorsignup.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "signinorsignup.android.library.compose"
            implementationClass = "dev.kick.signinorsignup.buildlogic.AndroidLibraryComposeConventionPlugin"
        }
    }
}
