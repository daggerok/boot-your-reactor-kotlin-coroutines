import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

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
    java.sourceCompatibility = Globals.javaVersion
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
            events(*Globals.testLoggingEvents)
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
    val buildSrcProjects = listOf("bot", "city", "user-message")
    buildSrcProjects.forEach {
        register<Copy>("$it-buildSrc") {
            from("$rootDir/buildSrc")
            into("$rootDir/$it/buildSrc")
            doLast {
                println("content copied to $rootDir/$it/buildSrc folder.")
            }
        }
    }
    register("buildSrc") {
        dependsOn(buildSrcProjects
                .map { "$it-buildSrc" }
                .toTypedArray())
    }
}

defaultTasks("clean", "buildSrc", "build")
