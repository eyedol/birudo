package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.exception.ErrorWrap;
import org.addhen.birudo.core.repository.JenkinsRepository;
import org.addhen.birudo.core.task.PostExecutionThread;
import org.addhen.birudo.core.task.ThreadExecutor;

public class RequestGCMTokenUsecase implements RequestGCMToken {

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private final JenkinsRepository mJenkinsRepository;

    private String mSenderId;

    private Callback mCallback;

    private JenkinsRepository.RequestGcmTokenCallback mRequestGcmTokenCallback = new JenkinsRepository.RequestGcmTokenCallback() {
        @Override
        public void onGcmTokenRequested(String token) {
            onGCMTokenSuccessfullyRequested(token);
        }

        @Override
        public void onError(ErrorWrap error) {
            onErrorOccurred(error);
        }
    };

    public RequestGCMTokenUsecase(JenkinsRepository jenkinsRepository,
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
    public void execute(String senderId, Callback callback) {
        if (senderId == null || callback == null) {
            throw new IllegalArgumentException("SenderId OR Callback cannot be null!!!");
        }
        mSenderId = senderId;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mJenkinsRepository.requestGCMToken(mSenderId, mRequestGcmTokenCallback);
    }

    private void onGCMTokenSuccessfullyRequested(final String token) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGCMTokenRequested(token);
            }
        });
    }

    private void onErrorOccurred(final ErrorWrap error) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(error);
            }
        });
    }
}
