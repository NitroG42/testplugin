package samples


import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class GreetPluginTest {
    @Test
    void testPlugin() {

        def message = "Hello, TestKit!"

        def pluginBinaryDir = new File("build/libs")
//        def pluginJar = pluginBinaryDir
//                .listFiles()
//                .first { it.name.endsWith("testplugin.jar") }
//                .absoluteFile
//                .invariantSeparatorsPath

//            buildscript {
//                repositories {
//                    google()
//                    jcenter()
//                }
//                dependencies {
//                    classpath "com.android.tools.build:gradle:3.3.0-alpha13"
//                    classpath files("$pluginJar")
//                }
//            }
//
//            apply plugin: "com.android.application"
//            apply plugin: "greet"
        // language=gradle
        givenBuildScript('''
            plugins {
                id "com.android.application" version "3.3.0-alpha13"
                id "greet"
            }
//            println "Buildscript classpath: " + project.buildscript.configurations.classpath.asPath

            android {
                compileSdkVersion 27
                defaultConfig {
                    applicationId = "fr.openium.greeting"
                    minSdkVersion 15
                    targetSdkVersion 27
                    versionCode 1
                    versionName "1.0"
                }
            }

            greeting {
                message = "${message}"
            }
        ''')

        givenSettingsScript('''
            pluginManagement {
  repositories {
    gradlePluginPortal()
    jcenter()
    google()
  }
  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "com.android.application") {
        useModule("com.android.tools.build:gradle:${requested.version}")
      }
    }
  }
}
''')

        def main = temporaryFolder.newFolder("src", "main")
        //val main = File(app, "src/main")
        main.mkdirs()
        def manifest = new File(main, "AndroidManifest.xml")
        manifest.write('''<?xml version="1.0" encoding="utf-8"?>
<manifest package="fr.openium.greeting">

    <!-- This needs to be an empty element as workaround for https://code.google.com/p/android/issues/detail?id=77890 -->

</manifest>''')

        build("greet", "--stacktrace", "--info")
//        assertThat(
//                build("greet").output.trimEnd(),
//                equalTo(message))
    }

    private
    BuildResult build(String... arguments) {
        return GradleRunner.create()
                .withProjectDir(temporaryFolder.root)
                .withPluginClasspath()
                .withArguments(*arguments)
                .forwardOutput()
//            .withDebug(true)
                .build()
    }


    private
    void givenBuildScript(String script) {
        newFile("build.gradle").write(script)
    }

    private
    void givenSettingsScript(String script) {
        newFile("settings.gradle").write(script)
    }

    private
    File newFile(String fileName) {
        temporaryFolder.newFile(fileName)
    }


    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder()
}
