fun getVersionDetails(): com.palantir.gradle.gitversion.VersionDetails =
    (extra["versionDetails"] as groovy.lang.Closure<*>)() as com.palantir.gradle.gitversion.VersionDetails

val gitInfo = getVersionDetails()
version = gitInfo.version

repositories {
    mavenCentral()
}

plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.0"
    id("com.palantir.git-version") version "0.12.3"
}

intellij {
    version.set(properties["idea-version"] as String)
    pluginName.set( "Ansible Vault Integration")
    updateSinceUntilBuild.set(false)
    downloadSources.set(true)
    plugins.set(
        listOf("yaml")
    )
}

tasks {
    compileJava {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }

    patchPluginXml {
        setVersion(version)
    }

    publishPlugin {
        dependsOn("patchPluginXML")
        token.set(System.getenv("JB_TOKEN"))
    }
}
