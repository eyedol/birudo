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

import android.app.Activity;
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
import org.addhen.birudo.ui.listener.SwipeToDismissTouchListener.PendingDismissData;
import org.addhen.birudo.ui.widget.SimpleDividerItemDecoration;
import org.addhen.birudo.util.AppUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

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

    private List<PendingDeletedJenkinsBuildInfoModel> mPendingList;

    private static final String CAB_ENABLED = "can_enabled";

    private static final String SELECTED_ITEMS = "selected_items";

    private boolean isCabEnabled = false;

    private ArrayList<Integer> selectedItemPositions;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        mListJenkinsBuildInfoPresenter.isAppConfigured();
        mPendingList = new ArrayList<>();
        initRecyclerView();
        if (savedInstance != null) {
            isCabEnabled = savedInstance.getBoolean(CAB_ENABLED);
            selectedItemPositions = savedInstance.getIntegerArrayList(SELECTED_ITEMS);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListJenkinsBuildInfoPresenter.resume();
        restoreCabItem();
    }

    @Override
    public void onPause() {
        super.onPause();
        mListJenkinsBuildInfoPresenter.pause();
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putBoolean(CAB_ENABLED, isCabEnabled);
        state.putIntegerArrayList(SELECTED_ITEMS, new ArrayList<>(mRecyclerViewAdapter.getSelectedItems()));
        super.onSaveInstanceState(state);
    }

    @Override
    public void initPresenter() {
        if (mListJenkinsBuildInfoPresenter != null) {
            mListJenkinsBuildInfoPresenter.setView(this);
        }
    }

    /**
     * Restore CAB items when there is a screen orientation
     */
    private void restoreCabItem() {

        // Start CAB if it was started before screen orientation
        if (isCabEnabled) {
            getActivity().startActionMode(this);
        }

        // Toggle selection based on the saved state
        if(selectedItemPositions !=null) {
            for(Integer position: selectedItemPositions) {
                toggleSelection(position);
            }
        }
    }

    /**
     * Set a view to indicate that list is empty
     */
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
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        setEmptyView();

        swipeToDelete();
    }

    private void swipeToDelete() {
        mSwipeToDismissTouchListener = new SwipeToDismissTouchListener(mRecyclerView,
                new SwipeToDismissTouchListener.DismissCallbacks() {

                    @Override
                    public SwipeToDismissTouchListener.SwipeDirection canDismiss(int position) {
                        return SwipeToDismissTouchListener.SwipeDirection.BOTH;
                    }

                    @Override
                    public void onDismiss(RecyclerView view,
                                          final List<PendingDismissData> dismissData) {
                        for (PendingDismissData data : dismissData) {
                            mPendingList.add(
                                    new PendingDeletedJenkinsBuildInfoModel(data.position,
                                            mRecyclerViewAdapter.getItem(data.position)));

                            // Soft delete items by removing them from the adapter. This will allow
                            // them to be restored by the user.
                            mRecyclerViewAdapter.removeItem(
                                    mRecyclerViewAdapter.getItem(data.position));
                        }
                        deleteItems();
                    }
                });

        mRecyclerView.addOnItemTouchListener(mSwipeToDismissTouchListener);
    }


    private void setItemsForMultipleDeletion() {
        // Add selected items to the pending list for deletion.
        addItemsForDeletion();
        for (PendingDeletedJenkinsBuildInfoModel model : mPendingList) {
            mRecyclerViewAdapter.removeItem(model.jenkinsBuildInfoModel);
        }
        deleteItems();
    }

    /**
     * Shows a snackbar showing the number of items deleted and offers the options to undo the deletion.
     */
    private void deleteItems() {
        // Sort in ascending order for restoring deleted items
        Comparator cmp = Collections.reverseOrder();
        Collections.sort(mPendingList, cmp);

        SnackbarManager.show(Snackbar.with(getAppContext())
                .text(getAppContext().getResources().getQuantityString(R.plurals.items_deleted,
                        mPendingList.size(), mPendingList.size()))
                .actionLabel(getAppContext().getString(R.string.undo))
                .actionColorResource(R.color.console_text_color)
                .attachToRecyclerView(mRecyclerView)
                .actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        // Restore items to their respective positions in the adapter.
                        for (PendingDeletedJenkinsBuildInfoModel pendingDeletedJenkinsBuildInfoModel : mPendingList) {
                            mRecyclerViewAdapter.addItem(pendingDeletedJenkinsBuildInfoModel.jenkinsBuildInfoModel,
                                    pendingDeletedJenkinsBuildInfoModel.position);
                        }
                        clearItems();

                    }
                })
                .eventListener(new EventListener() {
                    @Override
                    public void onShow(Snackbar snackbar) {
                        // Do nothing.
                    }

                    @Override
                    public void onShowByReplace(Snackbar snackbar) {
                        // Do nothing.
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        // Do nothing.
                    }

                    @Override
                    public void onDismiss(Snackbar snackbar) {
                        // Permanently delete items from the local database.
                        if (!snackbar.isActionClicked()) {
                            // Make sure the list of items to be deleted is not empty before
                            // attempting to delete them.
                            if (!AppUtil.isCollectionEmpty(mPendingList)) {
                                for (PendingDeletedJenkinsBuildInfoModel pendingDeletedJenkinsBuildInfoModel : mPendingList) {
                                    mListJenkinsBuildInfoPresenter.deleteBuildInfo(pendingDeletedJenkinsBuildInfoModel.jenkinsBuildInfoModel);
                                }
                            }
                            clearItems();
                        }
                        setEmptyView();
                    }

                    @Override
                    public void onDismissByReplace(Snackbar snackbar) {

                    }

                    @Override
                    public void onDismissed(Snackbar snackbar) {

                    }
                }));
    }

    /**
     * To be called by the activity holding this fragment so it causes the GCM Token
     * refresh.
     */
    public void onSenderIdChange() {
        mListJenkinsBuildInfoPresenter.refreshGcmToken();
    }

    /**
     * To be called by the activity holding this fragment so it adds a jenkins notification
     * to the local database.
     *
     * @param event The {@link org.addhen.birudo.state.BuildState.BuildStateEvent} event triggered.
     */
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
    public Context getAppContext() {
        return getActivity();
    }

    /**
     * Select or de-select an item in the adapter and show the count in the contextual actionbar's
     * title.
     *
     * @param position The position of the item to be marked.
     */
    private void toggleSelection(int position) {
        mRecyclerViewAdapter.toggleSelection(position);
        int count = mRecyclerViewAdapter.getSelectedItemCount();
        mActionMode.setTitle(getAppContext().getResources().getQuantityString(R.plurals.selected_items,
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
            setItemsForMultipleDeletion();
            result = true;
        }

        if (mActionMode != null) {
            mActionMode.finish();
        }
        return result;
    }


    private void addItemsForDeletion() {
        for (Integer position : mRecyclerViewAdapter.getSelectedItems()) {
            mPendingList.add(
                    new PendingDeletedJenkinsBuildInfoModel(position,
                            mRecyclerViewAdapter.getItem(position)));
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Nullify and clear all selected item when action mode is stopped.
        mSwipeToDismissTouchListener.setEnabled(true);
        mRecyclerViewAdapter.clearSelections();
        mActionMode = null;
        isCabEnabled = false;
        if(selectedItemPositions !=null) {
            selectedItemPositions.clear();
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View clickedView, int position) {
        final String url = mRecyclerViewAdapter.getItem(position).getUrl();

        if (mActionMode != null) {
            toggleSelection(position);
        } else {
            if (url != null || !url.isEmpty()) {
                // Launch the default browser to open up Jenkins web console.
                final String fullUrl = String.format("%s/console", url);
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(fullUrl)));
            }
        }
    }


    @Override
    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
        // Start the action mode view and mark item as selected.
        getActivity().startActionMode(this);
        isCabEnabled = true;
        toggleSelection(position);
    }

    /**
     * Clear all selected items marked for deletion.
     */
    private void clearItems() {
        mRecyclerViewAdapter.clearSelections();
        mPendingList.clear();
    }

    /**
     * For initializing {@link JenkinsBuildInfoModel} for deletions. Holds the position of the model
     * in the adapter and the model itself.
     */
    private static class PendingDeletedJenkinsBuildInfoModel implements Comparable<PendingDeletedJenkinsBuildInfoModel> {

        public int position;

        public JenkinsBuildInfoModel jenkinsBuildInfoModel;

        public PendingDeletedJenkinsBuildInfoModel(int position, JenkinsBuildInfoModel jenkinsBuildInfoModel) {
            this.position = position;
            this.jenkinsBuildInfoModel = jenkinsBuildInfoModel;
        }

        @Override
        public int compareTo(PendingDeletedJenkinsBuildInfoModel other) {
            // Sort by descending order of position.
            return other.position - position;
        }
    }
}
