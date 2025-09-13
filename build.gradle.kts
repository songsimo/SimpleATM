plugins {
    id("java")
}

group = "com.simo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.6.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.19.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.19.0")
}

tasks.test {
    useJUnitPlatform()
}