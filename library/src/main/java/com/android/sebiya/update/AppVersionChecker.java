/*
Copyright (C) 2018 Thomas Wirth

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.android.sebiya.update;

import com.g00fy2.versioncompare.Version;

public interface AppVersionChecker {

    boolean hasAvailableUpdates(AppUpdateInfo appUpdateInfo);

    AppVersionChecker DEFAULT = new DefaultImpl();

    /**
     * This class use https://github.com/G00fY2/version-compare library to check version.
     */
    class DefaultImpl implements AppVersionChecker {

        @Override
        public boolean hasAvailableUpdates(final AppUpdateInfo appUpdateInfo) {
            if (appUpdateInfo.getLatestVersionCode() > 0) {
                return appUpdateInfo.getTargetVersionCode() < appUpdateInfo.getLatestVersionCode();
            }

            if (appUpdateInfo.getTargetVersionName() == null || appUpdateInfo.getLatestVersionName() == null) {
                return false;
            }

            Version version = new Version(appUpdateInfo.getLatestVersionName());
            return version.isHigherThan(appUpdateInfo.getTargetVersionName());
        }
    }
}
