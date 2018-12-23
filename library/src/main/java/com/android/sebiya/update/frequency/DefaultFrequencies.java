package com.android.sebiya.update.frequency;

import android.content.Context;

public class DefaultFrequencies {

    public static final Frequency EVERY_TIME = new EveryTime();

    // TODO : impl
    public static final Frequency EVERY_DAY = new Frequency() {
        @Override
        public boolean shouldCheckUpdate(final Context context) {
            return false;
        }
    };
}
