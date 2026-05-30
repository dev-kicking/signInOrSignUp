plugins {
    id("signinorsignup.android.library.compose")
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:resources"))

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
}
