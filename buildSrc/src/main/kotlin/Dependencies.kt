object KotlinDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroidVersion}"
    const val jsonSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerializationJsonVersion}"
    const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinDateTimeVersion}"
}

object AndroidXDependencies {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashVersion}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val startup = "androidx.startup:startup-runtime:${Versions.appStartUpVersion}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activityKtxVersion}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"
    const val security = "androidx.security:security-crypto:${Versions.securityVersion}"

    const val lifeCycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    const val lifeCycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"

    const val paging = "androidx.paging:paging-common-ktx:${Versions.pagingVersion}"
    const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.pagingVersion}"
    const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManagerVersion}"

    const val hiltWorkManager = "androidx.hilt:hilt-work:1.0.0"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val ossLicense = "com.google.android.gms:play-services-oss-licenses:${Versions.ossVersion}"

    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
    const val navigationDynamic = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigationVersion}"
}

object TestDependencies {
    const val jUnit = "junit:junit:${Versions.junitVersion}"
    const val androidTest = "androidx.test.ext:junit:${Versions.androidTestVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
}

object MaterialDesignDependencies {
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesignVersion}"
}

object KaptDependencies {
    const val hiltAndroidCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
    const val hiltWorkManagerCompiler = "androidx.hilt:hilt-compiler:1.0.0"
}

object ThirdPartyDependencies {
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitJsonConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinSerializationConverterVersion}"
    const val okHttpBom = "com.squareup.okhttp3:okhttp-bom:${Versions.okHttpVersion}"
    const val okHttp = "com.squareup.okhttp3:okhttp"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor"

    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    const val ossLicense = "com.google.android.gms:play-services-oss-licenses:${Versions.ossVersion}"
    const val hiltCore = "com.google.dagger:hilt-core:${Versions.hiltVersion}"

    const val progressView = "com.github.skydoves:progressview:${Versions.progressViewVersion}"
    const val balloon = "com.github.skydoves:balloon:${Versions.balloonVersion}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"
    const val circularProgressBar = "com.mikhaellopez:circularprogressbar:${Versions.circularProgressBar}"
    const val circleIndicator = "me.relex:circleindicator:${Versions.circleIndicatorVersion}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmerVersion}"

    const val kakaoLogin = "com.kakao.sdk:v2-user:${Versions.kakaoVersion}"
    const val kakaoAuth = "com.kakao.sdk:v2-auth:${Versions.kakaoVersion}"
    const val kakaoTalk = "com.kakao.sdk:v2-talk:${Versions.kakaoVersion}"
    const val kakaoShare = "com.kakao.sdk:v2-share:${Versions.kakaoVersion}"

    const val amplitude = "com.amplitude:android-sdk:${Versions.amplitudeVersion}"
}

object ClassPathPlugins {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
    const val oss = "com.google.android.gms:oss-licenses-plugin:${Versions.ossPluginVersion}"
}

object FirebaseDependencies {
    const val bom = "com.google.firebase:firebase-bom:32.2.0"
    const val messaging = "com.google.firebase:firebase-messaging-ktx"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val analytics = "com.google.firebase:firebase-analytics-ktx"
    const val remoteConfig = "com.google.firebase:firebase-config-ktx"
}

object KakaoDependencies {
    const val user = "com.kakao.sdk:v2-user:${Versions.kakaoVersion}"
}

object GooglePlayDependencies {
    const val update = "com.google.android.play:app-update:${Versions.googlePlayUpdate}"
    const val updateKtx = "com.google.android.play:app-update-ktx:${Versions.googlePlayUpdate}"
}
