plugins {
	java
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

val lwjglVersion = "3.4.1"
val steamworks4jVersion = "1.10.0"
val jomlVersion = "1.10.8"
val steamworks4jserverVersion = "1.10.0"
val lwjglNatives = "natives-windows"

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

	implementation("org.lwjgl:lwjgl")
	implementation("org.lwjgl:lwjgl-bgfx")
	implementation("org.lwjgl:lwjgl-glfw")
	implementation("org.lwjgl:lwjgl-nanovg")
	implementation("org.lwjgl:lwjgl-nfd")
	implementation("org.lwjgl:lwjgl-nuklear")
	implementation("org.lwjgl:lwjgl-openal")
	implementation("org.lwjgl:lwjgl-opengl")
	implementation("org.lwjgl:lwjgl-opus")
	implementation("org.lwjgl:lwjgl-par")
	implementation("org.lwjgl:lwjgl-stb")
	implementation("org.lwjgl:lwjgl-vulkan")
	implementation("org.lwjgl:lwjgl::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-bgfx::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-glfw::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-nanovg::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-nfd::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-nuklear::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-openal::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-opengl::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-opus::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-par::$lwjglNatives")
	implementation("org.lwjgl:lwjgl-stb::$lwjglNatives")
	implementation("com.code-disaster.steamworks4j:steamworks4j:${steamworks4jVersion}")
	implementation("org.joml:joml:$jomlVersion")
	implementation("com.code-disaster.steamworks4j:steamworks4j-server:${steamworks4jserverVersion}")

	// Source: https://mvnrepository.com/artifact/com.google.code.gson/gson
	implementation("com.google.code.gson:gson:2.13.2")
	// Source: https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
	implementation("org.apache.logging.log4j:log4j-core:2.25.3")
	annotationProcessor("org.apache.logging.log4j:log4j-core:2.25.3")
}

tasks.test {
	failOnNoDiscoveredTests = false;
}