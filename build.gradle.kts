import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
    kotlin("plugin.noarg") version "2.0.0"
}

noArg {
    annotation("souliving.backend.model.NoArg")
}

group = "souliving-backend"
version = "0.0.1-SNAPSHOT"


java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.2")

    implementation("org.springdoc:springdoc-openapi-starter-common:2.3.0")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-config")

    implementation("org.simplejavamail:simple-java-mail:8.8.0")
    implementation("org.simplejavamail:spring-module:8.8.0")

    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/io.kotest/kotest-runner-junit5-jvm
   // testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.1")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
    testImplementation("io.kotest:kotest-property:5.8.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    testImplementation("io.projectreactor:reactor-test")
}


tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<BootRun> {
    jvmArgs("-Xms128m", "-Xmx512m", "-XX:+UseSerialGC")
}
