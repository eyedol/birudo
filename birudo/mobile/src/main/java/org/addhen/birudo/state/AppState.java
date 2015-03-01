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

package org.addhen.birudo.state;

import com.squareup.otto.Bus;

import org.addhen.birudo.model.JenkinsBuildInfoJsonModel;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AppState implements State, SenderIdState, BuildState {

    private final Bus mEventBus;

    public AppState(Bus eventBus) {
        mEventBus = eventBus;
    }

    @Override
    public void registerEvent(Object receiver) {
        mEventBus.register(receiver);
    }

    @Override
    public void unregisterEvent(Object receiver) {
        mEventBus.unregister(receiver);
    }

    @Override
    public void onSenderIdChange() {
        mEventBus.post(new RefreshGcmTokenEvent());
    }

    @Override
    public void onBuildStatusReceived(JenkinsBuildInfoJsonModel jenkinsBuildInfoModel) {
        mEventBus.post(new BuildStateEvent(jenkinsBuildInfoModel));
    }
}