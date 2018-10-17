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
//    testImplementation("com.android.tools.build:gradle:3.1.4")
    testImplementation("junit:junit:4.12")
}

repositories {
    google()
    jcenter()
}