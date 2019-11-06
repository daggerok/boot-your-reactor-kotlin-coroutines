import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version Globals.kotlinVersion
    kotlin("plugin.spring") version Globals.kotlinVersion
    id("org.springframework.boot") version Globals.springBootVersion
    id("io.spring.dependency-management") version Globals.dependencyManagementVersion
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
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
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

defaultTasks("clean", "build")
