package com.android.sebiya.update.ui;

import android.app.Activity;
import com.android.sebiya.update.AppUpdateInfo;

public class DefaultDisplays {

    public static Display TOAST = new SimpleToastDisplay();

    public static Display DIALOG = new SimpleDialogDisplay();

    public static Display SNACK_BAR = new SimpleSnackbarDisplay();

    // TODO : impl
    public static Display NOTIFICATION = new Display() {
        @Override
        public void show(final Activity activity, final AppUpdateInfo appUpdateInfo) {

        }
    };
}
