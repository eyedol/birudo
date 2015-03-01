package org.addhen.birudo.core.entity;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class JenkinsUser extends Entity {

    private String mUsername;
    private String mToken;
    private String mUrl;
    private String mSenderId;

    public JenkinsUser(String username, String token, String url, String senderId) {
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
