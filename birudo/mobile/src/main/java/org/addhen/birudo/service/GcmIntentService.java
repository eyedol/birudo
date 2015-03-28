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

package org.addhen.birudo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.addhen.birudo.BirudoApplication;
import org.addhen.birudo.RetrieveJenkinsBuildInfo;
import org.addhen.birudo.module.MainActivityModule;
import org.addhen.birudo.receiver.GcmBroadcastReceiver;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GcmIntentService extends IntentService {

    private final static String TAG = GcmIntentService.class.getSimpleName();

    private final static String GCM_KEY_COMITTERS = "i";

    private final static String GCM_KEY_MESSAGE = "m";

    @Inject
    RetrieveJenkinsBuildInfo mRetrieveJenkinsBuildInfo;


    public GcmIntentService() {
        super("GcmIntentService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        injectDependencies();
    }

    public void injectDependencies() {
        List<Object> modules = new LinkedList<>();
        modules.add(new MainActivityModule());

        BirudoApplication application = (BirudoApplication) getApplication();
        application.add(modules).inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {

            // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                Timber.d("GCM_RECEIVED " + extras.toString() + " message " + extras
                        .getString(GCM_KEY_COMITTERS));

                //showMessage(extras.getString(GCM_KEY_MESSAGE));
                Timber.d(TAG + extras.getString(GCM_KEY_MESSAGE));
                mRetrieveJenkinsBuildInfo.execute(extras.getString(GCM_KEY_MESSAGE));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }

    protected void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

}