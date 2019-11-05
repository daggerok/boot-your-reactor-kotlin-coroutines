import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    idea
    base
    java
}

allprojects {
    group = Globals.groupId
    version = Globals.version

    apply<JavaGradlePluginPlugin>()
    java.sourceCompatibility = JavaVersion.VERSION_11
}

tasks {
    withType<Wrapper>().configureEach {
        gradleVersion = Globals.gradleWrapperVersion
        distributionType = Wrapper.DistributionType.BIN
    }
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            showStandardStreams = true
            events(PASSED, SKIPPED, FAILED)
        }
    }
}

defaultTasks("clean", "build")
