object Versions {

    // Kotlin
    const val KOTLIN_VERSION = "1.8.0"
    const val JVM_TARGET = "1.8"
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 26
    const val TARGET_SDK_VERSION = 33
    const val SERIALIZATION_JSON = "1.5.1"

    // AndroidX
    const val CORE_KTX = "1.10.0"
    const val APP_COMPAT = "1.6.1"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val NAVIGATION = "2.5.3"
    const val PAGING3 = "3.1.1"

    // Android
    const val MATERIAL = "1.8.0"

    // Test
    const val JUNIT = "4.13.2"
    const val ANDROID_JUNIT = "1.1.5"
    const val ESPRESSO_CORE = "3.5.1"

    // DI
    const val HILT = "2.46"

    // Lifecycle
    const val LIFECYCLE = "2.6.1"

    // Database
    const val ROOM = "2.5.0"

    // MLKIT
    const val TEXT_RECOGNITION = "16.0.0"

    // MPChart
    const val MPCHART = "v3.1.0"

    // Network
    const val RETROFIT = "2.9.0"
    const val OKHTTP = "4.10.0"
    const val SERIALIZATION_CONVERTER = "0.8.0"
}

object Kotlin {
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"
    const val SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.SERIALIZATION_JSON}"
}

object AndroidX {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

    // Navigation
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

    // LifeCycle
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"

    // Room
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPLIER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"

    // Pagging
    const val PAGING3 = "androidx.paging:paging-runtime:${Versions.PAGING3}"
}

object Material {
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
}

object UnitTest {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
}

object AndroidTest {
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}

object DI {
    const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPLIER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    const val HILT_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
}

object MLKIT {
    const val TEXT_RECOGNITION = "com.google.mlkit:text-recognition-korean:${Versions.TEXT_RECOGNITION}"
}

object MPCHART {
    const val GRAPH = "com.github.PhilJay:MPAndroidChart:${Versions.MPCHART}"
}

object NETWORK {
    // Retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val KOTLIN_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.SERIALIZATION_CONVERTER}"
    const val GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
}