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

package org.addhen.birudo.model;

public class JenkinsUserModel extends Model {

    private String mUsername;

    private String mToken;

    private String mUrl;

    private String mSenderId;

    public JenkinsUserModel(String username, String token, String url, String senderId) {
        mUsername = username;
        mToken = token;
        mUrl = url;
        mSenderId = senderId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getToken() {
        return mToken;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getSenderId() {
        return mSenderId;
    }

    @Override
    public String toString() {
        return "JenkinsUser{" +
                "mUsername='" + mUsername + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mSenderId='" + mSenderId + '\'' +
                '}';
    }

}
