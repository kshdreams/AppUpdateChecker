package com.android.sebiya.update.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GooglePlayInstaller implements PackageInstaller {

    @Override
    public void install(final Context context) {
        Intent googlePlayLink = new Intent(Intent.ACTION_VIEW);
        googlePlayLink.setData(Uri.parse("market://details?id=" + getPackageName(context)));
        googlePlayLink.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(googlePlayLink);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getPackageName(Context context) {
        return context.getPackageName();
    }
}
