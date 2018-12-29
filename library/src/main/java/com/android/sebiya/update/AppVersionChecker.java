package com.android.sebiya.update;

import com.g00fy2.versioncompare.Version;

public interface AppVersionChecker {

    boolean hasAvailableUpdates(AppUpdateInfo appUpdateInfo);

    AppVersionChecker DEFAULT = new DefaultImpl();

    class DefaultImpl implements AppVersionChecker {

        @Override
        public boolean hasAvailableUpdates(final AppUpdateInfo appUpdateInfo) {
            if (appUpdateInfo.getLatestVersionCode() > 0) {
                return appUpdateInfo.getTargetVersionCode() < appUpdateInfo.getLatestVersionCode();
            }

            if (appUpdateInfo.getTargetVersionName() == null || appUpdateInfo.getLatestVersionName() == null) {
                return false;
            }

            Version version = new Version(appUpdateInfo.getLatestVersionName());
            return version.isHigherThan(appUpdateInfo.getTargetVersionName());
        }
    }
}
