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


    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
            }
        }

    }
}