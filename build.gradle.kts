val jvmVersion = "17"
val jodaMoneyVersion = "1.0.3"
val kotestVersion = "5.6.2"

plugins {
    kotlin("jvm") version "1.8.22"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.joda", "joda-money", jodaMoneyVersion)

    testImplementation("io.kotest", "kotest-runner-junit5", kotestVersion)
    testImplementation("io.kotest", "kotest-property", kotestVersion)
    testImplementation("io.kotest", "kotest-assertions-core", kotestVersion)
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = jvmVersion
        }
    }
    compileTestKotlin {
        kotlinOptions{
            jvmTarget = jvmVersion
        }
    }
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
