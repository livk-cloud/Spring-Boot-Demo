plugins {
	com.livk.service
}

dependencies {
	implementation(project(":spring-boot-example:spring-rabbit:rabbit-commons"))
	implementation("org.springframework.boot:spring-boot-starter-web")
}
