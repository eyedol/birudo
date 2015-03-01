package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.exception.ErrorWrap;

public interface RequestGCMToken extends IInteractor {

    void execute(String senderId, Callback callback);

    interface Callback {

        void onGCMTokenRequested(String token);

        void onError(ErrorWrap error);
    }
}
