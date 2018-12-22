package com.android.sebiya.update;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;
import com.android.sebiya.library.BuildConfig;
import com.android.sebiya.update.data.DataSource;
import com.android.sebiya.update.frequency.EveryTime;
import com.android.sebiya.update.frequency.Frequency;
import com.android.sebiya.update.ui.Display;
import com.android.sebiya.update.ui.SimpleToastDisplay;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public final class AppUpdateChecker implements LifecycleObserver {

    private static final String LOG_TAG = "AppUpdateChecker";

    private final Frequency mFrequency;

    private final DataSource mDataSource;

    private final Display mDisplay;

    private final LifecycleOwner mLifecycleOwner;

    private final AppUpdateLifecycleCallback mCallback;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private AppUpdateChecker(Builder builder) {
        mFrequency = builder.mFrequency;
        mDataSource = builder.mDataSource;
        mDisplay = builder.mDisplay;
        mLifecycleOwner = builder.mLifecycleOwner;
        if (mLifecycleOwner != null) {
            mLifecycleOwner.getLifecycle().addObserver(this);
        }
        mCallback = builder.mCallback;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void start(final Activity activity) {
        if (mFrequency.shouldCheckUpdate(activity)) {
            if (mCallback != null) {
                mCallback.onStart();
            }

            mCompositeDisposable.add(Single.create(new SingleOnSubscribe<AppUpdateInfo>() {
                @Override
                public void subscribe(final SingleEmitter<AppUpdateInfo> emitter) {
                    emitter.onSuccess(mDataSource.load());
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<AppUpdateInfo, AppUpdateInfo>() {
                        @Override
                        public AppUpdateInfo apply(final AppUpdateInfo appUpdateInfo) {
                            if (BuildConfig.DEBUG) {
                                Log.d(LOG_TAG, "start. data - " + appUpdateInfo);
                            }
                            if (mCallback != null) {
                                mCallback.onDataLoaded(appUpdateInfo);
                            }
                            if (appUpdateInfo.hasAvailableUpdates) {
                                mDisplay.show(activity, appUpdateInfo);
                            }
                            if (mCallback != null) {
                                mCallback.onDisplayShowing(appUpdateInfo);
                            }
                            return appUpdateInfo;
                        }
                    }).subscribe(new Consumer<AppUpdateInfo>() {
                        @Override
                        public void accept(final AppUpdateInfo appUpdateChecker) {
                            if (BuildConfig.DEBUG) {
                                Log.d(LOG_TAG, "start. info - " + appUpdateChecker);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(final Throwable throwable) {
                            Log.e(LOG_TAG, "start", throwable);
                        }
                    }));
        }
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    public void stop() {
        if (mLifecycleOwner != null) {
            mLifecycleOwner.getLifecycle().removeObserver(this);
        }
        mCompositeDisposable.clear();
    }

    public static class Builder {

        private Frequency mFrequency;

        private DataSource mDataSource;

        private Display mDisplay;

        private LifecycleOwner mLifecycleOwner;

        private AppUpdateLifecycleCallback mCallback;

        public Builder withDisplay(Display display) {
            mDisplay = display;
            return this;
        }

        public Builder withDataSource(DataSource source) {
            mDataSource = source;
            return this;
        }

        public Builder withFrequency(Frequency frequency) {
            mFrequency = frequency;
            return this;
        }

        public Builder withLifeCycle(LifecycleOwner lifeCycleOwner) {
            mLifecycleOwner = lifeCycleOwner;
            return this;
        }

        public Builder withCallback(AppUpdateLifecycleCallback callback) {
            mCallback = callback;
            return this;
        }

        public AppUpdateChecker build() {
            // TODO : make default data
            if (mDataSource == null) {
                throw new RuntimeException("data source is required");
            }
            makeDefaultValues();
            return new AppUpdateChecker(this);
        }

        private void makeDefaultValues() {
            if (mFrequency == null) {
                mFrequency = new EveryTime();
            }

            if (mDisplay == null) {
                mDisplay = new SimpleToastDisplay();
            }
        }
    }
}
