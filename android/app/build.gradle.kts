plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
    id("org.jetbrains.kotlin.android") version "1.8.22"
}

android {
    namespace = "com.example.flutter_application"
    compileSdk = 34 
    ndkVersion = "27.0.12077973"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        applicationId = "com.example.flutter_application"
        minSdk = 21 // Substitua pelo valor correto
        targetSdk = 33 // Substitua pelo valor correto
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0" // Atualize para uma versão compatível
    }
}

flutter {
    source = "../.."
}

dependencies {
    implementation("androidx.glance:glance-appwidget:1.0.0-alpha05")
    implementation("androidx.compose.runtime:runtime:1.0.0")
    implementation("androidx.compose.compiler:compiler:1.4.0") // Adicione esta linha
}