package com.android.sebiya.update.ui;

import android.app.Activity;
import android.widget.Toast;
import com.android.sebiya.update.AppUpdateInfo;

public class SimpleToastDisplay implements Display {

    @Override
    public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {
        String message = appUpdateInfo.hasAvailableUpdates() ? "Update " + appUpdateInfo.getLatestVersionName()
                + " is available!" : "No updates available";
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
