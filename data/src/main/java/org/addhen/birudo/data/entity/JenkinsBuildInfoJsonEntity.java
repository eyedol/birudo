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

package org.addhen.birudo.data.entity;

import com.google.gson.annotations.SerializedName;

import org.addhen.birudo.core.Entity;

import java.util.List;

public class JenkinsBuildInfoJsonEntity extends Entity {

    @SerializedName("id")
    private Long _id;

    @SerializedName("building")
    private boolean mBuilding;

    @SerializedName("fullDisplayName")
    private String mDisplayName;

    @SerializedName("result")
    private Result mResult;

    @SerializedName("duration")
    private long mDuration;

    @SerializedName("timestamp")
    private long mTimestamp;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("actions")
    private List<Actions> mActions;

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public List<Actions> getActions() {
        return mActions;
    }

    public void setActions(List<Actions> actions) {
        mActions = actions;
    }

    public void setBuilding(boolean building) {
        mBuilding = building;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public void setResult(Result result) {
        mResult = result;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public void setDuration(long duration) {
        mDuration = duration;
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

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public enum Result {
        @SerializedName("SUCCESS")
        SUCCESS,

        @SerializedName("FAILURE")
        FAILURE,

        @SerializedName("ABORTED")
        ABORTED,

        @SerializedName("NOT_BUILT")
        NOT_BUILT,

        @SerializedName("UNSTABLE")
        UNSTABLE;
    }



    public static class Actions {

        @SerializedName("causes")
        private List<Cause> mCauses;

        public List<Cause> getCauses() {
            return mCauses;
        }

        public void setCauses(List<Cause> causes) {
            mCauses = causes;
        }

        public static class Cause {

            @SerializedName("shortDescription")
            private String mDescription;

            @SerializedName("userId")
            private String mUserId;

            @SerializedName("userName")
            private String mUserName;

            @SerializedName("addr")
            private String mAddr;

            @SerializedName("note")
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
        return "JenkinsBuildInfoEntity{" +
                "mBuilding=" + mBuilding +
                ", mDisplayName='" + mDisplayName + '\'' +
                ", mResult=" + mResult +
                ", mDuration=" + mDuration +
                ", mTimestamp=" + mTimestamp +
                ", mUrl='" + mUrl + '\'' +
                ", mActions=" + mActions +
                '}';
    }
}
