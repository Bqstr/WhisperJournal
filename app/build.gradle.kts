plugins {
    alias(libs.plugins.android.application)
    kotlin("kapt")
    id("kotlin-kapt")
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")   // ✅ Add this line
}

android {
    namespace = "kz.bqstech.whisperJournal"
    compileSdk = 35

    defaultConfig {
        applicationId = "kz.bqstech.whisperJournal"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            // Existing exclusions
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            // Add the problematic file to the exclusion list
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    //implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.compose.testing)
    implementation(libs.androidx.graphics.shapes.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.kotlin.stdlib)
    implementation(libs.coroutines.android)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    //implementation(libs.compose.icons)

    // DI
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Media
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    // Ktor (Whisper API)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.gson)

    // Accompanist
    implementation(libs.system.ui)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)
//    androidTestImplementation(libs.ui.test.junit)
//    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.compose.tooling)

    //Navgation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    implementation("com.google.android.material:material:1.12.0")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
// latest stable (use 3.x when it’s stable)
// Converter for JSON (Gson)
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
// (Optional) For logging network requests
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")

    implementation("androidx.media3:media3-exoplayer:1.8.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.8.0")
    implementation("androidx.media3:media3-ui:1.8.0")
    implementation("androidx.media3:media3-ui-compose:1.8.0")



}
