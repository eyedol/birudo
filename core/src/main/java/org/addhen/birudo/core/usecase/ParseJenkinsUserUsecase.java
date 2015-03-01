package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.JenkinsUser;
import org.addhen.birudo.core.repository.JenkinsRepository;
import org.addhen.birudo.core.task.PostExecutionThread;
import org.addhen.birudo.core.task.ThreadExecutor;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class ParseJenkinsUserUsecase implements ParseJenkinsUser {

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private Callback mCallback;

    private String mJsonString;

    private final JenkinsRepository mJenkinsRepository;

    private final JenkinsRepository.ParseJsonStringCallback mParseJsonStringCallback
            = new JenkinsRepository.ParseJsonStringCallback() {
        @Override
        public void onJsonStringParsed(JenkinsUser jenkinsUser) {
            parseJsonString(jenkinsUser);
        }
    };

    public ParseJenkinsUserUsecase(JenkinsRepository jenkinsRepository,
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
    public void execute(String jsonString, Callback callback) {
        if (jsonString == null || callback == null) {
            throw new IllegalArgumentException("JsonString OR Callback cannot be null!!!");
        }
        mJsonString = jsonString;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mJenkinsRepository.parseUserJsonString(mJsonString, mParseJsonStringCallback);
    }

    private void parseJsonString(final JenkinsUser jenkinsUser) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.jenkinsUserParsed(jenkinsUser);
            }
        });
    }
}
