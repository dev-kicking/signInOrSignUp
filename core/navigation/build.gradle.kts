plugins {
    id("dev.kick.signinorsignup.android.navigation")
}

android {
    namespace = "dev.kick.signinorsignup.core.navigation"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
}
