import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version Globals.kotlinVersion
    kotlin("plugin.spring") version Globals.kotlinVersion
    id("org.springframework.boot") version Globals.springBootVersion
    id("io.spring.dependency-management") version Globals.dependencyManagementVersion
    id("com.google.cloud.tools.jib") version Globals.jibGradlePluginVersion
    id("org.ajoberstar.reckon") version Globals.reckonGradlePluginVersion
}

group = Globals.groupId
// version = Globals.version // reckon
reckon {
    scopeFromProp()
    // stageFromProp()
    snapshotFromProp()
}
java.sourceCompatibility = Globals.javaVersion

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot.experimental:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // implementation("io.projectreactor:reactor-kotlin-extensions:1.0.0.M2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("io.r2dbc:r2dbc-h2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.boot.experimental:spring-boot-test-autoconfigure-r2dbc")
    testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot.experimental:spring-boot-bom-r2dbc:${Globals.springBootR2dbcVersion}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events(*Globals.testLoggingEvents)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = Globals.freeCompilerArgs
        jvmTarget = Globals.javaVersion.toString()
    }
}

tasks.withType<BootJar> {
    launchScript()
}

apply(from = "$projectDir/../gradle/jib.gradle")

defaultTasks("clean", "test")
// defaultTasks("clean", "jib")
// defaultTasks("clean", "build")
