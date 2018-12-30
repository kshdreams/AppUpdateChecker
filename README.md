# AppUpdateChecker

A simple library that checks your app have updates on Google Play or your own server.

[![](https://jitpack.io/v/kshdreams/AppUpdateChecker.svg)](https://jitpack.io/#kshdreams/AppUpdateChecker)

### GETTING STARTED
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

### Usage
By default, the basic usage will show default dialog when new version is found on the Play Store.
By calling .start() method, the library will work in background and you can cancel it by calling .stop()

```java
AppUpdateChecker appUpdateChecker = AppUpdateChecker
        .builder()
        .withDisplay(DefaultDisplays.DIALOG) // snack bar, toast, or can customize by implmenting Display interface
        .withDataSource(DefaultDataSources.googlePlayDataSource("com.android.sebiya.update" /* your package name */, "1.0" /* current app version name */))
        .withFrequency(DefaultFrequencies.EVERY_TIME) // can control frequency of app update check
        .withLifeCycle(MainActivity.this) // automatically cancel when activity destroying.
        .build();

appUpdateChecker.start(MainActivity.this);
```


## Customizations
### Display
provide 3 types of display by default. Dialog, SnackBar, Toast.
but you can implements custom ui by implementing Display interface.
```java
AppUpdateChecker.builder()
    .withDisplay(DefaultDisplays.DIALOG)
    .withDisplay(DefaultDisplays.TOAST)
    .withDisplay(DefaultDisplays.SNACK_BAR)
    .withDisplay(new Display() {
        @Override
        public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {
            // custom notification, dialog, etc
        }
     })
```

### DataSource
provide 2 types of data source by default.
- GooglePlay : we retrieve versionName from google play detail pages of given packages. (we're using jsoup open source library(https://github.com/jhy/jsoup) to parse html page)
- Url : we download xml or json file from given url. you must implements Conveter to make version info from plain string.
or you can implements DataSource interface to get latest version. (ex> Firebase RemoteConfig)
```java
AppUpdateChecker.builder()
    .withDataSource(DefaultDataSources.googlePlayDataSource("com.android.sebiya.update" /* your package name */, "1.0" /* current app version name */))
    .withDataSource(DefaultDataSources.urlDataSource(
            "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json", 
            new Converter() {
                 @Override
                 public AppUpdateInfo convert(final String data) {
                    // parse json or xml and make version info
                    return new AppUpdateInfo(versionCode, versionName);
                 }
            })))
```

### Frequency
you can control how often check app update by calling .withFrequency()
```java
AppUpdateChecker.builder()
    .withFrequency(DefaultFrequencies.EVERY_TIME)
    .withFrequency(DefaultFrequencies.EVERY_DAY)
    .withFrequency(new Frequency() {
        @Override
        public boolean shouldCheckUpdate(final Context context) {
            // TODO : return whether should check update or not.
            return false;
        }
    })    
```

### Lifecycle
if you set LifecycleOwner we automatically cancel update check when Activity destroying.
otherwise you should call .stop() method when ui destroying.
```java
AppUpdateChecker.builder()
    .withLifeCycle(MainActivity.this)
```

### Version Compare
by default we're using "https://github.com/G00fY2/version-compare" library to compare version name
you could customize version compare logic by implements AppVersionChecker
```java
AppUpdateChecker.builder()
      .withVersionChecker(new AppVersionChecker() {
          @Override
          public boolean hasAvailableUpdates(final AppUpdateInfo appUpdateInfo) {
              // TODO : compare version code
              return false;
          }
      })
```


## License
```
Copyright 2018 kshdreams

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```