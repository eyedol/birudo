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

package org.addhen.birudo.data.repository;

import org.addhen.birudo.data.database.JenkinsBuildInfoDatabase;
import org.addhen.birudo.data.database.JenkinsBuildInfoDatabaseHelper;
import org.addhen.birudo.data.entity.JenkinsBuildInfoEntity;
import org.addhen.birudo.data.entity.mapper.JenkinsBuildInfoEntityMapper;
import org.addhen.birudo.data.exception.RepositoryError;
import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.repository.JenkinsBuildInfoRepository;

import java.util.List;

public class JenkinsBuildInfoDataRepository implements JenkinsBuildInfoRepository {

    private static JenkinsBuildInfoDataRepository sInstance;

    private final JenkinsBuildInfoDatabaseHelper mJenkinsBuildInfoDatabaseHelper;

    private final JenkinsBuildInfoEntityMapper mJenkinsBuildInfoEntityMapper;

    protected JenkinsBuildInfoDataRepository(
            JenkinsBuildInfoDatabaseHelper jenkinsBuildInfoDatabaseHelper,
            JenkinsBuildInfoEntityMapper jenkinsBuildInfoEntityMapper) {

        if (jenkinsBuildInfoEntityMapper == null || jenkinsBuildInfoDatabaseHelper == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        mJenkinsBuildInfoDatabaseHelper = jenkinsBuildInfoDatabaseHelper;
        mJenkinsBuildInfoEntityMapper = jenkinsBuildInfoEntityMapper;
    }

    public static synchronized JenkinsBuildInfoDataRepository getInstance(
            JenkinsBuildInfoDatabaseHelper jenkinsBuildInfoDatabaseHelper,
            JenkinsBuildInfoEntityMapper jenkinsBuildInfoEntityMapper) {
        if (sInstance == null) {
            sInstance = new JenkinsBuildInfoDataRepository(jenkinsBuildInfoDatabaseHelper,
                    jenkinsBuildInfoEntityMapper);
        }

        return sInstance;
    }

    @Override
    public void put(final JenkinsBuildInfo jenkinsBuildInfoEntity,
            final JenkinsBuildInfoPutCallback callback) {
        mJenkinsBuildInfoDatabaseHelper
                .put(mJenkinsBuildInfoEntityMapper.unmap(jenkinsBuildInfoEntity),
                        new JenkinsBuildInfoDatabase.JenkinsBuildInfoEntityPutCallback() {
                            @Override
                            public void onJenkinsBuildInfoEntityPut() {
                                callback.onJenkinsBuildInfoPut();
                            }

                            @Override
                            public void onError(Exception exception) {
                                callback.onError(new RepositoryError(exception));
                            }
                        });
    }

    @Override
    public void delete(final JenkinsBuildInfo jenkinsBuildInfoEntity,
            final JenkinsBuildInfoDeletedCallback callback) {
        mJenkinsBuildInfoDatabaseHelper
                .delete(mJenkinsBuildInfoEntityMapper.unmap(jenkinsBuildInfoEntity),
                        new JenkinsBuildInfoDatabase.JenkinsBuildInfoEntityDeletedCallback() {
                            @Override
                            public void onJenkinsBuildInfoDeleted() {
                                callback.onJenkinsBuildInfoDeleted();
                            }

                            @Override
                            public void onError(Exception exception) {
                                callback.onError(new RepositoryError(exception));
                            }
                        });
    }

    @Override
    public void getList(final JenkinsBuildInfoListCallback callback) {
        mJenkinsBuildInfoDatabaseHelper
                .getList(new JenkinsBuildInfoDatabase.JenkinsBuildInfoEntityListCallback() {
                    @Override
                    public void onJenkinsBuildInfoEntityListLoaded(
                            List<JenkinsBuildInfoEntity> jenkinsBuildInfoEntityList) {
                        callback.onJenkinsBuildInfoListLoaded(
                                mJenkinsBuildInfoEntityMapper.map(jenkinsBuildInfoEntityList));
                    }

                    @Override
                    public void onError(Exception exception) {
                        callback.onError(new RepositoryError(exception));
                    }
                });
    }
}
