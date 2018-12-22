package com.android.sebiya.update.data;

import com.android.sebiya.update.data.UrlDataSource.Converter;

public class DefaultDataSources {
    public static DataSource urlDataSource(String url, Converter converter) {
        return new UrlDataSource(url, converter);
    }
}
