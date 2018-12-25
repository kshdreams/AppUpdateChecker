package com.android.sebiya.update.ui;

import android.app.Activity;
import android.widget.Toast;
import com.android.sebiya.update.AppUpdateInfo;

public class SimpleToastDisplay implements Display {

    @Override
    public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {
        Toast.makeText(activity, getMessage(appUpdateInfo), Toast.LENGTH_SHORT).show();
    }

    protected String getMessage(AppUpdateInfo appUpdateInfo) {
        return appUpdateInfo.hasAvailableUpdates() ? "Update " + appUpdateInfo.getLatestVersionName()
                + " is available!" : "No updates available";
    }

}
