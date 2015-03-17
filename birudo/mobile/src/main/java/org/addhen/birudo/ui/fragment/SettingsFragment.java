/*
 * Copyright 2015 Henry Addo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.addhen.birudo.ui.fragment;

import org.addhen.birudo.BuildConfig;
import org.addhen.birudo.R;
import org.addhen.birudo.presenter.SettingsPresenter;
import org.addhen.birudo.ui.activity.BaseActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import javax.inject.Inject;

import timber.log.Timber;

import static org.addhen.birudo.module.DataModule.SENDER_ID_KEY;

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceClickListener, SettingsPresenter.View {

    public static final String SETTINGS_SCAN_QR_CODE = "scan_qr_code";

    public static final String SETTINGS_CLEAR_SETTINGS = "clear_settings";

    public static final String SETTINGS_KEY_GCM_TOKEN = "gcm_token";

    public static final String SETTINGS_KEY_GCM_SENDER_ID = "gcm_sender_id";

    public static final String SETTINGS_KEY_JENKINS_URL = "jenkins_base_url";

    public static final String SETTINGS_KEY_JENKINS_USERNAME = "jenkins_username";

    public static final String SETTINGS_KEY_VIBRATE = "notification_vibrate_key";

    public static final String SETTINGS_KEY_SOUND = "notification_sound_key";

    public static final String SETTINGS_ABOUT_KEY = "about_text_key";

    public static final String TAG = SettingsFragment.class.getSimpleName();

    private static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Inject
    SettingsPresenter mSettingsPresenter;

    private EditTextPreference mJenkinsUsername;

    private EditTextPreference mJenkinsBaseUrl;

    private EditTextPreference mGCMSenderId;

    private Preference mAbout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Uri data = getActivity().getIntent().getData();

        if (data != null && data.toString().contains("jenkins_server")) {

            addPreferencesFromResource(R.xml.jenkins_settings);
        } else {
            addPreferencesFromResource(R.xml.preferences);
        }

        setOnPreferenceClickListener(SETTINGS_SCAN_QR_CODE);
        setOnPreferenceClickListener(SETTINGS_CLEAR_SETTINGS);
        setOnPreferenceClickListener(SETTINGS_KEY_GCM_TOKEN);
        setOnPreferenceClickListener(SETTINGS_KEY_VIBRATE);
        setSettingsFields();
    }

    private void setOnPreferenceClickListener(String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            preference.setOnPreferenceClickListener(this);
        }
    }

    private void setSettingsFields() {
        mGCMSenderId = (EditTextPreference) getPreferenceScreen()
                .findPreference(SETTINGS_KEY_GCM_SENDER_ID);
        mJenkinsUsername = (EditTextPreference) getPreferenceScreen()
                .findPreference(SETTINGS_KEY_JENKINS_USERNAME);
        mJenkinsBaseUrl = (EditTextPreference) getPreferenceScreen()
                .findPreference(SETTINGS_KEY_JENKINS_URL);

        mAbout = getPreferenceScreen().findPreference(SETTINGS_ABOUT_KEY);

        final Preference gcmTokenPref = findPreference(SETTINGS_KEY_GCM_TOKEN);

        if (mGCMSenderId != null) {
            mGCMSenderId.setText(mSettingsPresenter.getGcmSenderId());
            mGCMSenderId.setSummary(
                    getString(R.string.gcm_sender_id_summary, mSettingsPresenter.getGcmSenderId()));
        }

        if (mJenkinsUsername != null) {
            mJenkinsUsername.setText(mSettingsPresenter.getJenkinsUsername());
        }

        if (mJenkinsBaseUrl != null) {
            mJenkinsBaseUrl.setText(mSettingsPresenter.getJenkinsBaseUrl());
            mJenkinsBaseUrl.setSummary(getString(R.string.settings_jenkins_base_url_summary,
                    mSettingsPresenter.getJenkinsBaseUrl()));
        }

        if (gcmTokenPref != null) {
            gcmTokenPref.setSummary(
                    getString(R.string.gcm_token_summary, mSettingsPresenter.getGcmToken()));
        }

        if(mAbout !=null) {
            mAbout.setSummary(BuildConfig.VERSION_NAME);
        }

        setPreference(getPreferenceScreen().getSharedPreferences());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        injectDependencies();
        mSettingsPresenter.setView(this);
    }

    public void onResume() {
        mSettingsPresenter.resume();
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public void onPause() {
        mSettingsPresenter.pause();
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Timber.d(TAG, String.format("Preferences %s changed", key));

        if (key.equals(SENDER_ID_KEY)) {
            mSettingsPresenter.refreshGcmToken();
        }

        if(key.equals(SETTINGS_KEY_VIBRATE)) {
            final boolean status = sharedPreferences.getBoolean(SETTINGS_KEY_VIBRATE, false);
            mSettingsPresenter.saveVibrationSettings(status);
        }

        if(key.equals(SETTINGS_KEY_SOUND)) {
            final boolean status = sharedPreferences.getBoolean(SETTINGS_KEY_SOUND, false);
            mSettingsPresenter.saveSoundSettings(status);
        }
    }

    private void setPreference(SharedPreferences sharedPreferences) {

            final boolean vibrateStatus = sharedPreferences.getBoolean(SETTINGS_KEY_VIBRATE, false);
            mSettingsPresenter.saveVibrationSettings(vibrateStatus);

            final boolean soundStatus = sharedPreferences.getBoolean(SETTINGS_KEY_SOUND, false);
            mSettingsPresenter.saveSoundSettings(soundStatus);

    }

    private void scanQrCode() {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException notFoundException) {
            showDialog().show();

        }
    }

    private AlertDialog showDialog() {

        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(getActivity());

        downloadDialog.setTitle(getResources().getString(R.string.no_qr_code_found));

        downloadDialog.setMessage(getResources().getString(R.string.yes));

        downloadDialog.setPositiveButton(getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("market://search?q=pname:"
                                + "com.google.zxing.client.android");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        getActivity().startActivity(intent);

                    }

                });

        downloadDialog.setNegativeButton(getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {

            if (resultCode == Activity.RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                mSettingsPresenter.parseJenkinsUser(contents);
            }

        }

    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(SETTINGS_SCAN_QR_CODE)) {
            scanQrCode();
            return true;
        }

        if (key.equals(SETTINGS_CLEAR_SETTINGS)) {
            mSettingsPresenter.clearSettings();
            return true;
        }

        if (key.equals(SETTINGS_KEY_GCM_TOKEN)) {
            return true;
        }

        if (key.equals(SETTINGS_KEY_VIBRATE)) {
            return true;
        }

        return false;
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getAppContext(), getText(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getAppContext() {
        return getActivity();
    }

    private void injectDependencies() {
        ((BaseActivity) getActivity()).inject(this);
    }
}
