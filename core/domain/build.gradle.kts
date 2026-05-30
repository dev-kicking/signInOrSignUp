plugins {
    id("dev.kick.signinorsignup.android.library")
}

android {
    namespace = "dev.kick.signinorsignup.core.domain"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
