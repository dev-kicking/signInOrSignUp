import dev.kick.signinorsignup.findLibrary

plugins {
    id("dev.kick.signinorsignup.android.library")
    id("dev.kick.signinorsignup.android.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:resources"))
    implementation(project(":core:ui"))

    implementation(findLibrary("androidx-activity-compose"))
    implementation(findLibrary("androidx-core-ktx"))
    implementation(findLibrary("androidx-hilt-navigation-compose"))
    implementation(findLibrary("androidx-lifecycle-runtime-compose"))
    implementation(findLibrary("androidx-lifecycle-runtime-ktx"))
    implementation(findLibrary("androidx-lifecycle-viewmodel-compose"))
    implementation(findLibrary("kotlinx-serialization-json"))
}
