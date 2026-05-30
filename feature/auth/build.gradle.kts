plugins {
    id("dev.kick.signinorsignup.android.feature")
    id("dev.kick.signinorsignup.android.hilt")
}

android {
    namespace = "dev.kick.signinorsignup.feature.auth"
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
