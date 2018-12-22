package com.android.sebiya.update.frequency;

import android.content.Context;

public class DefaultFrequencies {
    public static Frequency everyTime() {
        return new EveryTime();
    }

    // TODO : impl
    public static Frequency everyDay() {
        return new Frequency() {
            @Override
            public boolean shouldCheckUpdate(final Context context) {
                return false;
            }
        };
    }
}
