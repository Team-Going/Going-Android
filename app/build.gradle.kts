import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = Constants.packageName
    compileSdk = Constants.compileSdk

    defaultConfig {
        applicationId = Constants.packageName
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk
        versionCode = Constants.versionCode
        versionName = Constants.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // 👇 공식 API + providers 사용
        val localProps = gradleLocalProperties(rootDir, providers)

        buildConfigField(
            "String",
            "NATIVE_APP_KEY",
            "\"${localProps.getProperty("native.app.key")}\""
        )
        // manifestPlaceholders는 문자열 그 자체를 넣으면 됨(따옴표 불필요)
        manifestPlaceholders["NATIVE_APP_KEY"] = localProps.getProperty("nativeAppKey")
    }

    buildTypes {
        debug {
            val localProps = gradleLocalProperties(rootDir, providers)
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${localProps.getProperty("test.base.url")}\""
            )
        }
        release {
            val localProps = gradleLocalProperties(rootDir, providers)
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${localProps.getProperty("base.url")}\""
            )
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    KotlinDependencies.run {
        implementation(kotlin)
        implementation(coroutines)
        implementation(jsonSerialization)
    }

    AndroidXDependencies.run {
        implementation(coreKtx)
        implementation(appCompat)
        implementation(hilt)
        implementation(workManager)
        implementation(hiltWorkManager)
    }

    KaptDependencies.run {
        kapt(hiltCompiler)
        kapt(hiltWorkManagerCompiler)
    }

    TestDependencies.run {
        testImplementation(jUnit)
        androidTestImplementation(androidTest)
        androidTestImplementation(espresso)
    }

    ThirdPartyDependencies.run {
        implementation(platform(okHttpBom))
        implementation(okHttp)
        implementation(okHttpLoggingInterceptor)
        implementation(retrofit)
        implementation(retrofitJsonConverter)
        implementation(timber)
        implementation(ossLicense)
    }

    KakaoDependencies.run {
        implementation(user)
        implementation(share)
    }
}
