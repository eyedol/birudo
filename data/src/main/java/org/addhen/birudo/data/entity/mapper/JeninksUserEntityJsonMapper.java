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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.addhen.birudo.data.entity.JenkinsUserEntity;

import java.lang.reflect.Type;

public class JeninksUserEntityJsonMapper {

    private final Gson mGson;

    public JeninksUserEntityJsonMapper() {
        mGson = new Gson();
    }

    public JenkinsUserEntity map(String jeninksUserJsonResponse) throws JsonSyntaxException {
        try {
            Type userEntityType = new TypeToken<JenkinsUserEntity>() {
            }.getType();
            JenkinsUserEntity jenkinsUserEntity = mGson
                    .fromJson(jeninksUserJsonResponse, userEntityType);

            return jenkinsUserEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
