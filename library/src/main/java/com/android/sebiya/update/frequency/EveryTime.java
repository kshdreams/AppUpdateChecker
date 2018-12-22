package com.android.sebiya.update.frequency;

import android.content.Context;

public class EveryTime implements Frequency {

    @Override
    public boolean shouldCheckUpdate(final Context context) {
        return true;
    }
}
