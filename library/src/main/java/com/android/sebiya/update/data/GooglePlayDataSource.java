package com.android.sebiya.update.data;

import android.support.annotation.NonNull;
import android.util.Log;
import com.android.sebiya.update.AppUpdateInfo;
import java.io.IOException;
import org.jsoup.Jsoup;

/**
 * this class will retrieve version code from play store web.
 * possibly some package does not have specific version name
 * ex> Facebook version is "Varies with device"
 * in that case you can't use this data source.
 *
 * this class use Jsoup which have MIT license.
 * // TODO : link github page and write MIT license at the top of code
 */
public class GooglePlayDataSource implements DataSource {
    private static final String LOG_TAG = "GooglePlayDataSource";

    private static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=";

    private final String packageName;
    private final String targetVersionName;
    public GooglePlayDataSource(@NonNull String packageName, String targetVersionName) {
        this.packageName = packageName;
        this.targetVersionName = targetVersionName;
    }

    @Override
    public AppUpdateInfo load() {
        AppUpdateInfo info = null;
        try {
            String versionName = Jsoup.connect(GOOGLE_PLAY_URL + packageName)
                    .timeout(10000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get()
                    .select(".hAyfc .htlgb")
                    .get(7)
                    .ownText();
            Log.d(LOG_TAG, "load. package - " + packageName + ", version name from web - " + versionName);
            if (versionName != null) {
                info = new AppUpdateInfo(-1, targetVersionName);
                info.setLatestVersionName(versionName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }
}
