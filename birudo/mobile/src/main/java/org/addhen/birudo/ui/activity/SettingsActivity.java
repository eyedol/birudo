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
import org.addhen.birudo.module.MainActivityModule;
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


public class SettingsActivity extends BaseActivity implements SettingsPresenter.View {

    @Inject
    SettingsPresenter mMainPresenter;

    public SettingsActivity() {
        super(R.layout.settings, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter.setView(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.preference_container, new SettingsFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainPresenter.pause();
    }

    @Subscribe
    public void onSenderIdChange(AppState.RefreshGcmTokenEvent event) {
        mMainPresenter.refreshGcmToken();
    }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_LONG).show();
    }
}
