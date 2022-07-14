plugins {
    id("com.android.application")
    kotlin("android")
}
val ktorVersion = "2.0.3"
val serializationVersion = "1.3.3"
val coroutinesVersion = "1.6.1"

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.devaria.devagram.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
}