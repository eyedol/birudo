package org.addhen.birudo.presenter;

import android.text.TextUtils;

import org.addhen.birudo.data.pref.StringPreference;
import org.addhen.birudo.data.qualifier.GcmToken;
import org.addhen.birudo.ui.activity.LaunchActivity;

import javax.inject.Inject;

/**
 * Created by eyedol on 3/28/15.
 */
public class MainPresenter implements Presenter {

    @GcmToken
    StringPreference mGcmTokenPreference;

    @Inject
    LaunchActivity launchActivity;

    @Inject
    public MainPresenter(@GcmToken StringPreference gcmTokenPreference){
        mGcmTokenPreference = gcmTokenPreference;
    }

    private boolean isAppConfigured() {
        return mGcmTokenPreference != null && !TextUtils.isEmpty(mGcmTokenPreference.get());
    }

    @Override
    public void resume() {
        if(!isAppConfigured()) {
            launchActivity.settings();
        }
    }

    @Override
    public void pause() {
        // Do nothing
    }
}
