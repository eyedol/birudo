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

package org.addhen.birudo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.addhen.birudo.R;
import org.addhen.birudo.model.JenkinsBuildInfoModel;
import org.addhen.birudo.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class JenkinsBuildInfoAdapter extends BaseRecyclerViewAdapter<JenkinsBuildInfoModel> {

    private SparseBooleanArray mSelectedItems;
    RecyclerviewViewHolder mRecyclerviewViewHolder = new RecyclerviewViewHolder() {

        Context context;

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            if (getItem(position).getResult() == JenkinsBuildInfoModel.Result.SUCCESS) {
                ((Widgets) viewHolder).mStatus.setBackgroundColor(
                        context.getResources().getColor(R.color.success));
            } else {
                ((Widgets) viewHolder).mStatus.setBackgroundColor(
                        context.getResources().getColor(R.color.failed));
            }

            String name = getItem(position).getDisplayName();
            ((Widgets) viewHolder).mDisplayName.setText(name);

            ((Widgets) viewHolder).mBuildStatus.setText(
                    context.getString(R.string.build_status, getItem(position).getResult().name(),
                            AppUtil.formattedDuration(getItem(position).getDuration())));

            ((Widgets) viewHolder).mStartedBy.setText(context.getString(R.string.started_by,
                    getItem(position).getUserName()));

            ((Widgets) viewHolder).mTimestamp.setText(
                    AppUtil.formatDateTime(getItem(position).getTimestamp()));
            ((Widgets) viewHolder).row.setActivated(isChecked(position));
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            context = viewGroup.getContext();
            return new Widgets(LayoutInflater.from(
                    viewGroup.getContext())
                    .inflate(R.layout.list_build_info_item, viewGroup, false));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    };

    public JenkinsBuildInfoAdapter() {
        this.setRecyclerviewViewHolder(mRecyclerviewViewHolder);
        setHasStableIds(true);
        mSelectedItems = new SparseBooleanArray();
    }

    public void toggleSelection(int position) {
        if (isChecked(position)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }

    public boolean isChecked(int position) {
        return mSelectedItems.get(position, false);
    }

    public class Widgets extends RecyclerView.ViewHolder {

        TextView mStatus;

        TextView mDisplayName;

        TextView mBuildStatus;

        TextView mStartedBy;

        TextView mTimestamp;
        View row;

        public Widgets(View convertView) {
            super(convertView);
            row = convertView;
            mStatus = (TextView) convertView.findViewById(R.id.status);
            mDisplayName = (TextView) convertView.findViewById(R.id.display_name_label);
            mBuildStatus = (TextView) convertView.findViewById(R.id.message);
            mStartedBy = (TextView) convertView.findViewById(R.id.started_by);
            mTimestamp = (TextView) convertView.findViewById(R.id.timestamp);
        }
    }
}
