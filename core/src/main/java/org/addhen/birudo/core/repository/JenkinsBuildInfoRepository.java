package org.addhen.birudo.core.repository;

import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.exception.ErrorWrap;

import java.util.List;

public interface JenkinsBuildInfoRepository {

    void put(JenkinsBuildInfo jenkinsBuildInfoEntity,
            JenkinsBuildInfoPutCallback callback);

    void delete(JenkinsBuildInfo jenkinsBuildInfoEntity,
            JenkinsBuildInfoDeletedCallback callback);

    void getList(JenkinsBuildInfoListCallback callback);

    interface JenkinsBuildInfoPutCallback {

        void onJenkinsBuildInfoPut();

        void onError(ErrorWrap error);
    }

    interface JenkinsBuildInfoDeletedCallback {

        void onJenkinsBuildInfoDeleted();

        void onError(ErrorWrap error);
    }

    interface JenkinsBuildInfoListCallback {

        void onJenkinsBuildInfoListLoaded(
                List<JenkinsBuildInfo> jenkinsBuildInfoList);

        void onError(ErrorWrap error);
    }
}
