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
}

dependencies {
    // Testing - JUnit
    testImplementation("junit:junit:4.13.2")

    // Testing - Coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Testing - MockK (mocking framework)

    // Testing - Turbine (para testar StateFlow/Flow)

    testImplementation("app.cash.turbine:turbine:1.0.0")

    // Testing - Core Testing (para InstantTaskExecutorRule)
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Android Testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // CORE & LIFECYCLE

    // Unificamos las versiones de lifecycle para evitar conflictos.
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // COMPOSE

    // Usamos la BOM estable para que gestione las versiones de las librerías de Compose.
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // NAVIGATION

    implementation("androidx.navigation:navigation-compose:2.7.7")

    // NETWORKING (RETROFIT)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // COROUTINES & ASYNC
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("io.coil-kt:coil-compose:2.6.0") // Coil para imágenes

    // HELPERS & UTILS

    implementation("androidx.datastore:datastore-preferences:1.1.1") // Versión más estable
    implementation("com.google.accompanist:accompanist-permissions:0.34.0") // Estable

    // TESTING

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1") // Alineada con coroutines
    testImplementation("app.cash.turbine:turbine:1.1.0") // Versión más reciente
    testImplementation("io.mockk:mockk:1.13.11") // Versión más reciente
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.robolectric:robolectric:4.12.1") // Versión más reciente

    androidTestImplementation("androidx.test.ext:junit:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00")) // Misma BOM
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Asegúrate de que esta línea está en tu build.gradle.kts
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}

