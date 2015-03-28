package org.addhen.birudo.presenter;

import android.text.TextUtils;

import org.addhen.birudo.data.pref.StringPreference;
import org.addhen.birudo.data.qualifier.GcmToken;

import javax.inject.Inject;

/**
 * Created by eyedol on 3/28/15.
 */
public class MainPresenter implements Presenter {

    @GcmToken
    StringPreference mGcmTokenPreference;

    @Inject
    public MainPresenter(@GcmToken StringPreference gcmTokenPreference){
        mGcmTokenPreference = gcmTokenPreference;
    }

    public boolean isAppConfigured() {
        return mGcmTokenPreference != null && !TextUtils.isEmpty(mGcmTokenPreference.get());
    }

    @Override
    public void resume() {
        // Do nothing
    }

    @Override
    public void pause() {
        // Do nothing
    }
}
