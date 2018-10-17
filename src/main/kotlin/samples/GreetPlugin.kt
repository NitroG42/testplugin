package samples
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.gradle.kotlin.dsl.*

class GreetPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        val android = requireNotNull(project.the<AppExtension>()) {
            "The 'com.android.application' plugin is required."
        }
        val greeting = extensions.create("greeting", Greeting::class)
        tasks {
            register("greet") {
                doLast {
                    println(greeting.message)
                }
            }
        }
    }
}


open class Greeting {
    var message = "Hello!"
}