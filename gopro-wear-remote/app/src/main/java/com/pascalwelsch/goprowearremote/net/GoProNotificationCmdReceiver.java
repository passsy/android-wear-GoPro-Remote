package com.pascalwelsch.goprowearremote.net;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pascalwelsch.goprowearremote.ui.notifications.GoProNotificaionManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Pascal Welsch
 * @since 25.06.14.
 */
public class GoProNotificationCmdReceiver extends BroadcastReceiver {


    public static final String EXTRA_LOG_MESSAGE = "log_message";

    public static final String EXTRA_NOTIFICATION_ID = "notification_id";

    public static final int EXTRA_TYPE_MODE = 20;

    public static final int EXTRA_TYPE_ACTION = 10;

    public static final int EXTRA_TYPE_LOG = 30;

    public static final int EXTRA_TYPE_DISMISS = 40;

    public static final String TYPE = "type";

    public static final String EXTRA_MODE = "mode";

    public static final String EXTRA_ACTION = "action";

    public static final int DEFAULT_NOTIFICAION = 50;

    private static final String TAG = GoProNotificationCmdReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final GoProNotificaionManager notificaionManager = GoProNotificaionManager
                .from(context);

        final int type = intent.getExtras().getInt(TYPE);
        switch (type) {
            case EXTRA_TYPE_LOG:

                final String logMessage = intent.getExtras().getString(EXTRA_LOG_MESSAGE);
                Log.v(TAG, logMessage);
                break;
            case EXTRA_TYPE_MODE:
                final int notification = intent.getExtras().getInt(EXTRA_NOTIFICATION_ID);
                //NotificationManagerCompat.from(context).cancel(notification);
                final int mode = intent.getExtras().getInt(EXTRA_MODE);
                switch (mode) {
                    case GoProAction.SWITCH_TO_PHOTO:
                        GoProAction.fireGoProCommand("CM", "01", false, requestQueue);
                        notificaionManager.showPhotoNotificaion();
                        break;
                    case GoProAction.SWITCH_TO_VIDEO:
                        GoProAction.fireGoProCommand("CM", "00", false, requestQueue);
                        notificaionManager.showVideoNotificaion();
                        break;
                    case DEFAULT_NOTIFICAION:
                        notificaionManager.showStartNotification();
                        break;
                    default:
                        Toast.makeText(context, "not supported", Toast.LENGTH_SHORT).show();
                }
                break;
            case EXTRA_TYPE_ACTION:
                final int action = intent.getExtras().getInt(EXTRA_ACTION);
                switch (action) {
                    case GoProAction.TAKE_PHOTO:
                        GoProAction.fireGoProCommand("SH", "01", false, requestQueue);
                        break;
                    case GoProAction.START_VIDEO:
                        GoProAction.fireGoProCommand("SH", "01", false, requestQueue);
                        break;
                    case GoProAction.STOP_VIDEO:
                        GoProAction.fireGoProCommand("SH", "00", false, requestQueue);
                        break;
                    case GoProAction.POWER_ON:
                        GoProAction.fireGoProCommand("PW", "01", true, requestQueue);
                        break;
                    case GoProAction.POWER_OFF:
                        GoProAction.fireGoProCommand("PW", "00", true, requestQueue);
                        break;
                    default:
                        Toast.makeText(context, "not supported", Toast.LENGTH_SHORT).show();
                }
                break;
            case EXTRA_TYPE_DISMISS:
                //NotificationManagerCompat.from(context).cancelAll();
                //notificaionManager.showStartNotification();
                break;
        }
    }
}
