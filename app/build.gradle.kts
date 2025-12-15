plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.localgo.artelabspa"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.localgo.artelabspa"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // ✅ API KEY WeatherAPI (correcto)
        buildConfigField(
            "String",
            "WEATHER_API_KEY",
            "\"fca21e3e35774a03acd195335251512\""
        )

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

    // 🔥 ESTO FALTABA (MUY IMPORTANTE)
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    // CORE
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation("androidx.activity:activity-compose:1.9.0")

    // COMPOSE
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // NAVIGATION
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // NETWORK
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // IMÁGENES
    implementation("io.coil-kt:coil-compose:2.6.0")

    // TEST
    testImplementation("junit:junit:4.13.2")
}
