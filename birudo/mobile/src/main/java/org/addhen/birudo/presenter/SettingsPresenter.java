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

package org.addhen.birudo.presenter;

import org.addhen.birudo.data.pref.BooleanPreference;
import org.addhen.birudo.data.pref.StringPreference;
import org.addhen.birudo.data.qualifier.GcmToken;
import org.addhen.birudo.data.qualifier.JenkinsBaseUrl;
import org.addhen.birudo.data.qualifier.JenkinsToken;
import org.addhen.birudo.data.qualifier.JenkinsUsername;
import org.addhen.birudo.data.qualifier.SenderId;
import org.addhen.birudo.data.qualifier.Settings;
import org.addhen.birudo.R;
import org.addhen.birudo.core.entity.JenkinsUser;
import org.addhen.birudo.core.usecase.ParseJenkinsUser;
import org.addhen.birudo.core.usecase.ParseJenkinsUserUsecase;
import org.addhen.birudo.data.qualifier.Sound;
import org.addhen.birudo.data.qualifier.Vibrate;
import org.addhen.birudo.model.JenkinsUserModel;
import org.addhen.birudo.model.mapper.JenkinsUserModelMapper;
import org.addhen.birudo.state.SenderIdState;
import org.addhen.birudo.view.IView;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SettingsPresenter implements Presenter {

    private final ParseJenkinsUserUsecase mParseJenkinsUserUsecase;

    private final JenkinsUserModelMapper mJenkinsUserModelMapper;

    private final ParseJenkinsUserUsecase.Callback mCallback = new ParseJenkinsUser.Callback() {
        @Override
        public void jenkinsUserParsed(JenkinsUser jenkinsUser) {
            JenkinsUserModel jenkinsUserModel = mJenkinsUserModelMapper.map(jenkinsUser);
            saveJenkinsUser(jenkinsUserModel);
        }
    };

    @Inject
    @JenkinsUsername
    StringPreference mUsernamePreference;

    @Inject
    @JenkinsBaseUrl
    StringPreference mUrlPreference;

    @Inject
    @SenderId
    StringPreference mSenderIdPreference;

    @Inject
    @JenkinsToken
    StringPreference mTokenPreference;

    @Inject
    @GcmToken
    StringPreference mGcmPreference;

    @Inject
    @Settings
    SharedPreferences mSharedPreferences;

    @Inject
    SenderIdState mSenderIdState;

    @Inject
    @Vibrate
    BooleanPreference mVibratePreference;

    @Inject
    @Sound
    BooleanPreference mSoundPreference;

    private View mView;

    @Inject
    public SettingsPresenter(ParseJenkinsUserUsecase parseJenkinsUserUsecase,
            JenkinsUserModelMapper jenkinsUserModelMapper) {
        mParseJenkinsUserUsecase = parseJenkinsUserUsecase;
        mJenkinsUserModelMapper = jenkinsUserModelMapper;
    }

    public void setView(View view) {
        mView = view;
    }

    public void parseJenkinsUser(String jenkinsUserJsonResponse) {
        mParseJenkinsUserUsecase.execute(jenkinsUserJsonResponse, mCallback);
    }

    public void saveVibrationSettings(boolean status) {
        mVibratePreference.set(status);
    }

    public void saveSoundSettings(boolean status) {
        mSoundPreference.set(status);
    }

    private void saveJenkinsUser(JenkinsUserModel jenkinsUserModel) {
        mUsernamePreference.set(jenkinsUserModel.getUsername());
        mUrlPreference.set(jenkinsUserModel.getUrl());
        mTokenPreference.set(jenkinsUserModel.getToken());
        mSenderIdPreference.set(jenkinsUserModel.getSenderId());
        refreshGcmToken();
    }

    public void refreshGcmToken() {
        mSenderIdState.onSenderIdChange();
    }

    public void clearSettings() {
        mSharedPreferences.edit().clear().apply();
        mView.showToast(R.string.settings_cleared);
    }

    public String getJenkinsUsername() {
        return mUsernamePreference.get();
    }

    public String getJenkinsBaseUrl() {
        return mUrlPreference.get();
    }

    public String getGcmToken() {
        return mGcmPreference.get();
    }

    public String getGcmSenderId() {
        return mSenderIdPreference.get();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public interface View extends IView {

        void showToast(int resId);
    }
}
