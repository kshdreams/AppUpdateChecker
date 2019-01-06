package com.android.sebiya.update.ui;

import android.app.Activity;
import com.android.sebiya.update.AppUpdateInfo;
import com.android.sebiya.update.install.PackageInstaller;

public interface Display {

    void show(Activity activity, AppUpdateInfo appUpdateInfo, PackageInstaller installer);
}
