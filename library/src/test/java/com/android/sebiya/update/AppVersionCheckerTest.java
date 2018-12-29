package com.android.sebiya.update;

import org.junit.*;

public class AppVersionCheckerTest {

    @Test
    public void test_100_update_true_with_large_version_code() {
        AppUpdateInfo appUpdateInfo = new AppUpdateInfo(1, null);
        appUpdateInfo.setLatestVersionCode(2);
        Assert.assertTrue(AppVersionChecker.DEFAULT.hasAvailableUpdates(appUpdateInfo));
    }

    @Test
    public void test_110_update_false_with_same_version_code() {
        AppUpdateInfo appUpdateInfo = new AppUpdateInfo(1, null);
        appUpdateInfo.setLatestVersionCode(1);
        Assert.assertFalse(AppVersionChecker.DEFAULT.hasAvailableUpdates(appUpdateInfo));
    }

    @Test
    public void test_120_update_false_with_less_version_code() {
        AppUpdateInfo appUpdateInfo = new AppUpdateInfo(2, null);
        appUpdateInfo.setLatestVersionCode(1);
        Assert.assertFalse(AppVersionChecker.DEFAULT.hasAvailableUpdates(appUpdateInfo));
    }
}
