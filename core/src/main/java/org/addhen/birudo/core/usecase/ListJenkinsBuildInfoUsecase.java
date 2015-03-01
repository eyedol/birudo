package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.exception.ErrorWrap;
import org.addhen.birudo.core.repository.JenkinsBuildInfoRepository;
import org.addhen.birudo.core.task.PostExecutionThread;
import org.addhen.birudo.core.task.ThreadExecutor;

import java.util.List;

public class ListJenkinsBuildInfoUsecase implements ListJenkinsBuildInfo  {

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private Callback mCallback;

    private final JenkinsBuildInfoRepository mJenkinsBuildInfoRepository;

    private JenkinsBuildInfoRepository.JenkinsBuildInfoListCallback mJenkinsBuildInfoListCallback = new JenkinsBuildInfoRepository.JenkinsBuildInfoListCallback() {
        @Override
        public void onJenkinsBuildInfoListLoaded(List<JenkinsBuildInfo> jenkinsBuildInfoList) {
            onSuccessful(jenkinsBuildInfoList);
        }

        @Override
        public void onError(ErrorWrap errorWrap) {
            onErrorOccurred(errorWrap);
        }
    };

    public ListJenkinsBuildInfoUsecase(JenkinsBuildInfoRepository jenkinsBuildInfoRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {

        if(jenkinsBuildInfoRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }

        mJenkinsBuildInfoRepository = jenkinsBuildInfoRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Callback callback) {
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mJenkinsBuildInfoRepository.getList(mJenkinsBuildInfoListCallback);
    }

    private void onSuccessful(final List<JenkinsBuildInfo> jenkinsBuildInfoList) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onJenkinsBuildInfoLoaded(jenkinsBuildInfoList);
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
