object Versions {

    // Kotlin
    const val KOTLIN_VERSION = "1.8.0"
    const val JVM_TARGET = "1.8"
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 26
    const val TARGET_SDK_VERSION = 33

    // AndroidX
    const val CORE_KTX = "1.10.0"
    const val APP_COMPAT = "1.6.1"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val NAVIGATION = "2.5.3"

    // Android
    const val MATERIAL = "1.8.0"

    // Test
    const val JUNIT = "4.13.2"
    const val ANDROID_JUNIT = "1.1.5"
    const val ESPRESSO_CORE = "3.5.1"
}

object Kotlin {
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"
}

object AndroidX {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
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
