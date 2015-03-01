package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.JenkinsBuildInfoJson;
import org.addhen.birudo.core.exception.ErrorWrap;

public interface FetchBuildInfo extends IInteractor {

    void executed(String url, Callback callback);

    interface Callback {

        void onBuildInfoFetched(JenkinsBuildInfoJson buildInfo);

        void onError(ErrorWrap error);
    }
}
