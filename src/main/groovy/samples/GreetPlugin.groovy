package samples

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


class GreetPlugin implements Plugin<Project> {
    void apply(Project project) {
        def android = project.getExtensions().findByType(AppExtension)
        def greeting = project.getExtensions().create("greeting", Greeting.class)

        project.tasks.register("greet") {
            doLast() {
                println(greeting.message)
            }
        }
    }

}


class Greeting {
    def message = "Hello!"
}