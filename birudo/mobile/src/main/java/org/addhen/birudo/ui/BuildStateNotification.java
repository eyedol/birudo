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

package org.addhen.birudo.ui;

import org.addhen.birudo.R;
import org.addhen.birudo.data.pref.BooleanPreference;
import org.addhen.birudo.data.qualifier.Sound;
import org.addhen.birudo.data.qualifier.Vibrate;
import org.addhen.birudo.model.JenkinsBuildInfoJsonModel;
import org.addhen.birudo.ui.activity.MainActivity;
import org.addhen.birudo.util.AppUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;

import javax.inject.Inject;

public class BuildStateNotification {

    @Inject
    public void setNotification(Context context, @Vibrate BooleanPreference vibrate, @Sound BooleanPreference sound, String message,
            JenkinsBuildInfoJsonModel.Result result, long duration) {

        Intent pendingIntent = new Intent(context, MainActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, pendingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_view,
                        context.getString(R.string.open), viewPendingIntent)
                        .build();
        Bitmap bitmap;
        if (result == JenkinsBuildInfoJsonModel.Result.SUCCESS) {
            Drawable drawable = context.getResources()
                    .getDrawable(R.drawable.success_notification_bg);
            bitmap = AppUtil.drawableToBitmap(drawable);
        } else {
            Drawable drawable = context.getResources().getDrawable(R.drawable.fail_notification_bg);
            bitmap = AppUtil.drawableToBitmap(drawable);
        }

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender();
        wearableExtender.setBackground(bitmap);

        final String timeDuration = String.format("%s ", AppUtil.formattedDuration(duration));

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(context.getString(R.string.build_status, result.name(), timeDuration));

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_stat_ic_stat_notify_post)
                        .setContentTitle(context.getString(R.string.new_message_from_jenkins))
                        .setContentText(message)
                        .setContentIntent(viewPendingIntent)
                        .extend(wearableExtender.addAction(action))
                        .setStyle(bigStyle);
        Notification notify = notificationBuilder.build();

        if (vibrate.get()) {
            notify.defaults |= Notification.DEFAULT_VIBRATE;
        }

        if(sound.get()) {
            notify.defaults |= Notification.DEFAULT_SOUND;
        }

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(0, notify);
    }
}
