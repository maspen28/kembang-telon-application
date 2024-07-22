plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")

}

android {
    namespace = "com.code.kembang_telon"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.code.kembang_telon"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"http://192.168.0.6:8000\"")
        buildConfigField ("String", "CLIENT_KEY", "\"SB-Mid-client-0tjNqdkfZgCiKC-P\"")
        buildConfigField ("String", "PAYMENT_URL", "\"https://app.sandbox.midtrans.com/snap/v4/redirection\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    // Room architecture component dependencies
//    implementation("androidx.room:room-runtime:2.5.0-beta02")
//    kapt ("androidx.room:room-compiler:2.5.0-beta02")
//    implementation ("androidx.room:room-ktx:2.5.0-beta02")

    // midtrans
    implementation ("com.midtrans:uikit:2.0.0")
    implementation ("com.midtrans:uikit:2.0.0-SANDBOX")

    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.airbnb.android:lottie:5.2.0")
    implementation ("androidx.room:room-runtime:2.3.0")
    kapt ("androidx.room:room-compiler:2.3.0")

    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")

    implementation ("com.github.bumptech.glide:glide:4.15.0")
    implementation ("androidx.activity:activity-ktx:1.3.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}