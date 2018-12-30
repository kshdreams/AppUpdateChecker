/*
The MIT License

Copyright (c) 2009-2018 Jonathan Hedley <jonathan@hedley.net>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
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
 * https://github.com/jhy/jsoup
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
