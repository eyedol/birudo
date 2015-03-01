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

import java.util.List;

public class JenkinsBuildInfoJsonModel extends Model {

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

    public boolean isBuilding() {
        return mBuilding;
    }

    public void setBuilding(boolean building) {
        mBuilding = building;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
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

    public List<Actions> getActions() {
        return mActions;
    }

    public void setActions(List<Actions> causes) {
        mActions = causes;
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

            public String getDescription() {
                return mDescription;
            }

            public void setDescription(String description) {
                mDescription = description;
            }

            public String getUserId() {
                return mUserId;
            }

            public void setUserId(String userId) {
                mUserId = userId;
            }

            public String getUserName() {
                return mUserName;
            }

            public void setUserName(String userName) {
                mUserName = userName;
            }

            public String getAddr() {
                return mAddr;
            }

            public void setAddr(String addr) {
                mAddr = addr;
            }

            public String getNote() {
                return mNote;
            }

            public void setNote(String note) {
                mNote = note;
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
}
