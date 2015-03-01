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
