package org.addhen.birudo.data.entity.mapper;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.addhen.birudo.data.entity.JenkinsBuildInfoJsonEntity;

import java.lang.reflect.Type;

public class JenkinsBuildInfoEntityJsonMapper {

    private final Gson mGson;

    public JenkinsBuildInfoEntityJsonMapper() {
        mGson = new Gson();
    }

    public JenkinsBuildInfoJsonEntity map(String jsonResponse) throws JsonSyntaxException {
        Type jenkinsBuildInfoEntityType = new TypeToken<JenkinsBuildInfoJsonEntity>() {
        }.getType();
        JenkinsBuildInfoJsonEntity jenkinsBuildInfoEntity = mGson
                .fromJson(jsonResponse, jenkinsBuildInfoEntityType);

        return jenkinsBuildInfoEntity;
    }
}
