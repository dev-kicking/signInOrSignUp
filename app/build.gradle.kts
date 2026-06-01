plugins {
    id("dev.kick.signinorsignup.android.application")
    id("dev.kick.signinorsignup.android.compose")
    id("dev.kick.signinorsignup.android.hilt")
}

android {
    namespace = "dev.kick.signinorsignup"

    defaultConfig {
        applicationId = "dev.kick.signinorsignup"
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:resources"))
    implementation(project(":core:ui"))
    implementation(project(":feature:auth"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
