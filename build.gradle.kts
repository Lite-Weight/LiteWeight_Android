// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46")
    }
}

plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    kotlin("android") version Versions.KOTLIN_VERSION apply false
    kotlin("jvm") version Versions.KOTLIN_VERSION apply false
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
    id("com.google.dagger.hilt.android") version Versions.HILT apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}
