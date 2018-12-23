package com.android.sebiya.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AppUpdaterUtils {
    public static void launchGooglePlay(Context context, String packageName) {
        Intent googlePlayLink = new Intent(Intent.ACTION_VIEW);
        googlePlayLink.setData(Uri.parse("market://details?id=" + packageName));
        googlePlayLink.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(googlePlayLink);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
