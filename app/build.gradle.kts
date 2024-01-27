plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.exoplayertask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.exoplayertask"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // dependency for exoplayer
    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")
    // for core support in exoplayer.
    implementation ("com.google.android.exoplayer:exoplayer-core:2.19.1")
    // for adding dash support in our exoplayer.
    implementation ("com.google.android.exoplayer:exoplayer-dash:2.19.1")
    // for adding hls support in exoplayer.
    implementation ("com.google.android.exoplayer:exoplayer-hls:2.19.1")
    // for smooth streaming of video in our exoplayer.
    implementation ("com.google.android.exoplayer:exoplayer-smoothstreaming:2.19.1")
    // for generating default ui of exoplayer
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.19.1")
    // for converting jSON data
    implementation ("com.google.code.gson:gson:2.10")
    // for load images from url
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}