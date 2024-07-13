pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "IvaTask3"
include(":app")
include(":build-logic")
include(":core:data")
include(":core:datastore")
include(":core:designsystem")
include(":core:domain")
include(":core:firebase")
include(":core:model")
include(":core:network")
include(":feature:AnalyseWebPage")
