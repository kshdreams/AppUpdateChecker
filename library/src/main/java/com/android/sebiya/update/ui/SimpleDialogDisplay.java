package com.android.sebiya.update.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import com.android.sebiya.update.AppUpdateInfo;
import com.android.sebiya.update.AppUpdaterUtils;

public class SimpleDialogDisplay implements Display {

    public SimpleDialogDisplay() {

    }

    @Override
    public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {
        onCreateDialog(activity, appUpdateInfo).show();
    }

    protected String getTitle(AppUpdateInfo appUpdateInfo) {
        return appUpdateInfo.hasAvailableUpdates() ? "New update available!" : "No update";
    }

    protected String getMessage(AppUpdateInfo appUpdateInfo) {
        return appUpdateInfo.hasAvailableUpdates() ? "Update " + appUpdateInfo.getLatestVersionName()
                + " is available!" : "No updates available";
    }

    protected void performUpdate(Activity activity) {
        AppUpdaterUtils.launchGooglePlay(activity, activity.getPackageName());
    }

    protected AlertDialog.Builder onCreateDialog(final Activity activity, AppUpdateInfo appUpdateInfo) {
        Builder builder = new Builder(activity)
                .setTitle(getTitle(appUpdateInfo))
                .setMessage(getMessage(appUpdateInfo))
                .setCancelable(false);
        if (appUpdateInfo.hasAvailableUpdates()) {
            builder
                    .setPositiveButton("Update", new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            performUpdate(activity);
}
                    })
                    .setNegativeButton("Cancel", new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {

                        }
                    });
        } else {
            builder.setPositiveButton("Ok", new OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {

                }
            });
        }

        return builder;
    }
}
