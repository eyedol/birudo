package org.addhen.birudo.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class JenkinsUserEntity extends Entity {

    @SerializedName("username")
    private String mUsername;

    @SerializedName("token")
    private String mToken;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("senderId")
    private String mSenderId;

    public JenkinsUserEntity(String username, String token, String url, String senderId) {
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
