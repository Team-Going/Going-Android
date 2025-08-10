import org.gradle.api.JavaVersion

object Versions {
    // AGP (com.android.tools.build:gradle)
    // plugins { id("com.android.application") version "8.7.2" } 로 맞춰 쓰는 걸 권장
    const val gradleVersion = "8.7.2"

    // buildToolsVersion는 AGP 8.x부터 명시 불필요. 그래도 상수 유지하려면 35로.
    const val buildToolsVersion = "35.0.0"

    // Kotlin
    const val kotlinVersion = "1.9.24"
    const val kotlinSerializationJsonVersion = "1.6.3"
    const val kotlinDateTimeVersion = "0.6.1"        // 최신권장

    // AndroidX core / appcompat / material
    const val coreKtxVersion = "1.13.1"
    const val appCompatVersion = "1.7.0"
    const val materialDesignVersion = "1.12.0"

    // UI
    const val constraintLayoutVersion = "2.1.4"      // 2.2.0은 아직 안정화 대기
    const val flexboxVersion = "3.0.0"
    const val circleIndicatorVersion = "2.1.6"
    const val circleImageViewVersion = "3.1.0"
    const val shimmerVersion = "0.5.0"

    // App Startup / Legacy / Security
    const val appStartUpVersion = "1.1.1"
    const val legacySupportVersion = "1.0.0"
    const val securityVersion = "1.1.0-alpha06"      // 안정판 대체 없으면 유지

    // Hilt (Dagger)
    const val hiltVersion = "2.51.1"

    // Activity/Fragment/Lifecycle/Navi/Paging/Work/Splash
    const val activityKtxVersion = "1.9.2"
    const val fragmentKtxVersion = "1.8.3"
    const val lifecycleVersion = "2.8.4"
    const val navigationVersion = "2.8.0"
    const val pagingVersion = "3.3.2"
    const val workManagerVersion = "2.9.1"
    const val splashVersion = "1.0.1"

    // Coroutines
    const val coroutinesAndroidVersion = "1.9.0"

    // Networking
    const val retrofitVersion = "2.11.0"
    const val kotlinSerializationConverterVersion = "1.0.0" // (JakeWharton) 최신 여전히 1.0.0
    const val okHttpVersion = "4.12.0"                      // OkHttp 5는 API 변화 큼 → 4.12 권장

    // 이미지/애니메이션
    const val coilVersion = "2.6.0"
    const val lottieVersion = "6.4.1"

    // 기타
    const val timberVersion = "5.0.1"
    const val progressViewVersion = "1.1.3"
    const val balloonVersion = "1.4.5"
    const val circularProgressBar = "3.1.0"
    const val kakaoVersion = "2.19.0"
    const val amplitudeVersion = "2.23.2" // 프로젝트 요구 맞추되, 필요시 최신 확인

    // 테스트
    const val junitVersion = "4.13.2"
    const val espressoVersion = "3.6.1"
    const val androidTestVersion = "1.2.1" // androidx.test:runner / rules 세트 기준

    // Java / JVM
    val javaVersion = JavaVersion.VERSION_21
    const val jvmVersion = "21"

    // ▷ OSS Licenses
    const val ossPluginVersion = "0.10.6"
    const val ossVersion = "17.1.0"
}
