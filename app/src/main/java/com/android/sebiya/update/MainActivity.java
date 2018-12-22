package com.android.sebiya.update;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.android.sebiya.update.data.DefaultDataSources;
import com.android.sebiya.update.data.UrlDataSource.Converter;
import com.android.sebiya.update.frequency.DefaultFrequencies;
import com.android.sebiya.update.ui.DefaultDisplays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppUpdateChecker appUpdateChecker = AppUpdateChecker.builder()
                .withDisplay(DefaultDisplays.simpleToast())
                .withDataSource(DefaultDataSources.urlDataSource("https://github.com/kshdreams", new Converter() {
                    @Override
                    public AppUpdateInfo convert(final String data) {
                        return AppUpdateInfo.builder().build();
                    }
                }))
                .withFrequency(DefaultFrequencies.everyTime())
                .withLifeCycle(this)
                .build();

        appUpdateChecker.start(this);
    }
}
