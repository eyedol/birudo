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

package org.addhen.birudo.module;

import org.addhen.birudo.core.repository.JenkinsBuildInfoRepository;
import org.addhen.birudo.core.repository.JenkinsRepository;
import org.addhen.birudo.core.task.PostExecutionThread;
import org.addhen.birudo.core.task.ThreadExecutor;
import org.addhen.birudo.core.usecase.AddJenkinsBuildInfoUsecase;
import org.addhen.birudo.core.usecase.DeleteJenkinsBuildInfoUsecase;
import org.addhen.birudo.core.usecase.FetchBuildInfoUsecase;
import org.addhen.birudo.core.usecase.ListJenkinsBuildInfoUsecase;
import org.addhen.birudo.core.usecase.ParseJenkinsUserUsecase;
import org.addhen.birudo.core.usecase.RegisterGCMTokenOnServerUsecase;
import org.addhen.birudo.core.usecase.RequestGCMTokenUsecase;
import org.addhen.birudo.service.GcmIntentService;
import org.addhen.birudo.ui.activity.MainActivity;
import org.addhen.birudo.ui.activity.SettingsActivity;
import org.addhen.birudo.ui.fragment.ListJenkinsBuildInfoFragment;
import org.addhen.birudo.ui.fragment.SettingsFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false,
        injects = {SettingsFragment.class, SettingsActivity.class,
                ListJenkinsBuildInfoFragment.class,
                GcmIntentService.class,
                MainActivity.class})
public class MainActivityModule {

    @Provides
    @Singleton
    RequestGCMTokenUsecase provideRequestGcmToken(JenkinsRepository jenkinsRepository,
                                                  ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new RequestGCMTokenUsecase(jenkinsRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    ParseJenkinsUserUsecase provideParseJenkinsUsecase(JenkinsRepository jenkinsRepository,
                                                       ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new ParseJenkinsUserUsecase(jenkinsRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @Singleton
    RegisterGCMTokenOnServerUsecase provideRegisterGCMTokenOnServerUsecase(
            JenkinsRepository jenkinsRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new RegisterGCMTokenOnServerUsecase(jenkinsRepository, threadExecutor,
                postExecutionThread);
    }

    @Provides
    @Singleton
    ListJenkinsBuildInfoUsecase provideListJenkinsBuildInfoUsecase(
            JenkinsBuildInfoRepository jenkinsRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new ListJenkinsBuildInfoUsecase(jenkinsRepository, threadExecutor,
                postExecutionThread);
    }

    @Provides
    @Singleton
    AddJenkinsBuildInfoUsecase provideAddJenkinsBuildInfoUsecase(
            JenkinsBuildInfoRepository jenkinsRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new AddJenkinsBuildInfoUsecase(jenkinsRepository, threadExecutor,
                postExecutionThread);
    }

    @Provides
    @Singleton
    DeleteJenkinsBuildInfoUsecase provideDeleteJenkinsBuildInfoUsecase(
            JenkinsBuildInfoRepository jenkinsRepository,
            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new DeleteJenkinsBuildInfoUsecase(jenkinsRepository, threadExecutor,
                postExecutionThread);
    }

    @Provides
    FetchBuildInfoUsecase provideFetchBuildInfoUsecase(JenkinsRepository jenkinsRepository,
                                                       ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new FetchBuildInfoUsecase(jenkinsRepository, threadExecutor, postExecutionThread);
    }
}
