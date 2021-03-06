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

package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.GcmRegistrationStatus;
import org.addhen.birudo.core.entity.JenkinsUser;
import org.addhen.birudo.core.exception.ErrorWrap;
import org.addhen.birudo.core.repository.JenkinsRepository;
import org.addhen.birudo.core.task.PostExecutionThread;
import org.addhen.birudo.core.task.ThreadExecutor;

public class RegisterGCMTokenOnServerUsecase implements RegisterGCMTokenOnServer {

    private final ThreadExecutor mThreadExecutor;

    private final PostExecutionThread mPostExecutionThread;

    private final JenkinsRepository mJenkinsRepository;

    private JenkinsUser mJenkinsUser;

    private Callback mCallback;

    private JenkinsRepository.RegisterGcmTokenOnServerCallback mRegisterGcmTokenOnServerCallback
            = new JenkinsRepository.RegisterGcmTokenOnServerCallback() {
        @Override
        public void onGcmTokenRegistered(GcmRegistrationStatus status) {
            onGCMTokenSuccessfullyRegistered(status);
        }

        @Override
        public void onError(ErrorWrap error) {
            onErrorOccured(error);
        }
    };

    public RegisterGCMTokenOnServerUsecase(JenkinsRepository jenkinsRepository,
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
    public void execute(JenkinsUser user, Callback callback) {
        if (user == null || callback == null) {
            throw new IllegalArgumentException("gcmToken OR Callback cannot be null!!!");
        }
        mJenkinsUser = user;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mJenkinsRepository.regsiterGcmTokenOnServer(mJenkinsUser, mRegisterGcmTokenOnServerCallback);
    }

    private void onGCMTokenSuccessfullyRegistered(final GcmRegistrationStatus status) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGcmTokenRegistered(status);
            }
        });
    }

    private void onErrorOccured(final ErrorWrap error) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(error);
            }
        });
    }
}
