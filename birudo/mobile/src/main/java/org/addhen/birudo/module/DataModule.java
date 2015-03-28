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

package org.addhen.birudo.module;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import com.squareup.otto.Bus;

import org.addhen.birudo.data.database.JenkinsBuildInfoDatabaseHelper;
import org.addhen.birudo.data.entity.mapper.JenkinsBuildInfoEntityDataMapper;
import org.addhen.birudo.data.entity.mapper.JenkinsBuildInfoEntityJsonMapper;
import org.addhen.birudo.data.entity.mapper.JenkinsBuildInfoEntityMapper;
import org.addhen.birudo.data.entity.mapper.JenkinsUserEntityDataMapper;
import org.addhen.birudo.data.pref.BooleanPreference;
import org.addhen.birudo.data.pref.StringPreference;
import org.addhen.birudo.data.qualifier.GcmToken;
import org.addhen.birudo.data.qualifier.JenkinsBaseUrl;
import org.addhen.birudo.data.qualifier.JenkinsToken;
import org.addhen.birudo.data.qualifier.JenkinsUsername;
import org.addhen.birudo.data.qualifier.ScanqrCode;
import org.addhen.birudo.data.qualifier.SenderId;
import org.addhen.birudo.data.qualifier.Settings;
import org.addhen.birudo.data.qualifier.Sound;
import org.addhen.birudo.data.qualifier.Vibrate;
import org.addhen.birudo.data.repository.JenkinsBuildInfoDataRepository;
import org.addhen.birudo.data.repository.JenkinsDataRepository;
import org.addhen.birudo.core.repository.JenkinsBuildInfoRepository;
import org.addhen.birudo.core.repository.JenkinsRepository;
import org.addhen.birudo.core.task.ThreadExecutor;
import org.addhen.birudo.model.mapper.JenkinsBuildInfoModelMapper;
import org.addhen.birudo.model.mapper.JenkinsUserModelMapper;
import org.addhen.birudo.state.AppState;
import org.addhen.birudo.state.BuildState;
import org.addhen.birudo.state.SenderIdState;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module(
        complete = false,
        library = true
)
public final class DataModule {

    public static final String SENDER_ID_KEY = "jenkins-sender-id";

    @Provides
    @Singleton
    @Settings
    SharedPreferences provideSharedPreferences(Context app) {
        return app.getApplicationContext()
                .getSharedPreferences("birudorogu-shared-prefs", MODE_PRIVATE);
    }

    @Provides
    @Singleton
    @ScanqrCode
    StringPreference provideScanqrCodePreference(
            @Settings SharedPreferences prefs) {
        return new StringPreference(prefs, "scan-qr-code", null);
    }

    @Provides
    @Singleton
    @Vibrate
    BooleanPreference provideVibrationPreference(@Settings SharedPreferences prefs) {
        return new BooleanPreference(prefs,"vibrate-pref", true);
    }

    @Provides
    @Singleton
    @Sound
    BooleanPreference provideSoundPreference(@Settings SharedPreferences prefs) {
        return new BooleanPreference(prefs,"sound-pref", true);
    }

    @Provides
    @Singleton
    @JenkinsUsername
    StringPreference provideUsernamePreference(@Settings SharedPreferences prefs) {
        return new StringPreference(prefs, "jenkins-username", null);
    }

    @Provides
    @Singleton
    @JenkinsToken
    StringPreference provideJenkinsTokenPreference(@Settings SharedPreferences prefs) {
        return new StringPreference(prefs, "jenkins-token", null);
    }

    @Provides
    @Singleton
    @JenkinsBaseUrl
    StringPreference provideJenkinsUrlPreference(@Settings SharedPreferences prefs) {
        return new StringPreference(prefs, "jenkins-url", null);
    }

    @Provides
    @Singleton
    @SenderId
    StringPreference provideSenderIdPreference(@Settings SharedPreferences prefs) {
        return new StringPreference(prefs, SENDER_ID_KEY, null);
    }

    @Provides
    @Singleton
    @GcmToken
    StringPreference provideGcmTokenPreference(@Settings SharedPreferences prefs) {
        return new StringPreference(prefs, "gcm-token", null);
    }

    @Provides
    @Singleton
    JenkinsUserEntityDataMapper provideJenkinsEntityDataMapper() {
        return new JenkinsUserEntityDataMapper();
    }

    @Provides
    @Singleton
    JenkinsBuildInfoEntityJsonMapper provideJenkinsBuildInfoEntityJsonMapper() {
        return new JenkinsBuildInfoEntityJsonMapper();
    }

    @Provides
    @Singleton
    JenkinsBuildInfoEntityDataMapper provideJenkinsBuildInfoEntityDataMapper() {
        return new JenkinsBuildInfoEntityDataMapper();
    }

    @Provides
    @Singleton
    JenkinsBuildInfoEntityMapper provideJenkinsBuildInfoEntityMapper() {
        return new JenkinsBuildInfoEntityMapper();
    }

    @Provides
    @Singleton
    JenkinsUserModelMapper provideJenkinsUserModelMapper() {
        return new JenkinsUserModelMapper();
    }

    @Provides
    @Singleton
    JenkinsBuildInfoModelMapper provideJenkinsBuildInfoModelMapper() {
        return new JenkinsBuildInfoModelMapper();
    }

    @Provides
    JenkinsRepository provideJenkinsRepository(
            JenkinsUserEntityDataMapper jenkinsUserEntityDataMapper,
            JenkinsBuildInfoEntityDataMapper jenkinsBuildInfoEntityDataMapper,
            GoogleCloudMessaging gcm) {

        return JenkinsDataRepository
                .getInstance(jenkinsUserEntityDataMapper, jenkinsBuildInfoEntityDataMapper, gcm);
    }

    @Provides
    @Singleton
    JenkinsBuildInfoDatabaseHelper provideJenkinsBuildInfoDatabaseHelper(Context context,
            ThreadExecutor threadExecutor) {
        return JenkinsBuildInfoDatabaseHelper.getInstance(context, threadExecutor);
    }

    @Provides
    JenkinsBuildInfoRepository provideJenkinsBuildInfoRepository(
            JenkinsBuildInfoDatabaseHelper jenkinsBuildInfoDatabaseHelper,
            JenkinsBuildInfoEntityMapper jenkinsBuildInfoEntityMapper) {
        return JenkinsBuildInfoDataRepository
                .getInstance(jenkinsBuildInfoDatabaseHelper, jenkinsBuildInfoEntityMapper);
    }

    @Provides
    @Singleton
    GoogleCloudMessaging provideGoogleCloudMessaging(Context context) {
        return GoogleCloudMessaging.getInstance(context);
    }

    @Provides
    @Singleton
    public Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    public AppState provideAppState(Bus bus) {
        return new AppState(bus);
    }

    @Provides
    public SenderIdState provideSenderIdState(AppState appState) {
        return appState;
    }

    @Provides
    public BuildState provideBuildState(AppState appState) {
        return appState;
    }

}
