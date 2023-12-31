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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
