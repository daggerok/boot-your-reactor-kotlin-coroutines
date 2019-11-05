import org.gradle.api.JavaVersion
import org.gradle.api.tasks.testing.logging.TestLogEvent

object Globals {
    const val version = "1.0.0-SNAPSHOT"
    const val groupId = "com.github.daggerok.coroutines"

    const val kotlinVersion = "1.3.50"
    const val springBootVersion = "2.2.0.RELEASE"
    const val springBootR2dbcVersion = "0.1.0.M2"
    const val dependencyManagementVersion = "1.0.8.RELEASE"

    const val gradleWrapperVersion = "5.6.4"
    const val versionsGradlePluginVersion = "0.27.0"

    val javaVersion = JavaVersion.VERSION_11
    val freeCompilerArgs = listOf("-Xjsr305=strict")

    val testLoggingEvents = arrayOf(
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.FAILED
    )
}
