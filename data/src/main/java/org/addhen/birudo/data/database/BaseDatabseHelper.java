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

package org.addhen.birudo.data.database;

import org.addhen.birudo.data.database.converter.EnumEntityFieldConverter;
import org.addhen.birudo.data.entity.JenkinsBuildInfoEntity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.BuildConfig;

import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;
import timber.log.Timber;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public abstract class BaseDatabseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "birudogo-app-db.db";

    private static final int DATABASE_VERSION = 1;

    private static final int LAST_DATABASE_NUKE_VERSION = 0;

    private static final Class[] ENTITIES = new Class[]{JenkinsBuildInfoEntity.class};


    static {

        CupboardFactory.setCupboard(new CupboardBuilder()
                .registerFieldConverter(JenkinsBuildInfoEntity.Result.class,
                        new EnumEntityFieldConverter<>(JenkinsBuildInfoEntity.Result.class))
                .useAnnotations().build());

        // Register our entities
        for (Class<?> clazz : ENTITIES) {
            cupboard().register(clazz);
        }
    }

    private static String TAG = BaseDatabseHelper.class.getSimpleName();

    private boolean mIsClosed;

    public BaseDatabseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        // This will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < LAST_DATABASE_NUKE_VERSION) {
            if (BuildConfig.DEBUG) {
                Timber.d(TAG + " Nuking Database. Old Version: " + oldVersion);
            }
            cupboard().withDatabase(db).dropAllTables();
            onCreate(db);
        } else {
            // This will upgrade tables, adding columns and new tables.
            // Note that existing columns will not be converted
            cupboard().withDatabase(db).upgradeTables();
        }
    }

    /**
     * Close database connection
     */
    @Override
    public synchronized void close() {
        super.close();
        mIsClosed = true;
    }

    public boolean isClosed() {
        return mIsClosed;
    }

}

