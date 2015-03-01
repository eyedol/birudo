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

package org.addhen.birudo.data.database.converter;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Locale;

import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.FieldConverter;

public class EnumEntityFieldConverter<E extends Enum> implements FieldConverter<E> {

    private final Class<E> mEnumClass;

    public EnumEntityFieldConverter(Class<E> enumClass) {
        this.mEnumClass = enumClass;
    }

    @Override
    public E fromCursorValue(Cursor cursor, int columnIndex) {
        return (E) Enum.valueOf(mEnumClass, cursor.getString(columnIndex).toUpperCase(
                Locale.getDefault()));
    }

    @Override
    public void toContentValue(E value, String key, ContentValues values) {
        values.put(key, value.toString());
    }

    @Override
    public EntityConverter.ColumnType getColumnType() {
        return EntityConverter.ColumnType.TEXT;
    }
}
