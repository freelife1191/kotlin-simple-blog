import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.22"
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.asciidoctor.jvm.convert") version "4.0.1"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    // kotlin("plugin.noarg") version kotlinVersion
    // kotlin("plugin.allopen") version kotlinVersion
}

/*
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
*/


group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val snippetsDir by extra { file("build/generated-snippets") }
val koTestVersion by extra { "5.8.0" }
val kotestExtVersion by extra { "1.1.3" }
extra["springCloudVersion"] = "2023.0.0"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies {
    // implementation("org.springframework.boot:spring-boot-starter-actuator")
    // implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // implementation("org.springframework.boot:spring-boot-starter-data-redis")
    // implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    // implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-docker-compose")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    // testImplementation("org.springframework.security:spring-security-test")
    implementation(kotlin("stdlib-jdk8"))

    // implementation("org.hibernate:hibernate-core:6.2.7.Final")
    // implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate6:2.15.2")
    // https://github.com/line/kotlin-jdsl/blob/main/spring/README.md
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter-jakarta:2.2.1.RELEASE")

    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    // implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    // implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")
    // implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    // test
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.kotest:kotest-runner-junit5:${koTestVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${koTestVersion}")
    testImplementation("io.kotest:kotest-property:${koTestVersion}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${kotestExtVersion}")

    // https://github.com/serpro69/kotlin-faker
    // 더미 데이터 생성
    implementation("io.github.serpro69:kotlin-faker:1.15.0")
    // JPA 로그 포멧팅 출력
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.1")
    // Kotlin Logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    // apache
    implementation("org.apache.commons:commons-lang3:3.14.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.withType<Test> {
     useJUnitPlatform()
//    exclude("**/*")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}