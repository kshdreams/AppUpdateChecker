package com.android.sebiya.update;

import android.support.test.runner.AndroidJUnit4;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

    @Test
    public void test_200_update_true_with_larger_version_name() {
        AppUpdateInfo appUpdateInfo = new AppUpdateInfo(0, "1.0.0");
        appUpdateInfo.setLatestVersionName("2.0.0");
        Assert.assertTrue(AppVersionChecker.DEFAULT.hasAvailableUpdates(appUpdateInfo));
    }

    @Test
    public void test_210_update_false_with_same_version_name() {
        AppUpdateInfo appUpdateInfo = new AppUpdateInfo(0, "1.0.0");
        appUpdateInfo.setLatestVersionName("1.0.0");
        Assert.assertFalse(AppVersionChecker.DEFAULT.hasAvailableUpdates(appUpdateInfo));
    }

    @Test
    public void test_220_update_false_with_less_version_name() {
        AppUpdateInfo appUpdateInfo = new AppUpdateInfo(0, "2.0.0");
        appUpdateInfo.setLatestVersionName("1.0.0");
        Assert.assertFalse(AppVersionChecker.DEFAULT.hasAvailableUpdates(appUpdateInfo));
    }
}
