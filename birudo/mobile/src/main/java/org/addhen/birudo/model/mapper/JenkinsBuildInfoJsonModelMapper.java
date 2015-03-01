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

package org.addhen.birudo.model.mapper;

import org.addhen.birudo.core.entity.JenkinsBuildInfoJson;
import org.addhen.birudo.model.JenkinsBuildInfoJsonModel;

import java.util.ArrayList;
import java.util.List;

public class JenkinsBuildInfoJsonModelMapper {

    public JenkinsBuildInfoJsonModel map(JenkinsBuildInfoJson jenkinsBuildInfoEntity) {

        JenkinsBuildInfoJsonModel jenkinsBuildInfo = null;

        if (jenkinsBuildInfoEntity != null) {
            jenkinsBuildInfo = new JenkinsBuildInfoJsonModel();
            jenkinsBuildInfo.setId(jenkinsBuildInfoEntity.getId());
            jenkinsBuildInfo.setBuilding(jenkinsBuildInfoEntity.isBuilding());
            jenkinsBuildInfo.setDisplayName(jenkinsBuildInfoEntity.getDisplayName());
            jenkinsBuildInfo.setDuration(jenkinsBuildInfoEntity.getDuration());
            jenkinsBuildInfo.setTimestamp(jenkinsBuildInfoEntity.getTimestamp());
            jenkinsBuildInfo.setResult(
                    JenkinsBuildInfoJsonModel.Result
                            .valueOf(jenkinsBuildInfoEntity.getResult().name()));
            jenkinsBuildInfo.setActions(mapp(jenkinsBuildInfoEntity.getActions()));
            jenkinsBuildInfo.setUrl(jenkinsBuildInfoEntity.getUrl());
        }
        return jenkinsBuildInfo;

    }

    private JenkinsBuildInfoJsonModel.Actions map(JenkinsBuildInfoJson.Actions actions) {

        JenkinsBuildInfoJsonModel.Actions action = new JenkinsBuildInfoJsonModel.Actions();
        if (actions != null) {
            action.setCauses(map(actions.getCauses()));
        }
        return action;
    }

    private List<JenkinsBuildInfoJsonModel.Actions> mapp(
            List<JenkinsBuildInfoJson.Actions> actions) {
        List<JenkinsBuildInfoJsonModel.Actions> actionsList = new ArrayList<>();
        if (actions != null) {
            for (JenkinsBuildInfoJson.Actions action : actions) {
                actionsList.add(map(action));
            }
        }

        return actionsList;
    }

    private JenkinsBuildInfoJsonModel.Actions.Cause map(
            JenkinsBuildInfoJson.Actions.Cause causeEntity) {
        JenkinsBuildInfoJsonModel.Actions.Cause cause
                = new JenkinsBuildInfoJsonModel.Actions.Cause();

        if (causeEntity != null) {
            cause.setAddr(causeEntity.getAddr());
            cause.setDescription(causeEntity.getDescription());
            cause.setNote(causeEntity.getNote());
            cause.setUserId(causeEntity.getUserId());
            cause.setUserName(causeEntity.getUserName());
        }
        return cause;
    }

    private List<JenkinsBuildInfoJsonModel.Actions.Cause> map(
            List<JenkinsBuildInfoJson.Actions.Cause> causeList) {
        List<JenkinsBuildInfoJsonModel.Actions.Cause> causes = new ArrayList<>();
        JenkinsBuildInfoJsonModel.Actions.Cause cause;
        if (causeList != null) {
            for (JenkinsBuildInfoJson.Actions.Cause causeEntity : causeList) {
                cause = map(causeEntity);
                if (cause != null) {
                    causes.add(cause);
                }
            }
        }

        return causes;
    }
}
