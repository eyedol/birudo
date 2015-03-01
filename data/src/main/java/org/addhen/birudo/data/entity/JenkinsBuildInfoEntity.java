package org.addhen.birudo.data.entity;


public class JenkinsBuildInfoEntity extends Entity {

    private String mUserName;

    private String mDisplayName;

    private Result mResult;

    private long mDuration;

    private long mTimestamp;

    private String mUrl;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "JenkinsBuildInfoEntity{" +
                "mUserName='" + mUserName + '\'' +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mResult=" + mResult +
                ", mDuration=" + mDuration +
                ", mTimestamp=" + mTimestamp +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }

    public enum Result {
        SUCCESS,
        FAILURE,
        ABORTED,
        NOT_BUILT,
        UNSTABLE;
    }
}
