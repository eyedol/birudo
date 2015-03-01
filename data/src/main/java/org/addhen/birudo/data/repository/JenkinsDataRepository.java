package org.addhen.birudo.data.repository;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.addhen.birudo.data.entity.JenkinsBuildInfoJsonEntity;
import org.addhen.birudo.data.entity.JenkinsUserEntity;
import org.addhen.birudo.data.entity.mapper.JeninksUserEntityJsonMapper;
import org.addhen.birudo.data.entity.mapper.JenkinsBuildInfoEntityDataMapper;
import org.addhen.birudo.data.entity.mapper.JenkinsUserEntityDataMapper;
import org.addhen.birudo.data.exception.RepositoryError;
import org.addhen.birudo.data.net.JenkinsClient;
import org.addhen.birudo.core.entity.GcmRegistrationStatus;
import org.addhen.birudo.core.entity.JenkinsBuildInfoJson;
import org.addhen.birudo.core.entity.JenkinsUser;
import org.addhen.birudo.core.repository.JenkinsRepository;

import java.io.IOException;

public class JenkinsDataRepository implements JenkinsRepository {

    private static JenkinsDataRepository INSTANCE;

    private final JenkinsUserEntityDataMapper mJenkinsUserEntityDataMapper;

    private JeninksUserEntityJsonMapper mJeninksUserEntityJsonMapper;

    private final JenkinsBuildInfoEntityDataMapper mJenkinsBuildInfoEntityDataMapper;

    private GoogleCloudMessaging mGcm;


    private JenkinsDataRepository(JenkinsUserEntityDataMapper jenkinsUserEntityDataMapper,
            JenkinsBuildInfoEntityDataMapper jenkinsBuildInfoEntityDataMapper,
            GoogleCloudMessaging gcm) {
        mJenkinsUserEntityDataMapper = jenkinsUserEntityDataMapper;
        mJenkinsBuildInfoEntityDataMapper = jenkinsBuildInfoEntityDataMapper;
        mJeninksUserEntityJsonMapper = new JeninksUserEntityJsonMapper();
        mGcm = gcm;
    }

    public static synchronized JenkinsDataRepository getInstance(
            JenkinsUserEntityDataMapper jenkinsUserEntityDataMapper,
            JenkinsBuildInfoEntityDataMapper jenkinsBuildInfoEntityDataMapper, GoogleCloudMessaging gcm) {
        if (INSTANCE == null) {
            INSTANCE = new JenkinsDataRepository(jenkinsUserEntityDataMapper,
                    jenkinsBuildInfoEntityDataMapper, gcm);
        }
        return INSTANCE;
    }

    @Override
    public void parseUserJsonString(String jsonString,
            ParseJsonStringCallback parseJsonStringCallback) {
        JenkinsUserEntity jenkinsUserEntity = mJeninksUserEntityJsonMapper.map(jsonString);
        parseJsonStringCallback
                .onJsonStringParsed(mJenkinsUserEntityDataMapper.map(jenkinsUserEntity));
    }

    @Override
    public void requestGCMToken(String senderId,
            RequestGcmTokenCallback requestGcmTokenCallback) {
        try {
            final String token = mGcm.register(senderId);
            requestGcmTokenCallback.onGcmTokenRequested(token);
        } catch (IOException e) {
            requestGcmTokenCallback.onError(new RepositoryError(e));
        }
    }

    @Override
    public void fetchBuildInfo(String url,
            FetchBuildInfoCallback fetchBuildInfoCallback) {

        JenkinsClient jenkinsClient = new JenkinsClient(url);

        JenkinsBuildInfoJson jenkinsBuildInfo;
        JenkinsBuildInfoJsonEntity jenkinsBuildInfoEntity = null;
        try {
           jenkinsBuildInfoEntity = jenkinsClient.fetchBuildInfo();
        } catch (Exception e) {
            fetchBuildInfoCallback.onError(new RepositoryError(e));
        }

        jenkinsBuildInfo = mJenkinsBuildInfoEntityDataMapper.map(jenkinsBuildInfoEntity);

        fetchBuildInfoCallback.onBuildInfoFetched(jenkinsBuildInfo);
    }

    @Override
    public void regsiterGcmTokenOnServer(JenkinsUser jenkinsUser,
            RegisterGcmTokenOnServerCallback registerGcmTokenOnServerCallback) {
        JenkinsUserEntity jenkinsUserEntity = mJenkinsUserEntityDataMapper.unmap(jenkinsUser);
        JenkinsClient jenkinsClient = new JenkinsClient(jenkinsUserEntity);

        GcmRegistrationStatus status = null;
        try {
            status = jenkinsClient.sendGcmTokenToServer();
        } catch (Exception e) {
            registerGcmTokenOnServerCallback.onError(new RepositoryError(e));
        }

        registerGcmTokenOnServerCallback.onGcmTokenRegistered(status);
    }
}
