plugins {
	id("org.jetbrains.kotlin.jvm") version "1.9.25"
	id("org.jetbrains.kotlin.plugin.spring") version "1.9.25"
	id("org.jetbrains.kotlin.kapt") version "1.9.25" // Corregido para usar el plugin de kapt
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql:42.6.1")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.springframework.boot:spring-boot-starter-webflux")


	// Dependencias de seguridad
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Dependencias actualizadas de JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// Dependencias de Firebase Admin
	implementation("com.google.firebase:firebase-admin:8.1.0")

	//JSON
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")


	// Dependencias de pruebas
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Dependencias de MapStruct
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.jar {
	manifest {
		attributes["Main-Class"] = "com.example.bite_api.BiteApplication"
	}
}


tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	mainClass.set("com.example.bite_api.BiteApplicationKt")
}


tasks.jar {
	enabled = false
}
