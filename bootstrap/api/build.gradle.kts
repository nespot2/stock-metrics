plugins {
    id("org.asciidoctor.jvm.convert")
}

val snippetsDir = file("build/generated-snippets")

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
    baseDirFollowsSourceFile()
    attributes(mapOf("snippets" to snippetsDir.absolutePath))
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":adapter:web"))
    implementation(project(":adapter:persistence"))
    implementation(project(":application"))
    implementation(project(":domain"))
    
    testImplementation("org.springframework.boot:spring-boot-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-restdocs")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

}