import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            
            // SQLDelight Android
            implementation(libs.sqldelight.android)
            
            // Koin Android
            implementation(libs.koin.android)
            
            // Coroutines Android
            implementation(libs.kotlinx.coroutines.android)
        }
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.navigation.compose)
            implementation("io.insert-koin:koin-compose-viewmodel:4.0.0")
            
            // SQLDelight
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)
            
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            
            // Lifecycle ViewModel
            implementation(libs.lifecycle.viewmodel)
            
            // DateTime
            implementation(libs.kotlinx.datetime)
        }
        
        iosMain.dependencies {
            // SQLDelight Native
            implementation(libs.sqldelight.native)
        }
    }
}

android {
    namespace = "com.example.mifinca"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mifinca"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

sqldelight {
    databases {
        create("MiFincaDatabase") {
            packageName.set("com.example.mifinca.database")
            srcDirs.setFrom("src/commonMain/sqldelight")
        }
    }
}
