plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.ramir.bakeryapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.ramir.bakeryapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
        )
        )
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    //kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.8.0")

    implementation(libs.dagger.hilt)
    implementation(libs.dagger.hilt.navigation)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit.junit)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.coil.compose)
    implementation("androidx.compose.material:material-icons-extended:1.7.8")


    implementation("io.coil-kt:coil-compose:2.5.0")

    val room_version = "2.8.4"
    implementation("androidx.room:room-runtime:${room_version}")
    kapt("androidx.room:room-compiler:${room_version}")

    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.14.7")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
//Ya estaba, la hemos actualizado.
    testImplementation("io.mockk:mockk:1.13.8")

    implementation("androidx.core:core-splashscreen:1.0.1")


    // If you are doing instrumented tests (AndroidTest folder)
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    //testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    //testImplementation (libs.turbine.v100)
    testImplementation("app.cash.turbine:turbine:1.0.0")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}