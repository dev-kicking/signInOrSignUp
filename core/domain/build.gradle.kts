plugins {
    id("signinorsignup.android.library")
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
