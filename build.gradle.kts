plugins {
    id("org.jetbrains.intellij") version "0.4.22"
    java
}

group = "com.kagof"
version = "1.5.0"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    testImplementation("junit", "junit", "4.12")
    testImplementation("com.sksamuel.scrimage", "scrimage-core", "4.0.22")
    testImplementation("com.github.Arcnor.apng-writer:apng-writer-core:63699322f5")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2019.3"
}

tasks.getByName<org.jetbrains.intellij.tasks.PublishTask>("publishPlugin") {
    System.getenv("JETBRAINS_REPO_TOKEN")?.let { token(it) }
    System.getenv("PLUGIN_DEPLOYMENT_CHANNEL")?.let { channels(it) }
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    untilBuild(null)
    File("changenotes.html").let {
        if (it.exists() && it.isFile && it.canRead()) changeNotes(it.readText())
        else throw IllegalStateException("unable to read changenotes.html")
    }
    File("description.html").let {
        if (it.exists() && it.isFile && it.canRead()) pluginDescription(it.readText())
        else throw IllegalStateException("unable to read description.html")
    }

}