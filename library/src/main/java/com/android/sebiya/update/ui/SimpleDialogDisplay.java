package com.android.sebiya.update.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import com.android.sebiya.update.AppUpdateInfo;
import com.android.sebiya.update.install.PackageInstaller;

public class SimpleDialogDisplay implements Display {

    public SimpleDialogDisplay() {

    }

    @Override
    public void show(final Activity activity, final AppUpdateInfo appUpdateInfo, PackageInstaller installer) {
        onCreateDialog(activity, appUpdateInfo, installer).show();
    }

    protected String getTitle(AppUpdateInfo appUpdateInfo) {
        return appUpdateInfo.hasAvailableUpdates() ? "New update available!" : "No update";
    }

    protected String getMessage(AppUpdateInfo appUpdateInfo) {
        return appUpdateInfo.hasAvailableUpdates() ? "Update " + appUpdateInfo.getServerVersionName()
                + " is available!" : "No updates available";
    }

    protected void performUpdate(Activity activity, PackageInstaller installer) {
        installer.install(activity);
    }

    protected AlertDialog.Builder onCreateDialog(final Activity activity, AppUpdateInfo appUpdateInfo, final PackageInstaller installer) {
        Builder builder = new Builder(activity)
                .setTitle(getTitle(appUpdateInfo))
                .setMessage(getMessage(appUpdateInfo))
                .setCancelable(false);
        if (appUpdateInfo.hasAvailableUpdates()) {
            builder
                    .setPositiveButton("Update", new OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            performUpdate(activity, installer);
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
