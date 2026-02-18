import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin
import ru.dimanje.inside.buildsrc.MetaInfo

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
}




subprojects {
    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_18)
                plugins.withId("java") {
                    extensions.configure<JavaPluginExtension> {
                        sourceCompatibility = JavaVersion.VERSION_18
                        targetCompatibility = JavaVersion.VERSION_18
                    }
                }

            }
        }
    }

    plugins.matching { it is LibraryPlugin }.whenPluginAdded {
        configure<com.android.build.gradle.LibraryExtension> {
            compileSdk = MetaInfo.COMPILE_SDK

            defaultConfig {
                minSdk = MetaInfo.MIN_SDK
            }
        }
    }
    plugins.withId("com.android.application") {

        extensions.configure<BaseExtension>("android") {
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }
    }
    plugins.withId("com.android.library") {
        extensions.configure<BaseExtension>("android") {
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }
    }
}
