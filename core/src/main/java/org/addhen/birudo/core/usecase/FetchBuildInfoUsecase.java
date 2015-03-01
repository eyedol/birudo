package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.JenkinsBuildInfoJson;
import org.addhen.birudo.core.exception.ErrorWrap;
import org.addhen.birudo.core.repository.JenkinsRepository;
import org.addhen.birudo.core.task.PostExecutionThread;
import org.addhen.birudo.core.task.ThreadExecutor;

public class FetchBuildInfoUsecase implements FetchBuildInfo {

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private final JenkinsRepository mJenkinsRepository;

    private String mUrl;

    private Callback mCallback;

    private JenkinsRepository.FetchBuildInfoCallback mFetchBuildInfoCallback = new JenkinsRepository.FetchBuildInfoCallback() {
        @Override
        public void onBuildInfoFetched(JenkinsBuildInfoJson jenkinsBuildInfo) {
            onBuildInfoSuccessfullyFetched(jenkinsBuildInfo);
        }

        @Override
        public void onError(ErrorWrap errorWrap) {
            onErrorOccurred(errorWrap);
        }
    };

    public FetchBuildInfoUsecase(JenkinsRepository jenkinsRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {

        if (jenkinsRepository == null || threadExecutor == null || postExecutionThread == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null");
        }
        mJenkinsRepository = jenkinsRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;

    }

    @Override
    public void executed(String url, Callback callback) {
        if(url == null || callback == null) {
            throw new IllegalArgumentException("Build URL cannot be null");
        }
        mUrl = url;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mJenkinsRepository.fetchBuildInfo(mUrl, mFetchBuildInfoCallback);
    }

    private void onErrorOccurred(final ErrorWrap errorWrap) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(errorWrap);
            }
        });
    }

    private void onBuildInfoSuccessfullyFetched(final JenkinsBuildInfoJson jenkinsBuildInfo) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onBuildInfoFetched(jenkinsBuildInfo);
            }
        });
    }
}
