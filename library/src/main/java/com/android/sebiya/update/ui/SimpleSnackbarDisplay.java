package com.android.sebiya.update.ui;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.sebiya.update.AppUpdateInfo;
import com.android.sebiya.update.AppUpdaterUtils;

public class SimpleSnackbarDisplay implements Display {

    public SimpleSnackbarDisplay() {
    }

    @Override
    public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {
        String message = appUpdateInfo.hasAvailableUpdates() ? "Update " + appUpdateInfo.getLatestVersionName()
                + " is available!" : "No updates available";
        Snackbar.make(activity.getWindow().getDecorView(),
                message,
                Snackbar.LENGTH_INDEFINITE)
                .setAction("Update", new OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        AppUpdaterUtils.launchGooglePlay(activity, activity.getPackageName());
                    }
                })
                .show();
    }
}
