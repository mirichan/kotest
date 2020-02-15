import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
   id("java")
   kotlin("jvm")
   id("java-library")
   id("org.jetbrains.kotlin.plugin.spring") version "1.3.61"
   id("com.adarshr.test-logger")
}

repositories {
   mavenCentral()
}

dependencies {
   implementation(project(":kotest-core"))
   implementation(project(":kotest-assertions"))
   implementation(project(":kotest-runner:kotest-runner-jvm"))
   implementation(kotlin("stdlib-jdk8"))
   implementation(kotlin("reflect"))
   implementation("org.springframework:spring-test:5.2.2.RELEASE")
   implementation("org.springframework:spring-context:5.2.2.RELEASE")
   implementation("net.bytebuddy:byte-buddy:1.10.7")

   testImplementation(project(":kotest-runner:kotest-runner-junit5"))
   testImplementation("org.springframework.boot:spring-boot-starter-test:2.2.2.RELEASE")
}

tasks.withType<KotlinCompile>().configureEach {
   kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
}

tasks.named<Test>("test") {
   useJUnitPlatform()
   filter {
      isFailOnNoMatchingTests = false
   }
   testLogging {
      showExceptions = true
      showStandardStreams = true
      events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)
      exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
   }
}

apply(from = "../../publish.gradle")
