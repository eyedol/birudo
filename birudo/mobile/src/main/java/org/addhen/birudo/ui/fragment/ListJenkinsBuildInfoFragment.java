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

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.EventListener;

import org.addhen.birudo.R;
import org.addhen.birudo.RetrieveJenkinsBuildInfo;
import org.addhen.birudo.model.JenkinsBuildInfoModel;
import org.addhen.birudo.presenter.ListJenkinsBuildInfoPresenter;
import org.addhen.birudo.state.AppState;
import org.addhen.birudo.ui.adapter.JenkinsBuildInfoAdapter;
import org.addhen.birudo.ui.listener.ItemTouchListenerAdapter;
import org.addhen.birudo.ui.listener.SwipeToDismissTouchListener;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;
import timber.log.Timber;

public class ListJenkinsBuildInfoFragment extends BaseRecyclerViewFragment<JenkinsBuildInfoModel,
        JenkinsBuildInfoAdapter> implements ListJenkinsBuildInfoPresenter.View, ActionMode.Callback,
        ItemTouchListenerAdapter.RecyclerViewOnItemClickListener {

    private static ListJenkinsBuildInfoFragment mListJenkinsBuildInfoFragment;

    @Inject
    ListJenkinsBuildInfoPresenter mListJenkinsBuildInfoPresenter;

    @InjectView(android.R.id.empty)
    TextView mEmptyView;

    @Inject
    RetrieveJenkinsBuildInfo mRetrieveJenkinsBuildInfo;

    private SwipeToDismissTouchListener mSwipeToDismissTouchListener;

    private ActionMode mActionMode;

    private List<JenkinsBuildInfoModel> mModelList;

    public ListJenkinsBuildInfoFragment() {
        super(JenkinsBuildInfoAdapter.class, R.layout.list_build_info, 0, android.R.id.list);
    }

    public static ListJenkinsBuildInfoFragment newInstance() {

        if (mListJenkinsBuildInfoFragment == null) {
            mListJenkinsBuildInfoFragment = new ListJenkinsBuildInfoFragment();
        }

        return mListJenkinsBuildInfoFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        if (mListJenkinsBuildInfoPresenter != null) {
            mListJenkinsBuildInfoPresenter.isAppConfigured();
        }

        initRecyclerView();

        if (mListJenkinsBuildInfoPresenter != null) {
            mListJenkinsBuildInfoPresenter.init();
        }

        mModelList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Calling this here because when called in the onResume method the activity doesn't
        // attached in time and causing getActivity() to return a null value.
        if(mListJenkinsBuildInfoPresenter != null) {
            mListJenkinsBuildInfoPresenter.resume();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(mListJenkinsBuildInfoPresenter != null) {
            mListJenkinsBuildInfoPresenter.pause();
        }
    }

    @Override
    public void initPresenter() {
        if(mListJenkinsBuildInfoPresenter != null) {
            mListJenkinsBuildInfoPresenter.setView(this);
        }
    }

    private void setEmptyView() {
        if (mRecyclerViewAdapter != null && mRecyclerViewAdapter.getItemCount() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    private void initRecyclerView() {
        mRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setEmptyView();
            }
        });
        mRecyclerView.addOnItemTouchListener(new ItemTouchListenerAdapter(mRecyclerView, this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        setEmptyView();

        mSwipeToDismissTouchListener = new SwipeToDismissTouchListener(mRecyclerView,
                new SwipeToDismissTouchListener.DismissCallbacks() {

                    @Override
                    public SwipeToDismissTouchListener.SwipeDirection canDismiss(int position) {
                        return SwipeToDismissTouchListener.SwipeDirection.BOTH;
                    }

                    @Override
                    public void onDismiss(RecyclerView view,
                            final List<SwipeToDismissTouchListener.PendingDismissData> dismissData) {
                        List<Integer> positions = new ArrayList<>();
                        for (SwipeToDismissTouchListener.PendingDismissData data : dismissData) {
                            mModelList.add(mRecyclerViewAdapter.getItem(data.position));
                            mRecyclerViewAdapter.removeItem(
                                    mRecyclerViewAdapter.getItem(data.position));
                            positions.add(data.position);
                        }
                        deleteItems(positions);
                    }
                });

        mRecyclerView.addOnItemTouchListener(mSwipeToDismissTouchListener);
    }


    private void setItemsForDeletion(final List<Integer> positions) {
        for (Integer position : positions) {
            mModelList.add(mRecyclerViewAdapter.getItem(position));
            mRecyclerViewAdapter.removeItem(mRecyclerViewAdapter.getItem(position));
        }
        deleteItems(positions);
    }

    private void deleteItems(final List<Integer> positions) {
        SnackbarManager.show(Snackbar.with(getContext())
                .text(getContext().getResources().getQuantityString(R.plurals.items_deleted,
                        positions.size(), positions.size()))
                .actionLabel(getContext().getString(R.string.undo))
                .actionColorResource(R.color.console_text_color)
                .attachToRecyclerView(mRecyclerView)
                .actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        // Restore items
                        for (Integer i : positions) {
                            mRecyclerViewAdapter.addItem(mModelList.get(i), i);
                        }
                    }
                })
                .eventListener(new EventListener() {
                    @Override
                    public void onShow(Snackbar snackbar) {

                    }

                    @Override
                    public void onShowByReplace(Snackbar snackbar) {

                    }

                    @Override
                    public void onShown(Snackbar snackbar) {

                    }

                    @Override
                    public void onDismiss(Snackbar snackbar) {
                        setEmptyView();
                    }

                    @Override
                    public void onDismissByReplace(Snackbar snackbar) {

                    }

                    @Override
                    public void onDismissed(Snackbar snackbar) {
                        //Delete items
                        for (Integer i : positions) {
                            mListJenkinsBuildInfoPresenter.deleteBuildInfo(mModelList.get(i));
                        }
                        clearItems();
                    }
                }));
    }

    public void onSenderIdChange() {
        mListJenkinsBuildInfoPresenter.refreshGcmToken();
    }

    public void onJenkinsBuildInfoFetched(AppState.BuildStateEvent event) {
        mListJenkinsBuildInfoPresenter.addJenkinsBuildInfo(event.getJenkinsBuildInfoModel());
    }


    @Override
    public void setJenkinsBuildInfoListItems(List<JenkinsBuildInfoModel> modelList) {
        mRecyclerViewAdapter.setItems(modelList);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    private void toggleSelection(int position) {
        mRecyclerViewAdapter.toggleSelection(position);
        int count = mRecyclerViewAdapter.getSelectedItemCount();
        mActionMode.setTitle(getContext().getResources().getQuantityString(R.plurals.selected_items,
                count, count));
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.list_jenkins_build_info_cab_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        mSwipeToDismissTouchListener.setEnabled(false);
        mActionMode = mode;
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        boolean result = false;

        if (item.getItemId() == R.id.list_jenkins_build_info_delete) {
            setItemsForDeletion(mRecyclerViewAdapter.getSelectedItems());
            result = true;
        }

        if (mActionMode != null) {
            mActionMode.finish();
        }
        return result;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mSwipeToDismissTouchListener.setEnabled(true);
        mRecyclerViewAdapter.clearSelections();
        mActionMode = null;
    }

    @Override
    public void onItemClick(RecyclerView parent, View clickedView, int position) {
        final String url = mRecyclerViewAdapter.getItem(position).getUrl();

        if (mActionMode != null) {
            toggleSelection(position);
        } else {
            if (url != null || !url.isEmpty()) {

                final String fullUrl = String.format("%s/console", url);
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(fullUrl)));
            }
        }
    }

    @Override
    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
        //Do nothing
        getActivity().startActionMode(this);
        toggleSelection(position);
    }

    private void clearItems() {
        mRecyclerViewAdapter.clearSelections();
        mModelList.clear();
    }
}
