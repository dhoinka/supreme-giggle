plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.gloomstone"
version = "0.0.1-SNAPSHOT"
description = "supreme-giggle"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation(platform("com.google.cloud:libraries-bom:26.72.0"))
    implementation("com.google.cloud:google-cloud-firestore")
    // Source: https://mvnrepository.com/artifact/com.aventrix.jnanoid/jnanoid
    implementation("com.aventrix.jnanoid:jnanoid:2.0.0")


    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mockito.kotlin:mockito-kotlin:6.3.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    val imageName = System.getenv("IMAGE_NAME") ?: "supreme-giggle"
    val registry = System.getenv("REGISTRY") ?: "docker.io"
    val projectId = System.getenv("GCP_PROJECT_ID") ?: "local"
    val imageSha = System.getenv("IMAGE_SHA") ?: "latest"
    val shouldPublish = System.getenv("PUBLISH_IMAGE") == "true"

    builder = "paketobuildpacks/builder-jammy:latest"
    environment = mapOf(
        "BP_JVM_VERSION" to "21"
    )

    // Construct full image name based on registry
    val fullImageName = if (registry == "docker.io") {
        "$registry/$imageName:$imageSha"
    } else {
        "$registry/$projectId/$imageName:$imageSha"
    }

    this.imageName.set(fullImageName)
    publish.set(shouldPublish)
}
