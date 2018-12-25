package com.android.sebiya.update.data;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;
import com.android.sebiya.update.AppUpdateInfo;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;

@RunWith(AndroidJUnit4.class)
public class GooglePlayDataSourceTest {
    private static final String LOG_TAG = "GooglePlayDataSourceTest";

    private static final List<String> TEST_PACKAGES = new ArrayList<>();
    static {
        TEST_PACKAGES.add("com.kakao.talk"); // KakaoTalk
        TEST_PACKAGES.add("com.ss.android.ugc.trill"); // TikTok
        TEST_PACKAGES.add("com.ncsoft.lineagem19"); // Lineage M
    }

    @Test
    public void testVersionNameNotNull() {
        List<String> nullPackage = new ArrayList<>();
        for (String packageName : TEST_PACKAGES) {
            GooglePlayDataSource googlePlayDataSource = new GooglePlayDataSource(packageName, null);
            AppUpdateInfo info = googlePlayDataSource.load();
            Log.i(LOG_TAG, "testVersionNameNotNull. version - " + info.getLatestVersionName());
            if (TextUtils.isEmpty(info.getLatestVersionName())) {
                nullPackage.add(packageName);
            }
        }
        Assert.assertEquals(0, nullPackage.size());
    }
}
