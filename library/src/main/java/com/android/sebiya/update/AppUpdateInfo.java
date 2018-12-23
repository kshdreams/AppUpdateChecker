package com.android.sebiya.update;

public final class AppUpdateInfo {

    private long latestVersionCode;

    private String latestVersionName;

    private long targetVersionCode;

    public AppUpdateInfo(int targetVersionCode) {
        this.targetVersionCode = targetVersionCode;
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
        return targetVersionCode < getLatestVersionCode();
    }
}
