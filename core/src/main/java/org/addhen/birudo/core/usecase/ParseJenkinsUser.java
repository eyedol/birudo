package org.addhen.birudo.core.usecase;

import org.addhen.birudo.core.entity.JenkinsUser;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface ParseJenkinsUser extends IInteractor {

    void execute(String jsonString, Callback callback);

    interface Callback {
        void jenkinsUserParsed( JenkinsUser jenkinsUser);
    }
}
