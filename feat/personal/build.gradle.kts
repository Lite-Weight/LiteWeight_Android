plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.konkuk.personal"
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
    viewBinding {
        enable = true
    }
}

dependencies {

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
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
}
