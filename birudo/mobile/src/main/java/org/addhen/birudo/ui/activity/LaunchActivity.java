package org.addhen.birudo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.addhen.birudo.data.qualifier.ActivityContext;

import javax.inject.Inject;

/**
 * Launches Activity. This is the main class for navigating through the app
 */
public class LaunchActivity {
    private final Context mContext;

    @Inject
    public LaunchActivity(@ActivityContext Context context) {
        mContext = context;
    }

    public void settings() {
        mContext.startActivity(settingsIntent());
    }

    public void settings(Bundle bundle) {
        Intent intent = settingsIntent();
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private Intent settingsIntent() {
        Intent intent = SettingsActivity.getIntent(mContext);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
