package org.addhen.birudo.core.usecase;


import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.exception.ErrorWrap;

import java.util.List;

public interface ListJenkinsBuildInfo extends IInteractor {

    void execute(Callback callback);

    interface Callback {
        void onJenkinsBuildInfoLoaded(List<JenkinsBuildInfo> jenkinsBuildInfoList);

        void onError(ErrorWrap errorWrap);
    }
}
