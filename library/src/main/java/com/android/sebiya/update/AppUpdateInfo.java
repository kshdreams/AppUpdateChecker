package com.android.sebiya.update;

public final class AppUpdateInfo {

    public final boolean hasAvailableUpdates;

    public String lastestVersion;

    public String message;

    private AppUpdateInfo(Builder builder) {
        // TODO : check update available
        hasAvailableUpdates = true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        public AppUpdateInfo build() {
            return new AppUpdateInfo(this);
        }
    }
}
