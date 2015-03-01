package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.exception.ErrorWrap;
import org.addhen.birudo.core.repository.JenkinsBuildInfoRepository;
import org.addhen.birudo.core.task.PostExecutionThread;
import org.addhen.birudo.core.task.ThreadExecutor;

public class AddJenkinsBuildInfoUsecase implements AddJenkinsBuildInfo {

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private final JenkinsBuildInfoRepository mJenkinsBuildInfoRepository;

    private Callback mCallback;

    private JenkinsBuildInfo mJenkinsBuildInfo;

    private JenkinsBuildInfoRepository.JenkinsBuildInfoPutCallback mJenkinsBuildInfoPutCallback =
            new JenkinsBuildInfoRepository.JenkinsBuildInfoPutCallback() {
                @Override
                public void onJenkinsBuildInfoPut() {
                    onSuccess();
                }

                @Override
                public void onError(ErrorWrap error) {
                    onErrorOccurred(error);
                }
            };

    public AddJenkinsBuildInfoUsecase(JenkinsBuildInfoRepository jenkinsBuildInfoRepository,
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
    public void execute(JenkinsBuildInfo jenkinsBuildInfo, Callback callback) {
        mJenkinsBuildInfo = jenkinsBuildInfo;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mJenkinsBuildInfoRepository.put(mJenkinsBuildInfo, mJenkinsBuildInfoPutCallback);
    }

    private void onSuccess() {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onJenkinsBuildInfoAdded();
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
