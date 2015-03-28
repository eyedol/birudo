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

package org.addhen.birudo;

import android.content.Context;

import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.entity.JenkinsBuildInfoJson;
import org.addhen.birudo.core.exception.ErrorWrap;
import org.addhen.birudo.core.usecase.AddJenkinsBuildInfoUsecase;
import org.addhen.birudo.core.usecase.FetchBuildInfoUsecase;
import org.addhen.birudo.data.pref.BooleanPreference;
import org.addhen.birudo.data.qualifier.Sound;
import org.addhen.birudo.data.qualifier.Vibrate;
import org.addhen.birudo.model.JenkinsBuildInfoJsonModel;
import org.addhen.birudo.model.mapper.JenkinsBuildInfoJsonModelMapper;
import org.addhen.birudo.state.BuildState;
import org.addhen.birudo.ui.widget.BuildStateNotification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RetrieveJenkinsBuildInfo {

    @Inject
    FetchBuildInfoUsecase mFetchBuildInfoUsecase;

    @Inject
    AddJenkinsBuildInfoUsecase mAddJenkinsBuildInfoUsecase;

    @Inject
    BuildState mBuildState;

    @Inject
    @Vibrate
    BooleanPreference mVibrate;

    @Inject
    @Sound
    BooleanPreference mSound;

    private JenkinsBuildInfoJsonModelMapper mJenkinsBuildInfoModelMapper;

    private Context mContext;

    private final FetchBuildInfoUsecase.Callback mCallback = new FetchBuildInfoUsecase.Callback() {

        @Override
        public void onBuildInfoFetched(JenkinsBuildInfoJson buildInfo) {
            JenkinsBuildInfoJsonModel model = mJenkinsBuildInfoModelMapper.map(buildInfo);

            if (model != null) {
                new BuildStateNotification()
                        .setNotification(mContext, mVibrate, mSound, model.getDisplayName(),
                                model.getResult(),
                                model.getDuration());
                addJenkinsBuildInfo(model);
            }
        }

        @Override
        public void onError(ErrorWrap error) {

        }
    };

    private final AddJenkinsBuildInfoUsecase.Callback mAddJenkinsBuildInfoCallback = new AddJenkinsBuildInfoUsecase.Callback() {

        @Override
        public void onJenkinsBuildInfoAdded() {
            mBuildState.onBuildStatusReceived();
        }

        @Override
        public void onError(ErrorWrap errorWrap) {

        }
    };

    @Inject
    public RetrieveJenkinsBuildInfo(Context context) {
        mContext = context;
        mJenkinsBuildInfoModelMapper = new JenkinsBuildInfoJsonModelMapper();
    }

    public void execute(String message) {
        if (message != null) {
            Pattern pattern = Pattern.compile("(http(s*)://.*?)$");
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                String url = matcher.group(0);
                mFetchBuildInfoUsecase.executed(url, mCallback);
            }
        }
    }

    private void addJenkinsBuildInfo(JenkinsBuildInfoJsonModel jenkinsBuildInfoJsonModel) {
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
}
