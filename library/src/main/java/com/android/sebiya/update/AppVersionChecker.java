package com.android.sebiya.update;

import android.text.TextUtils;

public interface AppVersionChecker {

    boolean hasAvailableUpdates(AppUpdateInfo appUpdateInfo);

    AppVersionChecker DEFAULT = new DefaultImpl();

    class DefaultImpl implements AppVersionChecker {

        @Override
        public boolean hasAvailableUpdates(final AppUpdateInfo appUpdateInfo) {
            if (appUpdateInfo.getLatestVersionCode() > 0) {
                return appUpdateInfo.getTargetVersionCode() < appUpdateInfo.getLatestVersionCode();
            }

            // TODO : real version check
            return !TextUtils.equals(appUpdateInfo.getTargetVersionName(), appUpdateInfo.getLatestVersionName());
        }
    }
}
