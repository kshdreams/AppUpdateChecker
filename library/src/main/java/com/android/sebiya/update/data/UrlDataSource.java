package com.android.sebiya.update.data;

import android.support.annotation.NonNull;
import android.util.Log;
import com.android.sebiya.library.BuildConfig;
import com.android.sebiya.update.AppUpdateInfo;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class UrlDataSource implements DataSource {
    private static final String LOG_TAG = "UrlDataSource";

    private final String mUrl;

    private final Converter mConverter;

    private static InternalUrlApiService sDownloader;

    public UrlDataSource(@NonNull String url, @NonNull Converter converter) {
        mUrl = url;
        mConverter = converter;
    }

    @Override
    public AppUpdateInfo load() {
        ensureDownloader();

        AppUpdateInfo info = null;
        try {
            Response<String> response = sDownloader.download(mUrl).execute();
            String body = response.body();
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "load. body - " + body);
            }
            info = mConverter.convert(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    private void ensureDownloader() {
        if (sDownloader == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.naver.com")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            sDownloader = retrofit.create(InternalUrlApiService.class);
        }
    }

    public interface Converter {

        AppUpdateInfo convert(String data);
    }

    public interface InternalUrlApiService {

        @GET
        Call<String> download(@Url String url);
    }


}
