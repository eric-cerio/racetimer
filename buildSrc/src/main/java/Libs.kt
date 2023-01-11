object Libs {

    // Libs
    // https://proandroiddev.com/gradle-dependency-management-with-kotlin-94eed4df9a28
    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"

    // Support
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardViewVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val supportDesign =
        "com.google.android.material:material:${Versions.materialDesignVersion}"

    const val coordinatorLayout =
        "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorLayoutVersion}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityVersion}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"

    // Architecture Components
    const val archExtensionsCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.archCompVersion}"
    const val archRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.archCompVersion}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.archCompVersion}"
    const val archLifeCycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.archCompVersion}"
    const val viewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archCompVersion}"
    const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomRx = "androidx.room:room-rxjava2:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    // Security Crypto
    const val securityCrypto = "androidx.security:security-crypto:${Versions.securityCryptoVersion}"

    // SQLCipher
    const val sqlcipher = "net.zetetic:android-database-sqlcipher:${Versions.sqlChipherVersion}"
    const val sqlite = "androidx.sqlite:sqlite:${Versions.sqliteVersion}"

    // Navigation Components
    const val navigationFragments =
        "androidx.navigation:navigation-fragment:${Versions.navigationVersion}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationFragmentUI =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    // Core-KTX
    const val coreKTX = "androidx.core:core-ktx:${Versions.coreKtxVersion}"

    // Google Maps
    const val playServicesMaps =
        "com.google.android.gms:play-services-maps:${Versions.playServicesMapsVersion}"
    const val playServicesPlaces =
        "com.google.android.gms:play-services-places:${Versions.playServicesPlacesVersion}"
    const val playServicesLocation =
        "com.google.android.gms:play-services-location:${Versions.playServicesLocationVersion}"
    const val playServicesAuth =
        "com.google.android.gms:play-services-auth:${Versions.playServicesAuthVersion}"

    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val retrofitScalar =
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofitScalarVersion}"

    // OkHttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val okhttpLogging =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingVersion}"

    // RxJava
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2Version}"

    // RxKotlin
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlinVersion}"

    // RxAndroid
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"

    // RxBinding
    const val rxbindingAndroidX =
        "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBindingXVersion}"
    const val rxbindingAndroidXCore =
        "com.jakewharton.rxbinding3:rxbinding-core:${Versions.rxBindingXVersion}"
    const val rxbindingAndroidXAppCompat =
        "com.jakewharton.rxbinding3:rxbinding-appcompat:${Versions.rxBindingXVersion}"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.daggerVersion}"
    const val daggerSupport = "com.google.dagger:dagger-android-support:${Versions.daggerVersion}"
    const val daggerProcessor =
        "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}"

    // MultiDex
    const val multiDex = "androidx.multidex:multidex:${Versions.multiDexVersion}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    // GlassFish
    const val glassFishAnnotation = "org.glassfish:javax.annotation:${Versions.glassFishVersion}"

    // Epoxy
    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxyVersion}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.epoxyVersion}"

    // CircleImageView
    const val circleImageView = "de.hdodenhof:circleimageview:${Versions.circleImageViewVersion}"

    // SnapHelper
    const val snapHelperPlugin =
        "com.github.rubensousa:gravitysnaphelper:${Versions.snapHelperVersion}"

    // RxPermissions
    const val runtimePermission =
        "com.github.tbruyelle:rxpermissions:${Versions.runtimePermissionVersion}"

    // Stetho
    const val stetho = "com.facebook.stetho:stetho:${Versions.stethoVersion}"

    // Gson
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"

    // PreferenceManager
    val preference = "androidx.preference:preference-ktx:${Versions.preferenceManagerVersion}"

    // Facebook SDK
    const val facebookSdk = "com.facebook.android:facebook-login:${Versions.facebookSdkVersion}"
    const val facebookShimmer = "com.facebook.shimmer:shimmer:${Versions.facebookShimmerVersion}"

    // ViewPager2
    const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2Version}"

    // firebase
    const val crashlytics = "com.google.firebase:firebase-crashlytics"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
    const val firebaseBoM = "com.google.firebase:firebase-bom:${Versions.firebaseBoMVersion}"


    // uCrop
    const val uCrop = "com.github.yalantis:ucrop:${Versions.uCropVersion}"

    // PhotoView
    val photoView = "com.github.chrisbanes:PhotoView:${Versions.photoViewVersion}"
    val touchImageView = "com.github.MikeOrtiz:TouchImageView:${Versions.touchImageViewVersion}"

    // libphonenumber
    const val libphonenumber =
        "com.googlecode.libphonenumber:libphonenumber:${Versions.libphonenumberVersion}"

    // stepsIndicator
    const val stepsIndicator =
        "au.com.appetiser.baseplate:stepsindicatorview:${Versions.stepsIndicatorVersion}"

    // Country code
    const val countryCode = "com.hbb20:ccp:${Versions.countryCodeVersion}"

    // Stripe
    const val stripe = "com.stripe:stripe-android:${Versions.stripeVersion}"

    // Paging3
    const val paging = "androidx.paging:paging-runtime:${Versions.pagingVersion}"
    const val rxPaging = "androidx.paging:paging-rxjava2:${Versions.pagingVersion}"

    // Timeago
    const val timeAgo = "com.github.marlonlom:timeago:${Versions.timeAgoVersion}"

    // Matisse
    const val matisse = "com.zhihu.android:matisse:${Versions.matisseVersion}"

    // Swipe Refresh Layout
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

    //Biometric
    const val biometrics = "androidx.biometric:biometric:${Versions.biometric}"

    //SignalR
    const val signalR = "com.microsoft.signalr:signalr:${Versions.signalR}"
    const val rxAnimation = "com.mikhaellopez:rxanimation:${Versions.rxAnimation}"

    // Barcode model dependencies
    const val barcodeScanner = "com.google.mlkit:barcode-scanning:${Versions.barcodeScanner}"

    // CameraX dependencies
    const val camera2 = "androidx.camera:camera-camera2:${Versions.camera}"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.camera}"
    const val cameraView = "androidx.camera:camera-view:${Versions.cameraView}"

    // Chucker
    const val chucker = "com.github.chuckerteam.chucker:library:3.5.2"
    const val chuckerNoOp = "com.github.chuckerteam.chucker:library-no-op:3.5.2"

    // Shake Detector
    const val shakeDetector = "com.squareup:seismic:1.0.3"
    //EML Payments
    const val emlPayments = "com.emlpayments.sdk:eml-sdk:${Versions.emlPaymentsVersion}"

    // Lottie
    const val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"
    const val dotLottie = "com.github.dotlottie:dotlottieloader-android:${Versions.dotLottieVersion}"

    // Emoji
    const val emoji = "androidx.emoji2:emoji2:${Versions.emojiSupportVersion}"
    const val emojiViews = "androidx.emoji2:emoji2-views:${Versions.emojiSupportVersion}"
    const val emojiBundles = "androidx.emoji2:emoji2-bundled:${Versions.emojiSupportVersion}"

    // SMS retriever
    const val smsRetrieverAPi = "com.google.android.gms:play-services-auth-api-phone:${Versions.smsRetrieverApiVersion}"
    const val smsRetriever = "com.google.android.gms:play-services-auth:${Versions.smsRetrieverVersion}"

    //  One Signal
    const val oneSignal = "com.onesignal:OneSignal:${Versions.oneSignalVersion}"
}
