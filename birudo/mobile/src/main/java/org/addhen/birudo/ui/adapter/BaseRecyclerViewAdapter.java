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

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.addhen.birudo.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Base RecyclerView Adapter class for all RecyclerView adapters for a specific Model class
 *
 * @param <M> Model class
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class BaseRecyclerViewAdapter<M extends Model>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<M> mItems;

    protected RecyclerviewViewHolder mRecyclerviewViewHolder;

    public BaseRecyclerViewAdapter() {
        mItems = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        if (mRecyclerviewViewHolder == null) {
            throw new IllegalArgumentException("You must call setRecyclerviewViewHolder");
        }
        return mRecyclerviewViewHolder.onCreateViewHolder(viewGroup, position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (mRecyclerviewViewHolder == null) {
            throw new IllegalArgumentException("You must call setRecyclerviewViewHolder");
        }
        mRecyclerviewViewHolder.onBindViewHolder(viewHolder, position);
    }

    public List<M> getItems() {
        return mItems;
    }

    public void setItems(List<M> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public M getItem(int position) {
        return mItems.get(position);
    }

    public void addItem(M item, int position) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(M item) {
        int position = mItems.indexOf(item);
        if (position < 0) {
            return;
        }
        mItems.remove(item);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {

        if (mRecyclerviewViewHolder == null) {
            throw new IllegalArgumentException("You must call setRecyclerviewViewHolder");
        }

        return mRecyclerviewViewHolder.getItemCount();
    }

    public void setRecyclerviewViewHolder(RecyclerviewViewHolder recyclerviewViewHolder) {
        mRecyclerviewViewHolder = recyclerviewViewHolder;
    }

    public interface RecyclerviewViewHolder {

        void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);

        RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position);

        int getItemCount();
    }
}
