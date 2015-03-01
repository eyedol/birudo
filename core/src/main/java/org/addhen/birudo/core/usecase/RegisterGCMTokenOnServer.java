package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.GcmRegistrationStatus;
import org.addhen.birudo.core.entity.JenkinsUser;
import org.addhen.birudo.core.exception.ErrorWrap;

public interface RegisterGCMTokenOnServer extends IInteractor {

    void execute(JenkinsUser user, Callback callback);

    interface Callback {

        void onGcmTokenRegistered(GcmRegistrationStatus status);

        void onError(ErrorWrap error);
    }
}
