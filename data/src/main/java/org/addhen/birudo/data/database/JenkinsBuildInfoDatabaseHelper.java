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
import org.addhen.birudo.data.exception.JenkinsBuildInfoNotFoundException;
import org.addhen.birudo.core.task.ThreadExecutor;

import android.content.Context;

import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class JenkinsBuildInfoDatabaseHelper extends BaseDatabseHelper
        implements JenkinsBuildInfoDatabase {

    private static JenkinsBuildInfoDatabaseHelper sInstance;

    private static String TAG = JenkinsBuildInfoDatabaseHelper.class.getSimpleName();

    private final ThreadExecutor mThreadExecutor;

    private JenkinsBuildInfoDatabaseHelper(Context context, ThreadExecutor threadExecutor) {
        super(context);

        if (threadExecutor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }

        mThreadExecutor = threadExecutor;
    }

    public static synchronized JenkinsBuildInfoDatabaseHelper getInstance(Context context,
            ThreadExecutor threadExecutor) {

        if (sInstance == null) {
            sInstance = new JenkinsBuildInfoDatabaseHelper(context, threadExecutor);
        }
        return sInstance;
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void asyncRun(Runnable runnable) {
        mThreadExecutor.execute(runnable);
    }

    @Override
    public synchronized void put(final JenkinsBuildInfoEntity jenkinsBuildInfoEntity,
            final JenkinsBuildInfoEntityPutCallback callback) {
        asyncRun(new Runnable() {
            @Override
            public void run() {
                if (!isClosed()) {
                    try {
                        cupboard().withDatabase(getWritableDatabase()).put(jenkinsBuildInfoEntity);
                        callback.onJenkinsBuildInfoEntityPut();
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }
        });
    }

    @Override
    public synchronized void delete(final JenkinsBuildInfoEntity jenkinsBuildInfoEntity,
            final JenkinsBuildInfoEntityDeletedCallback callback) {
        asyncRun(new Runnable() {
            @Override
            public void run() {
                if (!isClosed()) {
                    try {
                        cupboard().withDatabase(getWritableDatabase())
                                .delete(jenkinsBuildInfoEntity);
                        callback.onJenkinsBuildInfoDeleted();
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }
        });
    }

    @Override
    public synchronized void getList(final JenkinsBuildInfoEntityListCallback callback) {
        asyncRun(new Runnable() {
            @Override
            public void run() {
                if (!isClosed()) {
                    try {
                        List<JenkinsBuildInfoEntity> jenkinsBuildInfoEntity
                                = getJenkinsBuildInfoEntity();
                        if (jenkinsBuildInfoEntity != null) {
                            callback.onJenkinsBuildInfoEntityListLoaded(jenkinsBuildInfoEntity);
                        } else {
                            callback.onError(new JenkinsBuildInfoNotFoundException());
                        }
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }
        });
    }

    private List<JenkinsBuildInfoEntity> getJenkinsBuildInfoEntity() {
        return cupboard().withDatabase(getReadableDatabase()).query(JenkinsBuildInfoEntity.class)
                .list();
    }
}
