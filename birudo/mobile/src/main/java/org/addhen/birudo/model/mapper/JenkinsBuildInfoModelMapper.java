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


import org.addhen.birudo.core.entity.JenkinsBuildInfo;
import org.addhen.birudo.model.JenkinsBuildInfoModel;

import java.util.ArrayList;
import java.util.List;

public class JenkinsBuildInfoModelMapper {

    public JenkinsBuildInfoModel map(JenkinsBuildInfo jenkinsBuildInfoEntity) {
        JenkinsBuildInfoModel jenkinsBuildInfo = null;

        if (jenkinsBuildInfoEntity != null) {
            jenkinsBuildInfo = new JenkinsBuildInfoModel();
            jenkinsBuildInfo.setResult(JenkinsBuildInfoModel.Result
                    .valueOf(jenkinsBuildInfoEntity.getResult().name()));
            jenkinsBuildInfo.setId(jenkinsBuildInfoEntity.getId());
            jenkinsBuildInfo.setTimestamp(jenkinsBuildInfoEntity.getTimestamp());
            jenkinsBuildInfo.setDuration(jenkinsBuildInfoEntity.getDuration());
            jenkinsBuildInfo.setDisplayName(jenkinsBuildInfoEntity.getDisplayName());
            jenkinsBuildInfo.setUserName(jenkinsBuildInfoEntity.getUserName());
            jenkinsBuildInfo.setUrl(jenkinsBuildInfoEntity.getUrl());

        }
        return jenkinsBuildInfo;
    }

    public JenkinsBuildInfo unmap(JenkinsBuildInfoModel jenkinsBuildInfoModel) {
        JenkinsBuildInfo jenkinsBuildInfo = null;

        if (jenkinsBuildInfoModel != null) {
            jenkinsBuildInfo = new JenkinsBuildInfo();
            jenkinsBuildInfo.setResult(
                    JenkinsBuildInfo.Result.valueOf(jenkinsBuildInfoModel.getResult().name()));
            jenkinsBuildInfo.setId(jenkinsBuildInfoModel.getId());
            jenkinsBuildInfo.setTimestamp(jenkinsBuildInfoModel.getTimestamp());
            jenkinsBuildInfo.setDuration(jenkinsBuildInfoModel.getDuration());
            jenkinsBuildInfo.setDisplayName(jenkinsBuildInfoModel.getDisplayName());
            jenkinsBuildInfo.setUserName(jenkinsBuildInfoModel.getUserName());
            jenkinsBuildInfo.setUrl(jenkinsBuildInfoModel.getUrl());
        }

        return jenkinsBuildInfo;
    }

    public List<JenkinsBuildInfoModel> map(List<JenkinsBuildInfo> jenkinsBuildInfoEntities) {
        List<JenkinsBuildInfoModel> buildInfoList = new ArrayList<>();

        if (jenkinsBuildInfoEntities != null) {
            for (JenkinsBuildInfo jenkinsBuildInfoEntity : jenkinsBuildInfoEntities) {
                buildInfoList.add(map(jenkinsBuildInfoEntity));
            }
        }
        return buildInfoList;
    }
}
