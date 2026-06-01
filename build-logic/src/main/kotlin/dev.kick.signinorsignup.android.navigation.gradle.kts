import dev.kick.signinorsignup.findLibrary

plugins {
    id("dev.kick.signinorsignup.android.library")
    id("dev.kick.signinorsignup.android.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(findLibrary("androidx-navigation-compose"))
    implementation(findLibrary("kotlinx-serialization-json"))
}
