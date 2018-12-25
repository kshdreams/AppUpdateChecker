package com.android.sebiya.update;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.sebiya.update.data.DefaultDataSources;
import com.android.sebiya.update.data.UrlDataSource.Converter;
import com.android.sebiya.update.frequency.DefaultFrequencies;
import com.android.sebiya.update.ui.DefaultDisplays;
import com.android.sebiya.update.ui.SimpleDialogDisplay;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private static final int SERVER_VERSION_CODE = 2;
    private static final int CURRENT_VERSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.dialog_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .withDisplay(DefaultDisplays.DIALOG)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(CURRENT_VERSION_CODE)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();

                appUpdateChecker.start(MainActivity.this);
            }
        });

        findViewById(R.id.snack_bar_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .withDisplay(DefaultDisplays.SNACK_BAR)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(CURRENT_VERSION_CODE)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();

                appUpdateChecker.start(MainActivity.this);
            }
        });

        findViewById(R.id.toast_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .withDisplay(DefaultDisplays.TOAST)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(CURRENT_VERSION_CODE)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();

                appUpdateChecker.start(MainActivity.this);
            }
        });


        findViewById(R.id.custom_dialog_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {

            }
        });

        findViewById(R.id.dialog_no_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .showUiWhenNoUpdates(true)
                        .withDisplay(new SimpleDialogDisplay() {
                            @Override
                            protected String getTitle(final AppUpdateInfo appUpdateInfo) {
                                if (!appUpdateInfo.hasAvailableUpdates()) {
                                    return "No updates!!!";
                                }
                                return super.getTitle(appUpdateInfo);
                            }
                        })
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(SERVER_VERSION_CODE)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();
                appUpdateChecker.start(MainActivity.this);
            }
        });

        findViewById(R.id.snack_bar_no_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .showUiWhenNoUpdates(true)
                        .withDisplay(DefaultDisplays.SNACK_BAR)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(SERVER_VERSION_CODE)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();
                appUpdateChecker.start(MainActivity.this);
            }
        });

        findViewById(R.id.toast_no_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .showUiWhenNoUpdates(true)
                        .withDisplay(DefaultDisplays.TOAST)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(SERVER_VERSION_CODE)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();
                appUpdateChecker.start(MainActivity.this);
            }
        });

        findViewById(R.id.custom_dialog_no_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {

            }
        });

    }

    public static class VersionSample {
        public int latestVersion;
        public String latestVersionName;
    }

    private static class GithubStringToAppUpdateInfoConverter implements Converter {

        private final int targetVersionCode;

        GithubStringToAppUpdateInfoConverter(int targetVersionCode) {
            this.targetVersionCode = targetVersionCode;
        }

        @Override
        public AppUpdateInfo convert(final String data) {
            AppUpdateInfo appUpdateInfo = new AppUpdateInfo(targetVersionCode);
            VersionSample versionSample = new Gson().fromJson(data, VersionSample.class);
            appUpdateInfo.setLatestVersionCode(versionSample.latestVersion);
            appUpdateInfo.setLatestVersionName(versionSample.latestVersionName);
            return appUpdateInfo;
        }
    }
}
