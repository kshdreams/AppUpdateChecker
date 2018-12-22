package com.android.sebiya.update.data;

import com.android.sebiya.update.AppUpdateInfo;

public interface DataSource {
    AppUpdateInfo load();
}
