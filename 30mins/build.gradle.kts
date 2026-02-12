plugins {
	java
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":engine"))
}

tasks.test {
	failOnNoDiscoveredTests = false;
}