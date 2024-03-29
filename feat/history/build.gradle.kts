plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.konkuk.history"
    compileSdk = Versions.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = Versions.MIN_SDK_VERSION
        targetSdk = Versions.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        jvmTarget = Versions.JVM_TARGET
    }
    dataBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":feat:capture"))

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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt(DI.HILT_COMPLIER)

    // Lifecycle (by viewModels())
    implementation(AndroidX.LIFECYCLE_VIEWMODEL)
    implementation(AndroidX.LIFECYCLE_LIVEDATA)
}

kapt {
    correctErrorTypes = true
}
