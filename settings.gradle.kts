pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // KakaoSDK repository
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")

        // 오픈 소스 라이브러리 배포 플랫폼 jitpack 추가
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "GoingGoing"

include(":app")
include(":core-ui")
include(":data")
include(":domain")
include(":presentation")
