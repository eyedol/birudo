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

import com.squareup.otto.Subscribe;

import org.addhen.birudo.R;
import org.addhen.birudo.model.JenkinsBuildInfoModel;
import org.addhen.birudo.module.MainActivityModule;
import org.addhen.birudo.presenter.ListJenkinsBuildInfoPresenter;
import org.addhen.birudo.presenter.SettingsPresenter;
import org.addhen.birudo.state.AppState;
import org.addhen.birudo.ui.fragment.SettingsFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;


public class SettingsActivity extends BaseActivity implements ListJenkinsBuildInfoPresenter.View {

    @Inject
    ListJenkinsBuildInfoPresenter mListJenkinsBuildInfoPresenter;

    public SettingsActivity() {
        super(R.layout.settings, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListJenkinsBuildInfoPresenter.setView(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.preference_container, new SettingsFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Subscribe
    public void onSenderIdChange(AppState.RefreshGcmTokenEvent event) {
        mListJenkinsBuildInfoPresenter.refreshGcmToken();
    }

    protected List<Object> getModules() {
        List<Object> modules = new LinkedList<>();
        modules.add(new MainActivityModule());
        return modules;
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public void setJenkinsBuildInfoListItems(List<JenkinsBuildInfoModel> modelList) {
        //Do nothing
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getAppContext(), message, Toast.LENGTH_LONG).show();
    }
}
