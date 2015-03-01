
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

package org.addhen.birudo.data.entity.mapper;

import org.addhen.birudo.data.entity.JenkinsBuildInfoEntity;
import org.addhen.birudo.core.entity.JenkinsBuildInfo;

import java.util.ArrayList;
import java.util.List;

public class JenkinsBuildInfoEntityMapper {

    public JenkinsBuildInfo map(JenkinsBuildInfoEntity jenkinsBuildInfoEntity) {
        JenkinsBuildInfo jenkinsBuildInfo = null;

        if(jenkinsBuildInfoEntity != null) {
            jenkinsBuildInfo = new JenkinsBuildInfo();
            jenkinsBuildInfo.setResult(JenkinsBuildInfo.Result.valueOf(jenkinsBuildInfoEntity.getResult().name()));
            jenkinsBuildInfo.setId(jenkinsBuildInfoEntity.getId());
            jenkinsBuildInfo.setTimestamp(jenkinsBuildInfoEntity.getTimestamp());
            jenkinsBuildInfo.setDuration(jenkinsBuildInfoEntity.getDuration());
            jenkinsBuildInfo.setDisplayName(jenkinsBuildInfoEntity.getDisplayName());
            jenkinsBuildInfo.setUserName(jenkinsBuildInfoEntity.getUserName());
            jenkinsBuildInfo.setUrl(jenkinsBuildInfoEntity.getUrl());

        }
        return jenkinsBuildInfo;
    }

    public JenkinsBuildInfoEntity unmap(JenkinsBuildInfo jenkinsBuildInfo) {
        JenkinsBuildInfoEntity jenkinsBuildInfoEntity = null;

        if(jenkinsBuildInfo !=null) {
            jenkinsBuildInfoEntity = new JenkinsBuildInfoEntity();
            jenkinsBuildInfoEntity.setResult(JenkinsBuildInfoEntity.Result.valueOf(jenkinsBuildInfo.getResult().name()));
            jenkinsBuildInfoEntity.setId(jenkinsBuildInfo.getId());
            jenkinsBuildInfoEntity.setTimestamp(jenkinsBuildInfo.getTimestamp());
            jenkinsBuildInfoEntity.setDuration(jenkinsBuildInfo.getDuration());
            jenkinsBuildInfoEntity.setDisplayName(jenkinsBuildInfo.getDisplayName());
            jenkinsBuildInfoEntity.setUserName(jenkinsBuildInfo.getUserName());
            jenkinsBuildInfoEntity.setUrl(jenkinsBuildInfo.getUrl());
        }

        return jenkinsBuildInfoEntity;
    }

    public List<JenkinsBuildInfo> map(List<JenkinsBuildInfoEntity> jenkinsBuildInfoEntities) {
        List<JenkinsBuildInfo> buildInfoList = new ArrayList<>();

        if(jenkinsBuildInfoEntities != null) {
            for(JenkinsBuildInfoEntity jenkinsBuildInfoEntity: jenkinsBuildInfoEntities)
            buildInfoList.add(map(jenkinsBuildInfoEntity));
        }
        return buildInfoList;
    }
}
