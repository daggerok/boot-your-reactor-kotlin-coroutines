import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    idea
    base
    java
    id("com.github.ben-manes.versions") version Globals.versionsGradlePluginVersion
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
    named<DependencyUpdatesTask>("dependencyUpdates") {
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea", "SNAPSHOT")
                            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
                            .any { it.matches(candidate.version) }
                    if (rejected) reject("Release candidate")
                }
            }
        }
    }
}

defaultTasks("clean", "build")
