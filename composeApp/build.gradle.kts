import org.jetbrains.compose.desktop.application.dsl.TargetFormat
val ktorVersion = "2.0.0"
val coroutinesVersion = "1.5.0-native-mt"
val serializationVersion = "1.6.0"
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("plugin.serialization") version "1.9.24"
    id("app.cash.sqldelight") version "2.0.2"

}

repositories {
    google()
    mavenCentral()
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.example")
        }
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    jvm("desktop")
    
    sourceSets {

        val desktopMain by getting

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test"))
            implementation(kotlin("test-junit5"))
        }

        androidNativeTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test"))
            implementation(kotlin("test-junit5"))
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation("app.cash.sqldelight:android-driver:2.0.2")
            implementation("androidx.compose.material3:material3:1.2.0")

        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-logging:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("io.ktor:ktor-client-apache:$ktorVersion")
            implementation("io.ktor:ktor-client-android:$ktorVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

            implementation("io.github.epicarchitect:calendar-compose-basis:1.0.5")
            implementation("io.github.epicarchitect:calendar-compose-ranges:1.0.5") // includes basis
            implementation("io.github.epicarchitect:calendar-compose-pager:1.0.5") // includes basis
            implementation("io.github.epicarchitect:calendar-compose-datepicker:1.0.5") // includes pager + ranges

            implementation("io.github.vanpra.compose-material-dialogs:core:0.9.0")
            implementation("io.github.vanpra.compose-material-dialogs:datetime:0.9.0")
            implementation("androidx.compose.material3:material3:1.2.0")


        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-cio:$ktorVersion")

            //implementation("app.cash.sqldelight:native-driver:2.0.2")
            implementation("app.cash.sqldelight:sqlite-driver:2.0.2")


        }

    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdkVersion(rootProject.extra["defaultMinSdkVersion"] as Int)
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)

        implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-cio:$ktorVersion")

    }
}
dependencies {
    implementation(libs.material)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.room.common)
    implementation(libs.core.ktx)

    implementation(kotlin("test"))
    implementation(libs.compose.preview.renderer)
    testImplementation("org.testng:testng:6.9.6")
    testImplementation("org.testng:testng:6.9.6")

    // Unit Testing dependencies
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.21")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("org.testng:testng:6.9.6")

    // Android Instrumentation testing dependencies
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("org.testng:testng:6.9.6")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}




