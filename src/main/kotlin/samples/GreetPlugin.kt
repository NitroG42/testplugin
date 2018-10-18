package samples

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the

class GreetPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        val android = requireNotNull(project.the<AppExtension>()) {
            "The 'com.android.application' plugin is required."
        }
        logger.info("starting")
        val greeting = extensions.create("greeting", Greeting::class)
        tasks {
            logger.info("tasks")
            val greet = register("greet") {
                doLast {
                    println(greeting.message)
                }
            }
            project.afterEvaluate {
                logger.info("afterEvaluate")
                android.applicationVariants.forEach {
                    //                    if (it.getBuildType().isDebuggable()) {
                    logger.info("foreach")
                    it.preBuild.dependsOn(greet)
//                    }
                }
            }
        }
    }

}


open class Greeting {
    var message = "Hello!"
}