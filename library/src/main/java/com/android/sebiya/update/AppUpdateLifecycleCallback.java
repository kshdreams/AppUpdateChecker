package com.android.sebiya.update;

public interface AppUpdateLifecycleCallback {

    void onStart();

    void onDataLoaded(AppUpdateInfo info);

    void onDisplayShowing(AppUpdateInfo info);
}
