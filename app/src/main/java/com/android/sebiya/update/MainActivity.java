package com.android.sebiya.update;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.sebiya.update.data.DefaultDataSources;
import com.android.sebiya.update.data.UrlDataSource.Converter;
import com.android.sebiya.update.frequency.DefaultFrequencies;
import com.android.sebiya.update.ui.DefaultDisplays;
import com.android.sebiya.update.ui.Display;
import com.android.sebiya.update.ui.SimpleDialogDisplay;
import com.android.sebiya.update.ui.SimpleSnackbarDisplay;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private static final int SERVER_VERSION_CODE = 2;
    private static final int CURRENT_VERSION_CODE = 1;

    private static final String SERVER_VERSION_NAME = "2.0";
    private static final String CURRENT_VERSION_NAME = "1.0";

    private static final String TIKTOK_GOOGLE_PLAY_VERSION = "4.4.4";
    private static final String TIKTOK_OLD_VERISON = "4.4.3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateCaseWithDialog();
        updateCaseWithSnackBar();
        updateCaseWithToast();
        updateCaseWithCustomDisplayAndGooglePlayDataSource();

        noUpdateCaseWithDialog();
        noUpdateCaseWithSnackBar();
        noUpdateCaseWithToast();
        noUpdateCaseWithCustomDisplayAndGooglePlayDataSource();
    }

    private void noUpdateCaseWithCustomDisplayAndGooglePlayDataSource() {
        findViewById(R.id.custom_dialog_no_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String tiktokPackage = "com.ss.android.ugc.trill";
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .showUiWhenNoUpdates(true)
                        .withDisplay(new Display() {
                            @Override
                            public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {
                                // make custom display such as notification, dialog, snackbar, toast, etc...
                                String message = "TikTok app has " + (appUpdateInfo.hasAvailableUpdates() ? "update" : "no update");
                                if (appUpdateInfo.hasAvailableUpdates()) {
                                    message += "\nlatest version - " + appUpdateInfo.getLatestVersionName();
                                }
                                // make custom display such as notification, dialog, snackbar, toast, etc...
                                new AlertDialog.Builder(activity)
                                        .setTitle("Custom Display")
                                        .setMessage(message)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, final int which) {
                                                if (appUpdateInfo.hasAvailableUpdates()) {
                                                    AppUpdaterUtils.launchGooglePlay(MainActivity.this, tiktokPackage);
                                                }
                                            }
                                        }).show();
                            }
                        })
                        .withDataSource(DefaultDataSources.googlePlayDataSource(tiktokPackage, "4.4.4"))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();

                appUpdateChecker.start(MainActivity.this);
            }
        });
    }

    private void noUpdateCaseWithToast() {
        findViewById(R.id.toast_no_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .showUiWhenNoUpdates(true)
                        .withDisplay(DefaultDisplays.TOAST)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(SERVER_VERSION_CODE, SERVER_VERSION_NAME)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();
                appUpdateChecker.start(MainActivity.this);
            }
        });
    }

    private void noUpdateCaseWithSnackBar() {
        findViewById(R.id.snack_bar_no_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .showUiWhenNoUpdates(true)
                        .withDisplay(DefaultDisplays.SNACK_BAR)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(SERVER_VERSION_CODE, SERVER_VERSION_NAME)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();
                appUpdateChecker.start(MainActivity.this);
            }
        });
    }

    private void noUpdateCaseWithDialog() {
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
                                new GithubStringToAppUpdateInfoConverter(SERVER_VERSION_CODE, SERVER_VERSION_NAME)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();
                appUpdateChecker.start(MainActivity.this);
            }
        });
    }

    private void updateCaseWithCustomDisplayAndGooglePlayDataSource() {
        findViewById(R.id.custom_dialog_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String tiktokPackage = "com.ss.android.ugc.trill";
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .withDisplay(new Display() {
                            @Override
                            public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {
                                String message = "TikTok app has " + (appUpdateInfo.hasAvailableUpdates() ? "update" : "no update");
                                if (appUpdateInfo.hasAvailableUpdates()) {
                                    message += "\nlatest version - " + appUpdateInfo.getLatestVersionName();
                                }
                                // make custom display such as notification, dialog, snackbar, toast, etc...
                                new AlertDialog.Builder(activity)
                                        .setTitle("Custom Display")
                                        .setMessage(message)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, final int which) {
                                                if (appUpdateInfo.hasAvailableUpdates()) {
                                                    AppUpdaterUtils.launchGooglePlay(MainActivity.this, tiktokPackage);
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, final int which) {

                                            }
                                        })
                                        .show();
                            }
                        })
                        .withDataSource(DefaultDataSources.googlePlayDataSource(tiktokPackage, TIKTOK_OLD_VERISON))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();

                appUpdateChecker.start(MainActivity.this);
            }
        });
    }

    private void updateCaseWithToast() {
        findViewById(R.id.toast_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .withDisplay(DefaultDisplays.TOAST)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(CURRENT_VERSION_CODE, CURRENT_VERSION_NAME)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();

                appUpdateChecker.start(MainActivity.this);
            }
        });
    }

    private void updateCaseWithDialog() {
        findViewById(R.id.dialog_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .withDisplay(DefaultDisplays.DIALOG)
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(CURRENT_VERSION_CODE, CURRENT_VERSION_NAME)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();

                appUpdateChecker.start(MainActivity.this);
            }
        });
    }

    private void updateCaseWithSnackBar() {
        findViewById(R.id.snack_bar_update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppUpdateChecker appUpdateChecker = AppUpdateChecker
                        .builder()
                        .withDisplay(new SimpleSnackbarDisplay(findViewById(R.id.snack_bar_update)))
                        .withDataSource(DefaultDataSources.urlDataSource(
                                "https://raw.githubusercontent.com/kshdreams/AppUpdateChecker/master/version_sample.json",
                                new GithubStringToAppUpdateInfoConverter(CURRENT_VERSION_CODE, CURRENT_VERSION_NAME)))
                        .withFrequency(DefaultFrequencies.EVERY_TIME)
                        .withLifeCycle(MainActivity.this)
                        .build();

                appUpdateChecker.start(MainActivity.this);
            }
        });
    }

    public static class VersionSample {
        public int latestVersion;
        public String latestVersionName;
    }

    private static class GithubStringToAppUpdateInfoConverter implements Converter {

        private final int targetVersionCode;
        private final String targetVersionName;

        GithubStringToAppUpdateInfoConverter(int targetVersionCode, String targetVersionName) {
            this.targetVersionCode = targetVersionCode;
            this.targetVersionName = targetVersionName;
        }

        @Override
        public AppUpdateInfo convert(final String data) {
            AppUpdateInfo appUpdateInfo = new AppUpdateInfo(targetVersionCode, targetVersionName);
            VersionSample versionSample = new Gson().fromJson(data, VersionSample.class);
            appUpdateInfo.setLatestVersionCode(versionSample.latestVersion);
            appUpdateInfo.setLatestVersionName(versionSample.latestVersionName);
            return appUpdateInfo;
        }
    }
}
