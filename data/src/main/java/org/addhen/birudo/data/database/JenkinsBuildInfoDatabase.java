package org.addhen.birudo.data.database;

import org.addhen.birudo.data.entity.JenkinsBuildInfoEntity;

import java.util.List;

public interface JenkinsBuildInfoDatabase {

    void put(JenkinsBuildInfoEntity jenkinsBuildInfoEntity,
            JenkinsBuildInfoEntityPutCallback callback);

    void delete(JenkinsBuildInfoEntity jenkinsBuildInfoEntity,
            JenkinsBuildInfoEntityDeletedCallback callback);

    void getList(JenkinsBuildInfoEntityListCallback callback);

    interface JenkinsBuildInfoEntityPutCallback {

        void onJenkinsBuildInfoEntityPut();

        void onError(Exception exception);
    }

    interface JenkinsBuildInfoEntityDeletedCallback {

        void onJenkinsBuildInfoDeleted();

        void onError(Exception exception);
    }

    interface JenkinsBuildInfoEntityListCallback {

        void onJenkinsBuildInfoEntityListLoaded(
                List<JenkinsBuildInfoEntity> jenkinsBuildInfoEntityList);

        void onError(Exception exception);
    }
}
