plugins {
    java
    application

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.danilopianini.gradle-java-qa") version "1.35.0"
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("mysql:mysql-connector-java:8.0.29")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation(libs.junit)

    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.3")


}

application {
    mainClass = "db_lab.App"
}
