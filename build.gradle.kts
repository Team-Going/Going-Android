buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(ClassPathPlugins.gradle)
        classpath(ClassPathPlugins.kotlinGradle)
        classpath(ClassPathPlugins.hilt)
        classpath(ClassPathPlugins.oss)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
