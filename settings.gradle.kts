rootProject.name = "stock-metrics"


include(
    "bootstrap:api",
    "adapter:persistence",
    "adapter:web",
    "application",
    "domain"
)

pluginManagement {
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings

    plugins {
        id("org.asciidoctor.jvm.convert") version "4.0.4"
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
            }
        }

    }
}