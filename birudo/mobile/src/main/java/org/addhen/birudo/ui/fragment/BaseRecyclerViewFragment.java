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

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;

import org.addhen.birudo.model.Model;
import org.addhen.birudo.ui.adapter.BaseRecyclerViewAdapter;

import butterknife.InjectView;
import timber.log.Timber;

/**
 * Base {@link android.app.ListFragment} that every fragment list will extend from.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public abstract class BaseRecyclerViewFragment<M extends Model, L extends BaseRecyclerViewAdapter>
        extends BaseFragment {

    private static String TAG = BaseRecyclerViewFragment.class.getSimpleName();

    /**
     * RecyclerView resource mId
     */
    private final int mRecyclerViewId;

    /**
     * RecyclerViewAdapter class
     */
    private final Class<L> mRecyclerViewAdapterClass;

    /**
     * RecyclerViewAdapter
     */
    protected L mRecyclerViewAdapter;

    /**
     * RecyclerView
     */
    @InjectView(android.R.id.list)
    protected RecyclerView mRecyclerView;

    protected BaseRecyclerViewFragment(Class<L> adapterClass, int layout, int menu,
                                       int recyclerViewId) {
        super(layout, menu);
        mRecyclerViewAdapterClass = adapterClass;
        mRecyclerViewId = recyclerViewId;
    }

    /**
     * Uses reflection to create a new instance of a class
     *
     * @param targetClass The class to create an instance
     * @return The created instance
     */
    private static <T> T createInstance(Class<?> targetClass) {
        try {
            return (T) targetClass.newInstance();
        } catch (IllegalAccessException e) {
            Timber.e(TAG, "IllegalAccessException", e);
        } catch (IllegalStateException e) {
            Timber.e(TAG, "IllegalStateException", e);
        } catch (SecurityException e) {
            Timber.e(TAG, "SecurityException", e);
            for (StackTraceElement exception : e.getStackTrace()) {
                Timber.e(TAG,
                        String.format("%s", exception.toString()));
            }
        } catch (InflateException e) {
            Timber.e(TAG, "InflateException", e);
        } catch (java.lang.InstantiationException e) {
            Timber.e(TAG, "InstantiationException", e);
        }
        return null;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initPresenter();
        if (mRecyclerViewId != 0) {
            mRecyclerViewAdapter = BaseRecyclerViewFragment
                    .createInstance(mRecyclerViewAdapterClass);
            mRecyclerView.setFocusable(true);
            mRecyclerView.setFocusableInTouchMode(true);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

    }
}
