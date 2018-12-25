package com.android.sebiya.update;

import android.text.TextUtils;

public final class AppUpdateInfo {

    private long latestVersionCode;

    private String latestVersionName;

    private long targetVersionCode;
    private String targetVersionName;

    public AppUpdateInfo(int targetVersionCode, String targetVersionName) {
        this.targetVersionCode = targetVersionCode;
        this.targetVersionName = targetVersionName;
    }

    public void setLatestVersionCode(final long latestVersionCode) {
        this.latestVersionCode = latestVersionCode;
    }

    public long getLatestVersionCode() {
        return latestVersionCode;
    }

    public String getLatestVersionName() {
        return latestVersionName;
    }

    public void setLatestVersionName(final String latestVersionName) {
        this.latestVersionName = latestVersionName;
    }

    public boolean hasAvailableUpdates() {
        if (getLatestVersionCode() > 0) {
            return targetVersionCode < getLatestVersionCode();
        }

        // TODO : real version check
        return !TextUtils.equals(targetVersionName, getLatestVersionName());
    }
}
