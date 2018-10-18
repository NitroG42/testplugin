plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("greet") {
            id = "greet"
            implementationClass = "samples.GreetPlugin"
        }
    }
}

dependencies {
    compileOnly("com.android.tools.build:gradle:3.1.4")
    testImplementation("junit:junit:4.12")
}

repositories {
    google()
    jcenter()
}


tasks.withType<Test> {
    // Our integration tests need a fully compiled jar
    dependsOn("assemble")
}