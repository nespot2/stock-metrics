
tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":adapter:web"))
    implementation(project(":adapter:persistence"))
    implementation(project(":application"))
    implementation(project(":domain"))
    
    testImplementation("org.springframework.boot:spring-boot-webmvc-test")
}