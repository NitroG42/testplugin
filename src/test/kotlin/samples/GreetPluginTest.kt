import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File


class GreetPluginTest {

    @Test
    fun `greeting message can be configured`() {

        val message = "Hello, TestKit!"

        givenBuildScript("""
            plugins {
                id("com.android.application") version("3.3.0-alpha13")
                id("greet")
            }
            android {
                compileSdkVersion(27)
                defaultConfig {
                    applicationId ="fr.openium.greeting"
                    minSdkVersion(15)
                    targetSdkVersion(27)
                    versionCode = 1
                    versionName = "1.0"
                }
            }

            greeting {
                message = "$message"
            }
        """)

        givenSettingsScript("""
            pluginManagement {
  repositories {
    gradlePluginPortal()
    jcenter()
    google()
  }
  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "com.android.application") {
        useModule("com.android.tools.build:gradle:${'$'}{requested.version}")
      }
    }
  }
}
        """.trimIndent())

        val main = temporaryFolder.newFolder("src", "main")
        //val main = File(app, "src/main")
        main.mkdirs()
        val manifest = File(main, "AndroidManifest.xml")
        manifest.apply {
            writeText("""<?xml version="1.0" encoding="utf-8"?>
<manifest package="fr.openium.greeting">

    <!-- This needs to be an empty element as workaround for https://code.google.com/p/android/issues/detail?id=77890 -->

</manifest>""")
        }

        build("greet", "--info", "--stacktrace")
//        assertThat(
//                build("greet").output.trimEnd(),
//                equalTo(message))
    }

    private
    fun build(vararg arguments: String): BuildResult =
            GradleRunner
                    .create()
                    .withProjectDir(temporaryFolder.root)
                    .withPluginClasspath()
                    .withArguments(*arguments)
                    .forwardOutput()
//            .withDebug(true)
                    .build()

    private
    fun givenBuildScript(script: String) =
            newFile("build.gradle.kts").apply {
                writeText(script)
            }

    private
    fun givenSettingsScript(script: String) =
            newFile("settings.gradle.kts").apply {
                writeText(script)
            }

    private
    fun newFile(fileName: String): File =
            temporaryFolder.newFile(fileName)

    @Rule
    @JvmField
    val temporaryFolder = TemporaryFolder()
}