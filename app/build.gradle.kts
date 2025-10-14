plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "app.beacon"
    compileSdk = 35

    defaultConfig {
        applicationId = "app.beacon"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

tasks.register("printCpath") {
  doLast {
    val cp = configurations
      .named("debugRuntimeClasspath")
      .get()
      .resolve()
      .joinToString(":") { it.absolutePath }
    println(cp)
  }
}

tasks.register("extractJarsFromAars") {
  doLast {
    val cp = configurations.named("debugRuntimeClasspath").get().resolve()
    val outputDir = file("$buildDir/extracted-jars")
    outputDir.mkdirs()

    val extractedPaths = cp
      .filter { it.name.endsWith(".aar") }
      .map { aar ->
        val jarPath = outputDir.resolve(aar.name.removeSuffix(".aar") + ".jar")
        val classJar = zipTree(aar).matching { include("classes.jar") }.singleFile
        classJar.copyTo(jarPath, overwrite = true)
        jarPath.absolutePath
      }

    println(extractedPaths.joinToString(":"))
  }
}

tasks.register("printExtractedJarPaths") {
  doLast {
    val cp = configurations.named("debugRuntimeClasspath").get().resolve()
    val outputDir = file("$buildDir/extracted-jars")
    outputDir.mkdirs()

    val extracted = cp
      .filter { it.name.endsWith(".aar") }
      .map { aar ->
        val outJar = outputDir.resolve(aar.name.removeSuffix(".aar") + ".jar")
        val classesJar = zipTree(aar).matching { include("classes.jar") }.singleFile
        classesJar.copyTo(outJar, overwrite = true)
        outJar.absolutePath
      }

    println(extracted.joinToString(":"))
  }
}

tasks.register("printJarPathsOnly") {
  doLast {
    val cp = configurations.named("debugRuntimeClasspath").get().resolve()
    val jarPaths = cp.filter { it.name.endsWith(".jar") }
      .map { it.absolutePath }

    println(jarPaths.joinToString(":"))
  }
}




dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
