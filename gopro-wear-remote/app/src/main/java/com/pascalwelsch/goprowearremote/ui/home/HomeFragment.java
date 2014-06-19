package com.pascalwelsch.goprowearremote.ui.home;

import com.pascalwelsch.goprowearremote.R;
import com.pascalwelsch.goprowearremote.utils.ViewHelper;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.Bundle;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mButton;

    private NotificationManagerCompat mNotificationManager;

    public HomeFragment() {
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.show_notificaion_btn:
                showSimpleWearNotification();
                break;
            default:
                Toast.makeText(getActivity(), "not implemented", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Get an instance of the NotificationManager service
        mNotificationManager =
                NotificationManagerCompat.from(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ViewHelper.setonClickListenerForViews(rootView, this, R.id.show_notificaion_btn);
        return rootView;
    }

    private void showSimpleWearNotification() {
        // Create a NotificationCompat.Builder for standard notification features
        // TODO
        final PendingIntent photoPendingIntent = null;
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setContentTitle("New mail from John Doe")
                        .setContentText(getString(R.string.hello_gopro_wear_remote))
                        .addAction(R.drawable.ic_launcher, "take photo", photoPendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher);

        // Create a WearablesNotification.Builder to add special functionality for wearables
        Notification notification =
                new WearableNotifications.Builder(notificationBuilder)
                        .setHintHideIcon(true)
                        .build();

        // Build the notification and issues it with notification manager.
        mNotificationManager.notify(1, notification);
    }
}
