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

package org.addhen.birudo.ui.activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.squareup.otto.Subscribe;

import org.addhen.birudo.R;
import org.addhen.birudo.module.MainActivityModule;
import org.addhen.birudo.state.AppState;
import org.addhen.birudo.ui.fragment.ListJenkinsBuildInfoFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public MainActivity() {
        super(R.layout.activity_main, R.menu.menu_main);
    }

    ListJenkinsBuildInfoFragment mListJenkinsBuildInfoFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mListJenkinsBuildInfoFragment = ListJenkinsBuildInfoFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.list_container, mListJenkinsBuildInfoFragment)
                    .commit();
        }

    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new LinkedList<>();
        modules.add(new MainActivityModule());
        return modules;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        checkPlayServices();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


   /* @Subscribe
    public void onJenkinsBuildInfoFetched(AppState.BuildStateEvent event) {
        final JenkinsBuildInfoModel buildInfoModel = event.getJenkinsBuildInfoModel();

        if (buildInfoModel != null) {
            mBuildInfoLayout.setVisibility(View.VISIBLE);
            mNoBuildInfoLayout.setVisibility(View.GONE);

            if (buildInfoModel.getResult() == JenkinsBuildInfoModel.Result.SUCCESS) {
                mStatus.setBackground(
                        getContext().getResources().getDrawable(R.drawable.success_notification_bg));
            }

            mDisplayName.setText(buildInfoModel.getDisplayName());

            mBuildStatus.setText(getString(R.string.build_status, buildInfoModel.getResult().name(),
                    AppUtil.formattedDuration(buildInfoModel.getDuration())));

            if(buildInfoModel.getActions() != null && buildInfoModel.getActions().size() > 0) {
               String by = buildInfoModel.getActions().get(1).getCauses().get(0).getAddr() != null ?
                       buildInfoModel.getActions().get(1).getCauses().get(0).getAddr()
                        : buildInfoModel.getActions().get(1).getCauses().get(0).getUserId();

                mStartedBy.setText(getString(R.string.started_by, by));
            }

            mUrl = buildInfoModel.getUrl();


        }
    }*/

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    @Subscribe
    public void onJenkinsBuildInfoFetched(AppState.BuildStateEvent event) {
        mListJenkinsBuildInfoFragment.onJenkinsBuildInfoFetched(event);
    }

    @Subscribe
    public void onSenderIdChange(AppState.RefreshGcmTokenEvent event) {
        mListJenkinsBuildInfoFragment.onSenderIdChange();
    }

}
