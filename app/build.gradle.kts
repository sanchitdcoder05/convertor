plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.convertor"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.convertor"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    // Core Android libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom)) // BOM for version alignment
    implementation(libs.androidx.ui) // Core UI
    implementation(libs.androidx.ui.graphics) // Graphics for UI components
    implementation(libs.androidx.ui.tooling.preview) // Preview support
    implementation(libs.androidx.material3)
// Material 3

    // Material Icons Extended (for ArrowBack, ArrowDropDown, etc.)
    implementation("androidx.compose.material:material-icons-extended:1.5.1")

    // Navigation Component for Compose
    implementation("androidx.navigation:navigation-compose:2.7.2")

    // Coil for image loading (optional but recommended for resource handling)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Unit Testing
    testImplementation(libs.junit)

    // Instrumented Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM for testing Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debugging Tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
