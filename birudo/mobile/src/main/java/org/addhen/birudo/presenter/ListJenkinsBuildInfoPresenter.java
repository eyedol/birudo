
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

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.addhen.birudo.data.pref.StringPreference;
import org.addhen.birudo.data.qualifier.GcmToken;
import org.addhen.birudo.data.qualifier.JenkinsBaseUrl;
import org.addhen.birudo.data.qualifier.JenkinsToken;
import org.addhen.birudo.data.qualifier.JenkinsUsername;
import org.addhen.birudo.data.qualifier.SenderId;
import org.addhen.birudo.ErrorMessageFactory;
import org.addhen.birudo.R;
import org.addhen.birudo.core.entity.GcmRegistrationStatus;
import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.entity.JenkinsUser;
import org.addhen.birudo.core.exception.ErrorWrap;
import org.addhen.birudo.core.usecase.AddJenkinsBuildInfo;
import org.addhen.birudo.core.usecase.AddJenkinsBuildInfoUsecase;
import org.addhen.birudo.core.usecase.DeleteJenkinsBuildInfo;
import org.addhen.birudo.core.usecase.DeleteJenkinsBuildInfoUsecase;
import org.addhen.birudo.core.usecase.ListJenkinsBuildInfo;
import org.addhen.birudo.core.usecase.ListJenkinsBuildInfoUsecase;
import org.addhen.birudo.core.usecase.RegisterGCMTokenOnServerUsecase;
import org.addhen.birudo.core.usecase.RequestGCMToken;
import org.addhen.birudo.core.usecase.RequestGCMTokenUsecase;
import org.addhen.birudo.model.JenkinsBuildInfoJsonModel;
import org.addhen.birudo.model.JenkinsBuildInfoModel;
import org.addhen.birudo.model.JenkinsUserModel;
import org.addhen.birudo.model.mapper.JenkinsBuildInfoModelMapper;
import org.addhen.birudo.model.mapper.JenkinsUserModelMapper;
import org.addhen.birudo.state.BuildState;
import org.addhen.birudo.state.SenderIdState;
import org.addhen.birudo.view.IView;

import android.text.TextUtils;

import java.util.List;

import javax.inject.Inject;

public class ListJenkinsBuildInfoPresenter implements Presenter {

    private final RequestGCMTokenUsecase mRequestGCMTokenUsecase;

    private final RegisterGCMTokenOnServerUsecase mRegisterGCMTokenOnServerUsecase;

    private final ListJenkinsBuildInfoUsecase mListJenkinsBuildInfoUsecase;

    private final AddJenkinsBuildInfoUsecase mAddJenkinsBuildInfoUsecase;

    private final DeleteJenkinsBuildInfoUsecase mDeleteJenkinsBuildInfoUsecase;

    private final RequestGCMTokenUsecase.Callback mCallback = new RequestGCMToken.Callback() {
        @Override
        public void onGCMTokenRequested(String token) {
            mGcmTokenPreference.set(token);
            registerGcmToken();
        }

        @Override
        public void onError(ErrorWrap error) {
            mView.showMessage(error.getErrorMessage());
        }
    };

    private final RegisterGCMTokenOnServerUsecase.Callback mRegisterGcmTokenOnServerCallback
            = new RegisterGCMTokenOnServerUsecase.Callback() {

        @Override
        public void onGcmTokenRegistered(GcmRegistrationStatus status) {
            if (!status.isStatus()) {
                mView.showMessage(mView.getAppContext()
                        .getString(R.string.registering_failed, status.getStatusCode()));
            } else {
                mView.showMessage(mView.getAppContext().getString(R.string.registering_success));
            }
        }

        @Override
        public void onError(ErrorWrap error) {
            mView.showMessage(error.getErrorMessage());
        }
    };

    private final AddJenkinsBuildInfoUsecase.Callback mAddJenkinsBuildInfoCallback
            = new AddJenkinsBuildInfo.Callback() {
        @Override
        public void onJenkinsBuildInfoAdded() {
            getJenkinBuildInfoList();
        }

        @Override
        public void onError(ErrorWrap errorWrap) {
            showErrorMessage(errorWrap);
        }
    };

    private final ListJenkinsBuildInfoUsecase.Callback mListJenkinsBuildInfoCallback
            = new ListJenkinsBuildInfo.Callback() {
        @Override
        public void onJenkinsBuildInfoLoaded(List<JenkinsBuildInfo> jenkinsBuildInfoList) {
            showJenkinsBuildInfoList(jenkinsBuildInfoList);
        }

        @Override
        public void onError(ErrorWrap errorWrap) {
            showErrorMessage(errorWrap);
        }
    };

    private final DeleteJenkinsBuildInfoUsecase.Callback mDeleteJenkinsBuildCallback
            = new DeleteJenkinsBuildInfo.Callback() {
        @Override
        public void onJenkinsBuildInfoDeleted() {

        }

        @Override
        public void onError(ErrorWrap errorWrap) {
            showErrorMessage(errorWrap);
        }
    };

    @Inject
    GoogleCloudMessaging mGcm;

    @Inject
    @SenderId
    StringPreference mSenderIdPreference;

    @Inject
    @GcmToken
    StringPreference mGcmTokenPreference;

    @Inject
    @JenkinsUsername
    StringPreference mJenkinsUsername;

    @Inject
    @JenkinsToken
    StringPreference mJenkinsToken;

    @Inject
    @JenkinsBaseUrl
    StringPreference mJenkinsBaseUrl;

    @Inject
    SenderIdState mSenderIdState;

    @Inject
    BuildState mBuildState;

    @Inject
    JenkinsBuildInfoModelMapper mJenkinsBuildInfoModelMapper;

    private View mView;

    @Inject
    public ListJenkinsBuildInfoPresenter(RequestGCMTokenUsecase requestGCMTokenUsecase,
            RegisterGCMTokenOnServerUsecase registerGCMTokenOnServerUsecase,
            ListJenkinsBuildInfoUsecase listJenkinsBuildInfoUsecase,
            DeleteJenkinsBuildInfoUsecase deleteJenkinsBuildInfoUsecase,
            AddJenkinsBuildInfoUsecase addJenkinsBuildInfoUsecase) {
        mRequestGCMTokenUsecase = requestGCMTokenUsecase;
        mRegisterGCMTokenOnServerUsecase = registerGCMTokenOnServerUsecase;
        mListJenkinsBuildInfoUsecase = listJenkinsBuildInfoUsecase;
        mDeleteJenkinsBuildInfoUsecase = deleteJenkinsBuildInfoUsecase;
        mAddJenkinsBuildInfoUsecase = addJenkinsBuildInfoUsecase;
    }

    public void setView(View view) {
        mView = view;
    }

    public void refreshGcmToken() {
        final String senderId = mSenderIdPreference.get();
        if (senderId != null && !senderId.isEmpty()) {
            mRequestGCMTokenUsecase.execute(senderId, mCallback);
        }
    }

    private void registerGcmToken() {
        final String username = mJenkinsUsername.get();
        final String token = mJenkinsToken.get();
        final String senderId = mGcmTokenPreference.get();
        final String url = mJenkinsBaseUrl.get();
        JenkinsUserModel jenkinsUserModel = new JenkinsUserModel(username, token, url, senderId);

        JenkinsUserModelMapper jenkinsUserModelMapper = new JenkinsUserModelMapper();
        JenkinsUser jenkinsUser = jenkinsUserModelMapper.unmap(jenkinsUserModel);

        if (jenkinsUser != null) {
            mRegisterGCMTokenOnServerUsecase
                    .execute(jenkinsUser, mRegisterGcmTokenOnServerCallback);
        }
    }

    public boolean isAppConfigured() {
        return mGcmTokenPreference != null && !TextUtils.isEmpty(mGcmTokenPreference.get());
    }

    public void addJenkinsBuildInfo(JenkinsBuildInfoJsonModel jenkinsBuildInfoJsonModel) {
        JenkinsBuildInfo jenkinsBuildInfo = new JenkinsBuildInfo();
        jenkinsBuildInfo.setId(jenkinsBuildInfoJsonModel.getId());
        jenkinsBuildInfo.setDuration(jenkinsBuildInfoJsonModel.getDuration());
        jenkinsBuildInfo.setTimestamp(jenkinsBuildInfoJsonModel.getTimestamp());
        jenkinsBuildInfo.setResult(
                JenkinsBuildInfo.Result.valueOf(jenkinsBuildInfoJsonModel.getResult().name()));
        jenkinsBuildInfo.setUrl(jenkinsBuildInfoJsonModel.getUrl());
        final String userName =
                jenkinsBuildInfoJsonModel.getActions().get(1).getCauses().get(0).getUserId() != null
                        ? jenkinsBuildInfoJsonModel.getActions().get(1).getCauses().get(0)
                        .getUserId()
                        : jenkinsBuildInfoJsonModel.getActions().get(1).getCauses().get(0)
                                .getAddr();
        jenkinsBuildInfo.setUserName(userName);
        jenkinsBuildInfo.setDisplayName(jenkinsBuildInfoJsonModel.getDisplayName());
        mAddJenkinsBuildInfoUsecase.execute(jenkinsBuildInfo, mAddJenkinsBuildInfoCallback);
    }

    private void showErrorMessage(ErrorWrap errorWrap) {
        String errorMessage = ErrorMessageFactory.create(mView.getAppContext(),
                errorWrap.getException());
        mView.showMessage(errorMessage);
    }

    private void showJenkinsBuildInfoList(List<JenkinsBuildInfo> jenkinsBuildInfos) {
        final List<JenkinsBuildInfoModel> jenkinsBuildInfoModels = mJenkinsBuildInfoModelMapper
                .map(jenkinsBuildInfos);
        mView.setJenkinsBuildInfoListItems(jenkinsBuildInfoModels);
    }

    @Override
    public void resume() {
        mSenderIdState.registerEvent(this);
        mBuildState.registerEvent(this);
        getJenkinBuildInfoList();
    }

    @Override
    public void pause() {
        mSenderIdState.unregisterEvent(this);
        mBuildState.unregisterEvent(this);
    }

    private void getJenkinBuildInfoList() {
        mListJenkinsBuildInfoUsecase.execute(mListJenkinsBuildInfoCallback);
    }

    public void deleteBuildInfo( JenkinsBuildInfoModel buildInfoModel) {
        final JenkinsBuildInfo jenkinsBuildInfo = mJenkinsBuildInfoModelMapper.unmap(buildInfoModel);
        mDeleteJenkinsBuildInfoUsecase.execute(jenkinsBuildInfo, mDeleteJenkinsBuildCallback);
    }

    public interface View extends IView {

        void setJenkinsBuildInfoListItems(List<JenkinsBuildInfoModel> modelList);

        void showMessage(String message);
    }
}
