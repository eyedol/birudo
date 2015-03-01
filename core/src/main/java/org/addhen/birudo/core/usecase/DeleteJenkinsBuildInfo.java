package org.addhen.birudo.core.usecase;


import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.core.exception.ErrorWrap;

public interface DeleteJenkinsBuildInfo extends IInteractor {

    void execute(JenkinsBuildInfo jenkinsBuildInfo, Callback callback);

    interface  Callback {

        void onJenkinsBuildInfoDeleted();

        void onError(ErrorWrap errorWrap);
    }
}
