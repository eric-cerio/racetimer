object BuildLibs {
    //Build Plugins
    const val androidPlugin = "com.android.tools.build:gradle:${Versions.androidPluginVersion}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val crashlyticsGradlePlugin = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsGradlePluginVersion}"
    const val dexCountPlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:${Versions.dexCountVersion}"
    const val googlePlayPlugin = "com.google.gms:google-services:${Versions.googlePlayVersion}"
    const val navigationPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationPluginVersion}"
}
