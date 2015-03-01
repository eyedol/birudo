package org.addhen.birudo.data.net;

import com.google.gson.Gson;

import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Response;

import org.addhen.birudo.data.entity.CrumbInfoEntity;
import org.addhen.birudo.data.entity.JenkinsBuildInfoJsonEntity;
import org.addhen.birudo.data.entity.JenkinsUserEntity;
import org.addhen.birudo.data.entity.mapper.JenkinsBuildInfoEntityJsonMapper;
import org.addhen.birudo.core.entity.GcmRegistrationStatus;

import java.io.IOException;

public class JenkinsClient extends BaseHttpClient {

    JenkinsUserEntity mJenkinsUserEntity;

    public JenkinsClient(String url) {
        this(url, null);
    }

    public JenkinsClient(JenkinsUserEntity jenkinsUserEntity) {
        this(jenkinsUserEntity.getUrl(), jenkinsUserEntity);
    }

    public JenkinsClient(String url, JenkinsUserEntity jenkinsUserEntity) {
        super(url);
        mJenkinsUserEntity = jenkinsUserEntity;
    }

    private CrumbInfoEntity retrieveCrumbInfo() {
        if (mJenkinsUserEntity == null) {
            return null;
        }
        super.url = String.format("%scrumbIssuer/api/json", mJenkinsUserEntity.getUrl());
        try {
            setMethod(HttpMethod.POST);
            execute();
        } catch (Exception e) {
            log("failed to set request method.", e);
        }

        final int statuCode = getResponse().code();
        if (statuCode == 404) {
            return null;
        }

        final Response response = getResponse();
        final Gson gson = new Gson();
        String resp = "";
        try {
            resp = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(resp, CrumbInfoEntity.class);
    }

    public GcmRegistrationStatus sendGcmTokenToServer() throws Exception {
        if (mJenkinsUserEntity == null) {
            return null;
        }

        // Retrieve crumbInfo
        CrumbInfoEntity crumbInfoEntity = retrieveCrumbInfo();
        if (crumbInfoEntity != null && crumbInfoEntity.isCsrfEnabled()) {
            setHeader(crumbInfoEntity.getCrumbRequestField(), crumbInfoEntity.getCrumb());
        }

        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();

        formEncodingBuilder.add("token", mJenkinsUserEntity.getSenderId());

        String credential = Credentials
                .basic(mJenkinsUserEntity.getUsername(), mJenkinsUserEntity.getToken());

        setHeader("Authorization", credential);

        setRequestBody(formEncodingBuilder.build());

        // Send to gcm/regsiter
        super.url = String.format("%sgcm/register", mJenkinsUserEntity.getUrl());
        setMethod(HttpMethod.POST);
        execute();

        final int statusCode = getResponse().code();
        GcmRegistrationStatus status = new GcmRegistrationStatus();
        status.setStatusCode(statusCode);
        if (statusCode != 200) {
            log("Sending registering token failed with satus code %s", statusCode);
            status.setStatus(false);
        } else {
            status.setStatus(true);
        }

        return status;
    }

    public JenkinsBuildInfoJsonEntity fetchBuildInfo() throws Exception {
        // Send info request
        final String tmpUrl = super.url;
        url = String.format("%s/api/json",tmpUrl);
        setMethod(HttpMethod.GET);
        execute();

        JenkinsBuildInfoEntityJsonMapper jsonMapper = new JenkinsBuildInfoEntityJsonMapper();

        final String jsonReponse = getResponse().body().string();
        return jsonMapper.map(jsonReponse);

    }
}
