package org.addhen.birudo.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.addhen.birudo.data.entity.JenkinsUserEntity;

import java.lang.reflect.Type;

public class JeninksUserEntityJsonMapper {

    private final Gson mGson;

    public JeninksUserEntityJsonMapper() {
        mGson = new Gson();
    }

    public JenkinsUserEntity map(String jeninksUserJsonResponse) throws JsonSyntaxException {
        try {
            Type userEntityType = new TypeToken<JenkinsUserEntity>() {
            }.getType();
            JenkinsUserEntity jenkinsUserEntity = mGson
                    .fromJson(jeninksUserJsonResponse, userEntityType);

            return jenkinsUserEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
