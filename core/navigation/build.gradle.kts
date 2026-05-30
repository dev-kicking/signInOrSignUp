plugins {
    id("dev.kick.signinorsignup.android.library")
    id("dev.kick.signinorsignup.android.compose")
}

android {
    namespace = "dev.kick.signinorsignup.core.navigation"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
}
