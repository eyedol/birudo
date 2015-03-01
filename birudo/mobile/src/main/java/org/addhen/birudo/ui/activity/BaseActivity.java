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

import org.addhen.birudo.BirudoroguApplication;
import org.addhen.birudo.module.ActivityModule;
import org.addhen.birudo.state.AppState;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

public abstract class BaseActivity extends ActionBarActivity {

    /**
     * Layout resource id
     */
    protected final int mLayout;

    /**
     * Menu resource id
     */
    protected final int mMenu;

    @Inject
    AppState mAppState;

    private ObjectGraph activityScopeGraph;

    public BaseActivity(int layout, int menu) {
        mLayout = layout;
        mMenu = menu;
    }

    protected abstract List<Object> getModules();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();

        if (mLayout != 0) {
            setContentView(mLayout);
            injectViews();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mMenu != 0) {
            getMenuInflater().inflate(mMenu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        mAppState.registerEvent(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAppState.unregisterEvent(this);
        super.onPause();
    }

    private void injectDependencies() {

        BirudoroguApplication application = (BirudoroguApplication) getApplication();

        List<Object> activityScopeModules = getModules();

        if (activityScopeModules != null) {
            activityScopeModules.add(new ActivityModule(this));
            activityScopeGraph = application.add(activityScopeModules);
            inject(this);
        }
    }

    public void inject(Object object) {
        if (activityScopeGraph != null) {
            activityScopeGraph.inject(object);
        }
    }

    private void injectViews() {
        ButterKnife.inject(this);
    }
}
