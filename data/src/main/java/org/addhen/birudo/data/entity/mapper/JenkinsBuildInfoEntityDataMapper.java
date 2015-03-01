package org.addhen.birudo.data.entity.mapper;

import org.addhen.birudo.data.entity.JenkinsBuildInfoJsonEntity;
import org.addhen.birudo.core.entity.JenkinsBuildInfoJson;

import java.util.ArrayList;
import java.util.List;

public class JenkinsBuildInfoEntityDataMapper {

    public JenkinsBuildInfoJson map(JenkinsBuildInfoJsonEntity jenkinsBuildInfoEntity) {

        JenkinsBuildInfoJson jenkinsBuildInfo = null;

        if (jenkinsBuildInfoEntity != null) {
            jenkinsBuildInfo = new JenkinsBuildInfoJson();
            jenkinsBuildInfo.setId(jenkinsBuildInfoEntity.getId());
            jenkinsBuildInfo.setBuilding(jenkinsBuildInfoEntity.isBuilding());
            jenkinsBuildInfo.setDisplayName(jenkinsBuildInfoEntity.getDisplayName());
            jenkinsBuildInfo.setDuration(jenkinsBuildInfoEntity.getDuration());
            jenkinsBuildInfo.setTimestamp(jenkinsBuildInfoEntity.getTimestamp());
            jenkinsBuildInfo.setResult(
                    JenkinsBuildInfoJson.Result.valueOf(jenkinsBuildInfoEntity.getResult().name()));
            jenkinsBuildInfo.setActions(mapp(jenkinsBuildInfoEntity.getActions()));
            jenkinsBuildInfo.setUrl(jenkinsBuildInfoEntity.getUrl());

        }
        return jenkinsBuildInfo;

    }

    private JenkinsBuildInfoJson.Actions map(JenkinsBuildInfoJsonEntity.Actions actions) {

        JenkinsBuildInfoJson.Actions action = new JenkinsBuildInfoJson.Actions();
        if(actions != null) {

            action.setCauses(map(actions.getCauses()));
        }
        return action;
    }

    private List<JenkinsBuildInfoJson.Actions> mapp(List<JenkinsBuildInfoJsonEntity.Actions> actions) {
        List<JenkinsBuildInfoJson.Actions> actionsList = new ArrayList<>();
        if(actions !=null) {
            for(JenkinsBuildInfoJsonEntity.Actions action : actions) {
                actionsList.add(map(action));
            }
        }
        return actionsList;
    }

    private JenkinsBuildInfoJson.Actions.Cause map(
            JenkinsBuildInfoJsonEntity.Actions.Cause causeEntity) {
        JenkinsBuildInfoJson.Actions.Cause cause = new JenkinsBuildInfoJson.Actions.Cause();

        if (causeEntity != null) {
            cause.setAddr(causeEntity.getAddr());
            cause.setDescription(causeEntity.getDescription());
            cause.setNote(causeEntity.getNote());
            cause.setUserId(causeEntity.getUserId());
            cause.setUserName(causeEntity.getUserName());
        }
        return cause;
    }

    private List<JenkinsBuildInfoJson.Actions.Cause> map(
            List<JenkinsBuildInfoJsonEntity.Actions.Cause> causeList) {
        List<JenkinsBuildInfoJson.Actions.Cause> causes = new ArrayList<>();
        JenkinsBuildInfoJson.Actions.Cause cause;
        if (causeList != null) {
            for (JenkinsBuildInfoJsonEntity.Actions.Cause causeEntity : causeList) {
                cause = map(causeEntity);
                if (cause != null) {
                    causes.add(cause);
                }
            }
        }

        return causes;
    }
}
