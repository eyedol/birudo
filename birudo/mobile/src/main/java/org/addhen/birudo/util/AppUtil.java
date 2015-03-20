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

package org.addhen.birudo.util;

import org.joda.time.Period;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


import java.util.Collection;
import java.util.Date;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class AppUtil {

    private AppUtil() {

    }

    public static boolean isCollectionEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static String formattedDuration(long duration) {

        PeriodFormatter hoursMinutes = new PeriodFormatterBuilder()
                .appendHours()
                .appendSuffix(" hr", " hrs")
                .appendSeparator(" ")
                .appendMinutes()
                .appendSuffix(" min", " mins")
                .appendSeparator(" ")
                .appendSeconds()
                .appendSuffix(" sec", " secs")
                .toFormatter();
        Period p = new Period(duration);

        return hoursMinutes.print(p);
    }

    public static String formatDateTime(long timestamp) {
        Date date = new Date(timestamp);
        return android.text.format.DateFormat.format("MMM dd, yyyy 'at' h:mm a",date).toString();
    }
}
