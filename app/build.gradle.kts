plugins {
    application
    id("java")
    id("com.github.ben-manes.versions") version "0.52.0"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("hexlet.code.App")
}


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.13.0-M2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.0-M2")
    implementation("info.picocli:picocli:4.7.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.1")
}

tasks.test {
    useJUnitPlatform()
}