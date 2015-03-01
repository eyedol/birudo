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

package org.addhen.birudo.core.repository;

import org.addhen.birudo.core.entity.GcmRegistrationStatus;
import org.addhen.birudo.core.entity.JenkinsBuildInfoJson;
import org.addhen.birudo.core.entity.JenkinsUser;
import org.addhen.birudo.core.exception.ErrorWrap;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface JenkinsRepository {

    void parseUserJsonString(String jsonString, ParseJsonStringCallback parseJsonStringCallback);

    void requestGCMToken(String senderId, RequestGcmTokenCallback requestGcmTokenCallback);

    void fetchBuildInfo(String url, FetchBuildInfoCallback fetchBuildInfoCallback );

    void regsiterGcmTokenOnServer(JenkinsUser jenkinsUser,
            RegisterGcmTokenOnServerCallback registerGcmTokenOnServerCallback);

    interface ParseJsonStringCallback {

        void onJsonStringParsed(JenkinsUser jenkinsUser);
    }

    interface RequestGcmTokenCallback {

        void onGcmTokenRequested(String token);

        void onError(ErrorWrap error);
    }

    interface RegisterGcmTokenOnServerCallback {

        void onGcmTokenRegistered(GcmRegistrationStatus status);

        void onError(ErrorWrap error);
    }

    interface FetchBuildInfoCallback {
        void onBuildInfoFetched(JenkinsBuildInfoJson jenkinsBuildInfo);

        void onError(ErrorWrap errorWrap);
    }
}
