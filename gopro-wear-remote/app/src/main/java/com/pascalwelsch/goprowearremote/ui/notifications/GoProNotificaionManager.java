package com.pascalwelsch.goprowearremote.ui.notifications;

import com.pascalwelsch.goprowearremote.R;
import com.pascalwelsch.goprowearremote.net.GoProAction;
import com.pascalwelsch.goprowearremote.net.GoProNotificationCmdReceiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;

/**
 * @author Pascal Welsch
 * @since 25.06.14.
 */
public class GoProNotificaionManager {

    private static GoProNotificaionManager sInstance;

    private static String GROUP_ID = "goProGroup";

    private final Context mContext;

    private final NotificationManagerCompat mNotificationManager;

    private GoProNotificaionManager(final Context context) {
        mContext = context.getApplicationContext();
        mNotificationManager = NotificationManagerCompat.from(context);
    }

    public static GoProNotificaionManager from(final Context context) {
        if (sInstance == null) {
            sInstance = new GoProNotificaionManager(context);
        }
        return sInstance;
    }

    public void showPhotoNotificaion() {

        mNotificationManager.cancel(2);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Photo mode")
                        .setContentText("Take a picture")
                        .addAction(R.drawable.ic_launcher, "take photo",
                                getActionPendingIntent(GoProAction.TAKE_PHOTO))
                        .addAction(android.R.drawable.ic_menu_revert, "back",
                                getShowDefaultNotificationPendingIntent())
                        .setDeleteIntent(getActionDismissedPendingIntent());

        Notification n1 = new WearableNotifications.Builder(notificationBuilder)
                .setGroup(GROUP_ID, 1)
                .build();
        mNotificationManager.notify(1, n1);
    }

    public void showStartNotification() {
        NotificationCompat.Builder summaryBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setAutoCancel(true)
                        .setContentTitle("GoPro Remote is running");
        Notification summary = new WearableNotifications.Builder(summaryBuilder)
                .setGroup(GROUP_ID, WearableNotifications.GROUP_ORDER_SUMMARY)
                .build();
        mNotificationManager.notify(0, summary);

        NotificationCompat.Builder modeNotificationBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("GoPro Remote")
                        .setContentText("Take photos or videos with your wrist. Choose a mode")
                        .addAction(android.R.drawable.ic_menu_camera, "switch to photo",
                                getSwitchModePendingIntent(1, GoProAction.SWITCH_TO_PHOTO))
                        .addAction(android.R.drawable.ic_menu_slideshow, "switch to video",
                                getSwitchModePendingIntent(1, GoProAction.SWITCH_TO_VIDEO));

        Notification mode = new WearableNotifications.Builder(modeNotificationBuilder)
                .setGroup(GROUP_ID, 1)
                .build();

        mNotificationManager.notify(1, mode);

        NotificationCompat.Builder moreNotificationBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("More...")
                        .setContentText("Additional Actions")
                        .addAction(R.drawable.ic_launcher, "On",
                                getActionPendingIntent(GoProAction.POWER_ON))
                        .addAction(R.drawable.ic_launcher, "Off",
                                getActionPendingIntent(GoProAction.POWER_OFF))
                        .setDeleteIntent(getActionDismissedPendingIntent());

        Notification n1 = new WearableNotifications.Builder(moreNotificationBuilder)
                .setGroup(GROUP_ID, 2)
                .build();
        mNotificationManager.notify(2, n1);
    }

    public void showVideoNotificaion() {

        mNotificationManager.cancel(2);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Video mode")
                        .setContentText("Take a video")
                        .addAction(R.drawable.ic_launcher, "start video",
                                getActionPendingIntent(GoProAction.START_VIDEO))
                        .addAction(R.drawable.ic_launcher, "stop video",
                                getActionPendingIntent(GoProAction.STOP_VIDEO))
                        .addAction(android.R.drawable.ic_menu_revert, "back",
                                getShowDefaultNotificationPendingIntent())
                        .setDeleteIntent(getActionDismissedPendingIntent());

        Notification n1 = new WearableNotifications.Builder(notificationBuilder)
                .setGroup(GROUP_ID, 1)
                .build();
        mNotificationManager.notify(1, n1);
    }

    private PendingIntent getActionDismissedPendingIntent() {
        final Intent intent = new Intent(getContext(), GoProNotificationCmdReceiver.class);
        intent.putExtra(GoProNotificationCmdReceiver.TYPE,
                GoProNotificationCmdReceiver.EXTRA_TYPE_DISMISS);
        return PendingIntent.getBroadcast(getContext(), (int) (Math.random() * 99999), intent, 0);
    }

    private PendingIntent getActionPendingIntent(final int action) {
        final Intent intent = new Intent(getContext(), GoProNotificationCmdReceiver.class);
        intent.putExtra(GoProNotificationCmdReceiver.TYPE,
                GoProNotificationCmdReceiver.EXTRA_TYPE_ACTION);
        intent.putExtra(GoProNotificationCmdReceiver.EXTRA_ACTION, action);
        return PendingIntent.getBroadcast(getContext(), (int) (Math.random() * 99999), intent, 0);
    }

    private Context getContext() {
        return mContext;
    }

    private PendingIntent getLogPendingIntent(final String logMessage) {
        final Intent intent = new Intent(getContext(), GoProNotificationCmdReceiver.class);
        intent.putExtra(GoProNotificationCmdReceiver.EXTRA_LOG_MESSAGE, logMessage);
        return PendingIntent.getBroadcast(getContext(), (int) (Math.random() * 99999), intent, 0);
    }

    private PendingIntent getShowDefaultNotificationPendingIntent() {
        final Intent intent = new Intent(getContext(), GoProNotificationCmdReceiver.class);
        intent.putExtra(GoProNotificationCmdReceiver.TYPE,
                GoProNotificationCmdReceiver.EXTRA_TYPE_MODE);
        intent.putExtra(GoProNotificationCmdReceiver.EXTRA_MODE,
                GoProNotificationCmdReceiver.DEFAULT_NOTIFICAION);
        return PendingIntent.getBroadcast(getContext(), (int) (Math.random() * 99999), intent, 0);
    }

    private PendingIntent getSwitchModePendingIntent(final int notificationId,
            final int switchToPhoto) {
        final Intent intent = new Intent(getContext(), GoProNotificationCmdReceiver.class);
        intent.putExtra(GoProNotificationCmdReceiver.TYPE,
                GoProNotificationCmdReceiver.EXTRA_TYPE_MODE);
        intent.putExtra(GoProNotificationCmdReceiver.EXTRA_NOTIFICATION_ID, notificationId);
        intent.putExtra(GoProNotificationCmdReceiver.EXTRA_MODE, switchToPhoto);
        return PendingIntent.getBroadcast(getContext(), (int) (Math.random() * 99999), intent, 0);
    }
}
