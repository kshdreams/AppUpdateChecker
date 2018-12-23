package com.android.sebiya.update;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.android.sebiya.update.data.DefaultDataSources;
import com.android.sebiya.update.data.UrlDataSource.Converter;
import com.android.sebiya.update.frequency.DefaultFrequencies;
import com.android.sebiya.update.ui.DefaultDisplays;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppUpdateChecker appUpdateChecker = AppUpdateChecker
                .builder()
                .showUiWhenNoUpdates(true)
                .withDisplay(DefaultDisplays.SNACK_BAR)
                .withDataSource(DefaultDataSources.urlDataSource(
                        "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                        new Converter() {
                            @Override
                            public AppUpdateInfo convert(final String data) {
                                AppUpdateInfo appUpdateInfo = new AppUpdateInfo(2);
                                VersionSample versionSample = new Gson().fromJson(data, VersionSample.class);
                                appUpdateInfo.setLatestVersionCode(versionSample.latestVersion);
                                appUpdateInfo.setLatestVersionName(versionSample.latestVersionName);
                                return appUpdateInfo;
                            }
                        }))
                .withFrequency(DefaultFrequencies.EVERY_TIME)
                .withLifeCycle(this)
                .build();

        appUpdateChecker.start(this);
    }

    public static class VersionSample {
        public int latestVersion;
        public String latestVersionName;
    }
}
