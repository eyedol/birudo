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

package org.addhen.birudo.core.entity;

import org.addhen.birudo.core.Entity;

public class GcmRegistrationStatus extends Entity {
    private int mStatusCode;
    private boolean mStatus;

    public void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public boolean isStatus() {
        return mStatus;
    }

    @Override
    public String toString() {
        return "GcmRegistrationStatus{" +
                "mStatusCode=" + mStatusCode +
                ", mStatus=" + mStatus +
                '}';
    }
}
