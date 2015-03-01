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

package org.addhen.birudo.data.entity.mapper;

import org.addhen.birudo.data.entity.JenkinsUserEntity;
import org.addhen.birudo.core.entity.JenkinsUser;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class JenkinsUserEntityDataMapper {

    public JenkinsUser map(JenkinsUserEntity jenkinsUserEntity) {
        JenkinsUser jenkinsUser = null;
        if (jenkinsUserEntity != null) {
            jenkinsUser = new JenkinsUser(jenkinsUserEntity.getUsername(),
                    jenkinsUserEntity.getToken(),
                    jenkinsUserEntity.getUrl(), jenkinsUserEntity.getSenderId());
        }

        return jenkinsUser;
    }

    public JenkinsUserEntity unmap(JenkinsUser jenkinsUser) {
        JenkinsUserEntity jenkinsUserEntity = null;
        if (jenkinsUser != null) {
            jenkinsUserEntity = new JenkinsUserEntity(jenkinsUser.getUsername(),
                    jenkinsUser.getToken(),
                    jenkinsUser.getUrl(), jenkinsUser.getSenderId());
        }

        return jenkinsUserEntity;
    }
}
