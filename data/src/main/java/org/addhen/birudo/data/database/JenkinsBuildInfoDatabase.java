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
