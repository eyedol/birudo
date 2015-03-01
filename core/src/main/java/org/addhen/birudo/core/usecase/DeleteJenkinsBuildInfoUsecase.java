package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.exception.ErrorWrap;
import org.addhen.birudo.core.repository.JenkinsBuildInfoRepository;
import org.addhen.birudo.core.task.PostExecutionThread;
import org.addhen.birudo.core.task.ThreadExecutor;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeleteJenkinsBuildInfoUsecase implements DeleteJenkinsBuildInfo {

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private final JenkinsBuildInfoRepository mJenkinsBuildInfoRepository;

    private Callback mCallback;

    private JenkinsBuildInfo mJenkinsBuildInfo;

    private JenkinsBuildInfoRepository.JenkinsBuildInfoDeletedCallback mJenkinsBuildInfoDeletedCallback = new JenkinsBuildInfoRepository.JenkinsBuildInfoDeletedCallback() {
        @Override
        public void onJenkinsBuildInfoDeleted() {
            onSuccess();
        }

        @Override
        public void onError(ErrorWrap error) {
            onErrorOccurred(error);
        }
    };

    public DeleteJenkinsBuildInfoUsecase(JenkinsBuildInfoRepository jenkinsBuildInfoRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {

        if (jenkinsBuildInfoRepository == null || threadExecutor == null
                || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }

        mJenkinsBuildInfoRepository = jenkinsBuildInfoRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;

    }
    @Override
    public void execute(JenkinsBuildInfo jenkinsBuildInfo,
            Callback callback) {
        mJenkinsBuildInfo = jenkinsBuildInfo;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mJenkinsBuildInfoRepository.delete(mJenkinsBuildInfo, mJenkinsBuildInfoDeletedCallback);
    }

    private void onSuccess() {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onJenkinsBuildInfoDeleted();
            }
        });
    }

    private void onErrorOccurred(final ErrorWrap errorWrap) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(errorWrap);
            }
        });
    }
}
