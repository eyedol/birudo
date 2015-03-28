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

package org.addhen.birudo;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import org.addhen.birudo.module.BirudoModule;

import java.util.List;

import dagger.ObjectGraph;
import timber.log.Timber;

public class BirudoApplication extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        // It's important to initialize the ResourceZoneInfoProvider; otherwise
        // joda-time-android will not work.
        JodaTimeAndroid.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
        initializeDependencyInjector();
    }

    /**
     * Extend the dependency container graph with new dependencies provided by the modules passed as
     * arguments.
     *
     * @param modules used to populate the dependency container.
     */
    public ObjectGraph add(List<Object> modules) {
        if (modules == null) {
            throw new IllegalArgumentException(
                    "You can't add a null module, review your getModules() implementation");
        }
        return mObjectGraph.plus(modules.toArray());
    }

    private void initializeDependencyInjector() {
        mObjectGraph = ObjectGraph.create(new BirudoModule(this));
        mObjectGraph.inject(this);
        mObjectGraph.injectStatics();
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.HollowTree {

        @Override
        public void i(String message, Object... args) {
            // TODO Setup Crashlytics.log(String.format(message, args));
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);

            // TODO Setup Crashlytics.logException(t);
        }

    }
}
