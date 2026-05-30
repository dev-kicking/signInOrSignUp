plugins {
    id("dev.kick.signinorsignup.android.library")
}

android {
    namespace = "dev.kick.signinorsignup.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
