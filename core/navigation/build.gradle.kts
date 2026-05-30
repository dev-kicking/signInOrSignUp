plugins {
    id("signinorsignup.android.library.compose")
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
}
