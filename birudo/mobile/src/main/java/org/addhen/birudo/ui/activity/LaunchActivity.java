package org.addhen.birudo.ui.activity;

import org.addhen.birudo.data.qualifier.ActivityContext;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

/**
 * Launches Activity. This is the main class for navigating through the app
 *
 */
public class LaunchActivity {
    private final Context mContext;

    @Inject
    public LaunchActivity(@ActivityContext Context context) {
        mContext = context;
    }

    public void settings() {
        Intent intent = SettingsActivity.getIntent(mContext);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}
