package com.android.sebiya.update;

public final class AppUpdateInfo {

    public final boolean hasAvailableUpdates;

    public long latestVersionCode;

    public String latestVersionName;

    public String message;

    public AppUpdateInfo() {
        // TODO : check update available
        hasAvailableUpdates = true;
    }

    public void setLatestVersionCode(final long latestVersionCode) {
        this.latestVersionCode = latestVersionCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
