package com.android.sebiya.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.android.sebiya.update.AppVersionChecker.DefaultImpl;

public final class AppUpdateInfo {

    private static final String LOG_TAG = "AppUpdateInfo";

    private Builder mBuilder;

    private AppUpdateInfo(Builder builder) {
        mBuilder = builder;
    }

    public long getCurrentVersionCode() {
        return mBuilder.currentVersionCode;
    }

    public String getCurrentVersionName() {
        return mBuilder.currentVersionName;
    }

    public long getServerVersionCode() {
        return mBuilder.serverVersionCode;
    }

    public String getServerVersionName() {
        return mBuilder.serverVersionName;
    }

    public long getForceUpdateVersionCode() {
        return mBuilder.forceUpdateVersionCode;
    }

    public String getForceUpdateVersionName() {
        return mBuilder.forceUpdateVersionName;
    }


    public boolean hasAvailableUpdates() {
        return mBuilder.appVersionChecker.hasAvailableUpdates(this);
    }

    public boolean hasForceUpdates() {
        return mBuilder.appVersionChecker.hasForceUpdates(this);
    }

    public static class Builder {

        private long serverVersionCode;
        private String serverVersionName;
        private long currentVersionCode;
        private String currentVersionName;
        private String forceUpdateVersionName;
        private long forceUpdateVersionCode;
        private AppVersionChecker appVersionChecker;
        private final Context context;


        public Builder(@Nullable Context context) {
            this.context = context;
        }

        public Builder withVersionChecker(AppVersionChecker checker) {
            appVersionChecker = checker;
            return this;
        }

        public Builder withCurrentVersion(long code, String name) {
            currentVersionCode = code;
            currentVersionName = name;
            return this;
        }

        public Builder withServerVersion(long code, String name) {
            serverVersionCode = code;
            serverVersionName = name;
            return this;
        }

        public Builder withForceUpdate(long code, String name) {
            forceUpdateVersionCode = code;
            forceUpdateVersionName = name;
            return this;
        }

        public AppUpdateInfo build() {
            if (appVersionChecker == null) {
                appVersionChecker = new DefaultImpl();
            }
            if (currentVersionCode == 0 || currentVersionName == null) {
                if (context == null) {
                    throw new IllegalArgumentException("context or current version code should be set");
                }
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    currentVersionName = packageInfo.versionName;
                    currentVersionCode = packageInfo.versionCode;
                    Log.i(LOG_TAG, "build. name - " + currentVersionCode + ", code - " + currentVersionCode);
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return new AppUpdateInfo(this);
        }
    }
}
