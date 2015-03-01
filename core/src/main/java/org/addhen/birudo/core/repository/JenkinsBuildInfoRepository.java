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
