package org.addhen.birudo.core.entity;

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
