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

package org.addhen.birudo.ui.fragment;

import org.addhen.birudo.ui.activity.BaseActivity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    /**
     * Layout resource mId
     */
    protected final int mLayout;

    /**
     * Menu resource mId
     */
    protected final int mMenu;

    /**
     * BaseFragment
     *
     * @param menu mMenu resource mId
     */
    public BaseFragment(int layout, int menu) {
        this.mLayout = layout;
        this.mMenu = menu;
    }

    /**
     * Initializes the {@link org.addhen.birudo.presenter.Presenter} for this fragment in a MVP
     * pattern used to architect the application presentation layer.
     */
    public abstract void initPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPresenter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        injectDependencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        android.view.View root = null;
        if (mLayout != 0) {
            root = inflater.inflate(mLayout, container, false);
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectViews(view);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (this.mMenu != 0) {
            inflater.inflate(this.mMenu, menu);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }


    /**
     * Replace every field annotated using @Inject annotation with the provided dependency specified
     * inside a Dagger module value.
     */
    private void injectDependencies() {
        ((BaseActivity) getActivity()).inject(this);
    }

    /**
     * Replace every field annotated with ButterKnife annotations like @InjectView with the proper
     * value.
     *
     * @param view to extract each widget injected in the fragment.
     */
    private void injectViews(final View view) {
        ButterKnife.inject(this, view);
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message A message resource
     */
    protected void showToast(int message) {
        Toast.makeText(getActivity(), getText(message), Toast.LENGTH_LONG)
                .show();
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message A message string
     */
    protected void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG)
                .show();
    }

}
