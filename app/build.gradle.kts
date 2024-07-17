plugins {
    java
    application
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("mysql:mysql-connector-java:8.0.29")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation(libs.junit)


}

application {
    mainClass = "db_lab.App"
}
