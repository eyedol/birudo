package org.addhen.birudo.data.entity.mapper;

import org.addhen.birudo.data.entity.JenkinsUserEntity;
import org.addhen.birudo.core.entity.JenkinsUser;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class JenkinsUserEntityDataMapper {

    public JenkinsUser map(JenkinsUserEntity jenkinsUserEntity) {
        JenkinsUser jenkinsUser = null;
        if (jenkinsUserEntity != null) {
            jenkinsUser = new JenkinsUser(jenkinsUserEntity.getUsername(),
                    jenkinsUserEntity.getToken(),
                    jenkinsUserEntity.getUrl(), jenkinsUserEntity.getSenderId());
        }

        return jenkinsUser;
    }

    public JenkinsUserEntity unmap(JenkinsUser jenkinsUser) {
        JenkinsUserEntity jenkinsUserEntity = null;
        if (jenkinsUser != null) {
            jenkinsUserEntity = new JenkinsUserEntity(jenkinsUser.getUsername(),
                    jenkinsUser.getToken(),
                    jenkinsUser.getUrl(), jenkinsUser.getSenderId());
        }

        return jenkinsUserEntity;
    }
}
