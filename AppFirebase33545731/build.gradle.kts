import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("androidx.navigation.safeargs") version "2.8.4" apply false
    id("com.github.ben-manes.versions") version "0.51.0" apply true
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

// Função para verificar se uma versão é instável
fun isNonStable(version: String): Boolean {
    return listOf("alpha", "beta", "rc", "snapshot").any { keyword ->
        version.contains(keyword, ignoreCase = true)
    }
}

// Função para verificar se uma dependência está na lista de bloqueio
fun isBlockListed(groupAndModule: String): Boolean {
    return listOf(
        "androidx.browser:browser",
        "com.google.android.gms:play-services-auth"
    ).any { keyword ->
        groupAndModule.contains(keyword, ignoreCase = true)
    }
}

tasks.withType<DependencyUpdatesTask> {
    // Usar uma configuração adequada para rejeitar versões
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) || isBlockListed("${candidate.group}:${candidate.module}")) {
                    reject("Rejected due to being unstable or blocklisted")
                }
            }
        }
    }

    checkForGradleUpdate = true
    outputFormatter = "plain"

    // Substituindo `reportFileName` por `outputFile`
    outputFile = file("dependency-updates-report.txt")
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
