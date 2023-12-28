plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = Versions.javaVersion
    targetCompatibility = Versions.javaVersion
}

dependencies {
    KotlinDependencies.run {
        implementation(kotlin)
        implementation(coroutines)
        implementation(dateTime)
    }
}