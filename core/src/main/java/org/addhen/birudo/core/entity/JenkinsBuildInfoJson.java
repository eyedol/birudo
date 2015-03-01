package org.addhen.birudo.core.entity;

import java.util.List;

public class JenkinsBuildInfoJson extends Entity {

    private boolean mBuilding;

    private String mDisplayName;

    private Result mResult;

    private long mDuration;

    private long mTimestamp;

    private List<Actions> mActions;

    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public void setBuilding(boolean building) {
        mBuilding = building;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public void setActions(List<Actions> causes) {
        mActions = causes;
    }

    public boolean isBuilding() {
        return mBuilding;
    }

    public Result getResult() {
        return mResult;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public long getDuration() {
        return mDuration;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public List<Actions> getActions() {
        return mActions;
    }

    public enum Result {
        SUCCESS,
        FAILURE,
        ABORTED,
        NOT_BUILT,
        UNSTABLE;
    }

    public static class Actions {

        private List<Cause> mCauses;

        public List<Cause> getCauses() {
            return mCauses;
        }

        public void setCauses(List<Cause> causes) {
            mCauses = causes;
        }

        public static class Cause {

            private String mDescription;

            private String mUserId;

            private String mUserName;

            private String mAddr;

            private String mNote;

            public void setDescription(String description) {
                mDescription = description;
            }

            public void setUserId(String userId) {
                mUserId = userId;
            }

            public void setUserName(String userName) {
                mUserName = userName;
            }

            public void setAddr(String addr) {
                mAddr = addr;
            }

            public void setNote(String note) {
                mNote = note;
            }

            public String getDescription() {
                return mDescription;
            }

            public String getUserId() {
                return mUserId;
            }

            public String getUserName() {
                return mUserName;
            }

            public String getAddr() {
                return mAddr;
            }

            public String getNote() {
                return mNote;
            }

            @Override
            public String toString() {
                return "Cause{" +
                        "mDescription='" + mDescription + '\'' +
                        ", mUserId='" + mUserId + '\'' +
                        ", mUserName='" + mUserName + '\'' +
                        ", mAddr='" + mAddr + '\'' +
                        ", mNote='" + mNote + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "JenkinsBuildInfo{" +
                "mBuilding=" + mBuilding +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mResult=" + mResult +
                ", mDuration=" + mDuration +
                ", mTimestamp=" + mTimestamp +
                ", mCauses=" + mActions +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }

}
