plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.videoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.videoapp"
        minSdk = 26
        targetSdk = 35
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // Compose
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.material)

    // Coil для загрузки изображений
    implementation (libs.coil.kt.coil.compose)

    // Retrofit & Gson
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    // ExoPlayer
    implementation (libs.exoplayer)
    implementation (libs.exoplayer.ui)

    // Room
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    ksp (libs.androidx.room.compiler)

    // Hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    //more icons
    implementation ("androidx.compose.material:material-icons-extended:1.6.0")
    //fetch mp4 video url from youtube api
    implementation ("com.github.HaarigerHarald:android-youtubeExtractor:v2.1.0")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.1")


}