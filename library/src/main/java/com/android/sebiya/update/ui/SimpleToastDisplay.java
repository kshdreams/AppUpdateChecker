package com.android.sebiya.update.ui;

import android.app.Activity;
import android.widget.Toast;
import com.android.sebiya.update.AppUpdateInfo;

public class SimpleToastDisplay implements Display {

    @Override
    public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {
        Toast.makeText(activity, appUpdateInfo.message, Toast.LENGTH_SHORT).show();
    }
}
