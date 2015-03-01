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
