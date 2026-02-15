subprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "java-library")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    tasks.withType<Test>().configureEach {
        failOnNoDiscoveredTests = false
    }
}
