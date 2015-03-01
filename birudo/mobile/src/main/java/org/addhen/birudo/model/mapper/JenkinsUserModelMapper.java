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

package org.addhen.birudo.model.mapper;

import org.addhen.birudo.core.entity.JenkinsUser;
import org.addhen.birudo.model.JenkinsUserModel;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class JenkinsUserModelMapper {

    public JenkinsUserModelMapper() {

    }

    public JenkinsUserModel map(JenkinsUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot map a null value");
        }

        JenkinsUserModel jenkinsUserModel = new JenkinsUserModel(user.getUsername(),
                user.getToken(), user.getUrl(), user.getSenderId());
        return jenkinsUserModel;
    }

    public JenkinsUser unmap(JenkinsUserModel jenkinsUserModel) {
        if (jenkinsUserModel == null) {
            throw new IllegalArgumentException("Cannot map a null value");
        }

        JenkinsUser jenkinsUser = new JenkinsUser(jenkinsUserModel.getUsername(),
                jenkinsUserModel.getToken(), jenkinsUserModel.getUrl(),
                jenkinsUserModel.getSenderId());
        return jenkinsUser;
    }
}
