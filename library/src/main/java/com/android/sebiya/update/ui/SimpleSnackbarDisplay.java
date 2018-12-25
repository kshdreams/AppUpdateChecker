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
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(),
                getMessage(appUpdateInfo),
                Snackbar.LENGTH_INDEFINITE);
        if (appUpdateInfo.hasAvailableUpdates()) {
            snackbar.setAction("Update", new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    performUpdate(activity);
                }
            });
        } else {
            snackbar.setAction("Ok", new OnClickListener() {
                @Override
                public void onClick(final View v) {

                }
            });
        }
        snackbar.show();
    }

    protected String getMessage(AppUpdateInfo appUpdateInfo) {
        return appUpdateInfo.hasAvailableUpdates() ? "Update " + appUpdateInfo.getLatestVersionName()
                + " is available!" : "No updates available";
    }

    protected void performUpdate(Activity activity) {
        AppUpdaterUtils.launchGooglePlay(activity, activity.getPackageName());
    }

}
