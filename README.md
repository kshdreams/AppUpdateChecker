# AppUpdateChecker

A simple library that checks your app have updates on Google Play or your own server.


##### GETTING STARTED
AppUpdateChecker releases are available via JitPack.
```ruby
// Project level build.gradle
// ...
repositories {
    maven { url 'https://jitpack.io' }
}
// ...

// Module level build.gradle
dependencies {
implementation 'com.github.kshdreams:AppUpdateChecker:0.0.1'
}
```

#### Usage
By default, the basic usage will show default dialog when new version is found on the Play Store.
By calling .start() method, the library will work in background and you can cancel it by calling .stop()

```
AppUpdateChecker appUpdateChecker = AppUpdateChecker
        .builder()
        .withDisplay(DefaultDisplays.DIALOG) // snack bar, toast, or can customize by implmenting Display interface
        .withDataSource(DefaultDataSources.googlePlayDataSource("com.android.sebiya.update" /* your package name */, "1.0" /* current app version name */))
        .withFrequency(DefaultFrequencies.EVERY_TIME) // can control frequency of app update check
        .withLifeCycle(MainActivity.this) // automatically cancel when activity destroying.
        .build();

appUpdateChecker.start(MainActivity.this);
```
