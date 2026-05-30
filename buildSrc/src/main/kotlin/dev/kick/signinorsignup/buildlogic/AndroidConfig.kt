package dev.kick.signinorsignup.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val COMPILE_SDK = 36
private const val MIN_SDK = 31
private const val TARGET_SDK = 36
private const val TEST_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
private const val BASE_NAMESPACE = "dev.kick.signinorsignup"

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.application")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        configureAndroidApplication(enableCompose = true)
    }
}

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")

        configureAndroidLibrary(enableCompose = false)
    }
}

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        configureAndroidLibrary(enableCompose = true)
    }
}

private fun Project.configureAndroidApplication(enableCompose: Boolean) {
    extensions.configure<ApplicationExtension> {
        namespace = BASE_NAMESPACE
        compileSdk = COMPILE_SDK
        defaultConfig {
            applicationId = BASE_NAMESPACE
            minSdk = MIN_SDK
            targetSdk = TARGET_SDK
            versionCode = 1
            versionName = "1.0"
            testInstrumentationRunner = TEST_RUNNER
        }
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
        }
        configureJava()
        buildFeatures {
            compose = enableCompose
        }
    }
    configureKotlin()
}

private fun Project.configureAndroidLibrary(enableCompose: Boolean) {
    extensions.configure<LibraryExtension> {
        namespace = moduleNamespace()
        compileSdk = COMPILE_SDK
        defaultConfig {
            minSdk = MIN_SDK
            testInstrumentationRunner = TEST_RUNNER
        }
        buildTypes {
            release {
                isMinifyEnabled = false
            }
        }
        configureJava()
        buildFeatures {
            compose = enableCompose
        }
    }
    configureKotlin()
}

private fun Project.moduleNamespace(): String {
    val pathNamespace = path
        .removePrefix(":")
        .replace(':', '.')
        .replace('-', '_')

    return "$BASE_NAMESPACE.$pathNamespace"
}

private fun com.android.build.api.dsl.CommonExtension<*, *, *, *, *, *>.configureJava() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}
