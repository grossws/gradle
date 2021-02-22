plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    // We should use a legacy version to support running on jdk6
    implementation("commons-lang:commons-lang:2.6")
    testImplementation("junit:junit:4.+")
}

// tag::java-cross-compilation[]
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(7))
    }
}
// end::java-cross-compilation[]

tasks.withType<Test>().configureEach {
    systemProperty("targetJavaVersion", project.findProperty("targetJavaVersion"))
}

tasks.register("checkJavadocOutput") {
    dependsOn(tasks.javadoc)
    doLast {
        require(File(project.the<JavaPluginConvention>().docsDir, "javadoc/org/gradle/Person.html").readText().contains("<p>Represents a person.</p>"))
    }
}

tasks.build { dependsOn("checkJavadocOutput") }

