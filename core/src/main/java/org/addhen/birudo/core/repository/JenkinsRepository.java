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
