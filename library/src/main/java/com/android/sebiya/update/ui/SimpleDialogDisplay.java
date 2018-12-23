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

    protected AlertDialog.Builder onCreateDialog(final Activity activity, AppUpdateInfo appUpdateInfo) {

        String message = appUpdateInfo.hasAvailableUpdates() ? "Update " + appUpdateInfo.getLatestVersionName()
                + " is available!" : "No updates available";
        String title = appUpdateInfo.hasAvailableUpdates() ? "New update available!" : "No update";

        return new Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("업데이트", new OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        AppUpdaterUtils.launchGooglePlay(activity, activity.getPackageName());
                    }
                })
                .setNegativeButton("취소", new OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {

                    }
                });
    }
}
