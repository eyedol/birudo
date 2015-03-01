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
