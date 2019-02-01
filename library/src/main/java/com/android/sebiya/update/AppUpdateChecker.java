package com.android.sebiya.update;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;
import com.android.sebiya.update.data.DataSource;
import com.android.sebiya.update.frequency.EveryTime;
import com.android.sebiya.update.frequency.Frequency;
import com.android.sebiya.update.install.GooglePlayInstaller;
import com.android.sebiya.update.install.PackageInstaller;
import com.android.sebiya.update.ui.Display;
import com.android.sebiya.update.ui.SimpleDialogDisplay;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public final class AppUpdateChecker implements LifecycleObserver{

    private static final String LOG_TAG = "AppUpdateChecker";

    private final Frequency mFrequency;

    private final DataSource mDataSource;

    private final Display mDisplay;

    private final LifecycleOwner mLifecycleOwner;

    private final AppUpdateLifecycleCallback mCallback;

    private final AppVersionChecker mVersionChecker;

    private final PackageInstaller mPackageInstaller;

    private final boolean mForceShow;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private final boolean mEnableLog;

    private AppUpdateChecker(Builder builder) {
        mFrequency = builder.mFrequency;
        mDataSource = builder.mDataSource;
        mDisplay = builder.mDisplay;
        mLifecycleOwner = builder.mLifecycleOwner;
        if (mLifecycleOwner != null) {
            mLifecycleOwner.getLifecycle().addObserver(this);
        }
        mCallback = builder.mCallback;
        mForceShow = builder.mForceShow;
        mVersionChecker = builder.mVersionChecker;
        mPackageInstaller = builder.mPackageInstaller;
        mEnableLog = builder.mEnableLog;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void start(final Activity activity) {
        if (mFrequency.shouldCheckUpdate(activity)) {
            if (mCallback != null) {
                mCallback.onStart();
            }

            mCompositeDisposable.add(
                    loadAppUpdateInfo()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(showUi(activity))
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(final Boolean dialogShow) {
                                    if (mEnableLog) {
                                        Log.d(LOG_TAG, "start. dialogShow - " + dialogShow);
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
        Log.d(LOG_TAG, "stop called");
        if (mLifecycleOwner != null) {
            mLifecycleOwner.getLifecycle().removeObserver(this);
        }
        mCompositeDisposable.clear();
    }

    private Single<AppUpdateInfo> loadAppUpdateInfo() {
        return Single.create(new SingleOnSubscribe<AppUpdateInfo>() {
            @Override
            public void subscribe(final SingleEmitter<AppUpdateInfo> emitter) {
                emitter.onSuccess(mDataSource.load());
            }
        });
    }


    private Function<AppUpdateInfo, Boolean> showUi(final Activity activity) {
        return new Function<AppUpdateInfo, Boolean>() {
            @Override
            public Boolean apply(final AppUpdateInfo appUpdateInfo) {
                if (mEnableLog) {
                    Log.d(LOG_TAG, "showUi. data - " + appUpdateInfo);
                }
                if (mCallback != null) {
                    mCallback.onDataLoaded(appUpdateInfo);
                }
                if (mVersionChecker.hasAvailableUpdates(appUpdateInfo) || mVersionChecker
                        .hasForceUpdates(appUpdateInfo) || mForceShow) {
                    mDisplay.show(activity, appUpdateInfo, mPackageInstaller);
                    if (mCallback != null) {
                        mCallback.onDisplayShowing(appUpdateInfo);
                    }
                    return true;
                }
                return false;
            }
        };
    }

    public static class Builder {

        private Frequency mFrequency;

        private DataSource mDataSource;

        private Display mDisplay;

        private boolean mForceShow;

        private boolean mEnableLog;

        private LifecycleOwner mLifecycleOwner;

        private AppVersionChecker mVersionChecker;

        private AppUpdateLifecycleCallback mCallback;

        private PackageInstaller mPackageInstaller;

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

        public Builder withVersionChecker(AppVersionChecker versionChecker) {
            mVersionChecker = versionChecker;
            return this;
        }

        public Builder withInstaller(PackageInstaller installer) {
            mPackageInstaller = installer;
            return this;
        }

        public Builder showUiWhenNoUpdates(boolean forceShow) {
            mForceShow = forceShow;
            return this;
        }

        public Builder enableLog(boolean enable) {
            mEnableLog = enable;
            return this;
        }


        public AppUpdateChecker build() {
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
                mDisplay = new SimpleDialogDisplay();
            }

            if (mVersionChecker == null) {
                mVersionChecker = AppVersionChecker.DEFAULT;
            }

            if (mPackageInstaller == null) {
                mPackageInstaller = new GooglePlayInstaller();
            }
        }
    }
}
