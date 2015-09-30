/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.tv.settings.device.apps;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v17.preference.LeanbackSettingsFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import com.android.tv.settings.BrowseInfoFactory;
import com.android.tv.settings.MenuActivity;
import com.android.tv.settings.R;

/**
 * Activity allowing the management of apps settings.
 */
public class AppsActivity extends Activity {

    // Used for storage only.
    public static final String EXTRA_VOLUME_UUID = "volumeUuid";
    public static final String EXTRA_VOLUME_NAME = "volumeName";

    private String mVolumeUuid;
    private String mVolumeName; // TODO: surface this to the user somewhere

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getIntent().getExtras();
        if (args != null && args.containsKey(EXTRA_VOLUME_UUID)) {
            mVolumeUuid = args.getString(EXTRA_VOLUME_UUID);
            mVolumeName = args.getString(EXTRA_VOLUME_NAME);
        }
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content,
                            AppsSettingsFragment.newInstance(mVolumeUuid, mVolumeName))
                    .commit();
        }
    }

    public static class AppsSettingsFragment extends LeanbackSettingsFragment {

        public static AppsSettingsFragment newInstance(String volumeUuid, String volumeName) {
            final Bundle b = new Bundle(2);
            b.putString(EXTRA_VOLUME_UUID, volumeUuid);
            b.putString(EXTRA_VOLUME_NAME, volumeName);
            final AppsSettingsFragment f = new AppsSettingsFragment();
            f.setArguments(b);
            return f;
        }

        @Override
        public void onPreferenceStartInitialScreen() {
            final String volumeUuid = getArguments().getString(EXTRA_VOLUME_UUID);
            final String volumeName = getArguments().getString(EXTRA_VOLUME_NAME);
            startPreferenceFragment(AppsFragment.newInstance(volumeUuid, volumeName));
        }

        @Override
        public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
            return false;
        }

        @Override
        public boolean onPreferenceStartScreen(PreferenceFragment caller, PreferenceScreen pref) {
            return false;
        }
    }
}
