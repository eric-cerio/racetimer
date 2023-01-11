object Android {
    // Manifest version information!
    private const val versionMajor = 0
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionBuild = 1 // bump for dogfood builds, public betas, etc.

    const val versionCode =
        versionMajor * 10000 + versionMinor * 1000 + versionPatch * 10 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val compileSdkVersion = 32
    const val targetSdkVersion = 32
    const val minSdkVersion = 26
    const val buildToolsVersion = "29.0.2"
}
