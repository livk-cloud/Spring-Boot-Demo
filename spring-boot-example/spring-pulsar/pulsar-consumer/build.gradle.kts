plugins {
	com.livk.service
}

dependencies {
	implementation(project(":spring-boot-example:spring-pulsar:pulsar-commons"))
	implementation("org.springframework.boot:spring-boot-starter-web")
}
