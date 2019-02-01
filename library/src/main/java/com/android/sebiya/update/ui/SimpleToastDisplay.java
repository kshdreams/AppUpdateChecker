package com.android.sebiya.update.ui;

import android.app.Activity;
import android.widget.Toast;
import com.android.sebiya.update.AppUpdateInfo;
import com.android.sebiya.update.install.PackageInstaller;

public class SimpleToastDisplay implements Display {

    @Override
    public void show(final Activity activity, final AppUpdateInfo appUpdateInfo, PackageInstaller installer) {
        Toast.makeText(activity, getMessage(appUpdateInfo), Toast.LENGTH_SHORT).show();
    }

    protected String getMessage(AppUpdateInfo appUpdateInfo) {
        return appUpdateInfo.hasAvailableUpdates() ? "Update " + appUpdateInfo.getServerVersionName()
                + " is available!" : "No updates available";
    }

}
