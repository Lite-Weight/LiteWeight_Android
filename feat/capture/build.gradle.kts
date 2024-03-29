import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    namespace = "com.konkuk.capture"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "DATA_API_KEY", gradleLocalProperties(rootDir).getProperty("data.api.key"))
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":autotextcorrection"))

    // Android
    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.APP_COMPAT)
    implementation(AndroidX.CONSTRAINT_LAYOUT)
    implementation(Material.MATERIAL)

    // Test
    implementation(UnitTest.JUNIT)
    implementation(AndroidTest.ANDROID_JUNIT)
    implementation(AndroidTest.ESPRESSO_CORE)

    // Navigation
    implementation(AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(AndroidX.NAVIGATION_UI_KTX)

    // DI
    implementation(DI.HILT)
    kapt(DI.HILT_COMPLIER)

    // Lifecycle (by viewModels())
    implementation(AndroidX.LIFECYCLE_VIEWMODEL)
    implementation(AndroidX.LIFECYCLE_LIVEDATA)

    // OCR
    implementation(MLKIT.TEXT_RECOGNITION)

    // Serialization
    implementation(Kotlin.SERIALIZATION)

    // Network
    implementation(NETWORK.RETROFIT)
    // implementation(NETWORK.KOTLIN_CONVERTER)
    implementation(NETWORK.GSON_CONVERTER)
    implementation(NETWORK.OKHTTP)
    implementation(NETWORK.OKHTTP_INTERCEPTOR)

    // Paging
    implementation(AndroidX.PAGING3)
}

kapt {
    correctErrorTypes = true
}
