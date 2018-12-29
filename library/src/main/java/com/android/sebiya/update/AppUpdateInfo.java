package com.android.sebiya.update;

public final class AppUpdateInfo {

    private long latestVersionCode;

    private String latestVersionName;

    private long targetVersionCode;
    private String targetVersionName;

    private boolean hasAvailableUpdates;

    public AppUpdateInfo(int targetVersionCode, String targetVersionName) {
        this.targetVersionCode = targetVersionCode;
        this.targetVersionName = targetVersionName;
    }

    public long getTargetVersionCode() {
        return targetVersionCode;
    }

    public String getTargetVersionName() {
        return targetVersionName;
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

    public void setHasAvailableUpdates(final boolean hasAvailableUpdates) {
        this.hasAvailableUpdates = hasAvailableUpdates;
    }

    public boolean hasAvailableUpdates() {
        return hasAvailableUpdates;
    }
}
