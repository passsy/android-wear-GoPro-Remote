package com.pascalwelsch.goprowearremote.ui.home;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pascalwelsch.goprowearremote.R;
import com.pascalwelsch.goprowearremote.net.GoProAction;
import com.pascalwelsch.goprowearremote.ui.notifications.GoProNotificaionManager;
import com.pascalwelsch.goprowearremote.utils.ViewHelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private GoProNotificaionManager mNoticiationManager;

    private RequestQueue mRequestQueue;

    public HomeFragment() {
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.show_notificaion_btn:
                mNoticiationManager.showStartNotification();
                break;
            case R.id.take_photo_btn:
                fireGoProCommand("SH", "01");
                break;
            case R.id.stop_video_btn:
                fireGoProCommand("SH", "00");
                break;
            case R.id.mode_photo:
                fireGoProCommand("CM", "01");
                break;
            case R.id.mode_video:
                fireGoProCommand("CM", "00");
                break;
            default:
                Toast.makeText(getActivity(), "not implemented", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNoticiationManager = GoProNotificaionManager.from(getActivity());
        mRequestQueue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ViewHelper.setonClickListenerForViews(
                rootView, this,
                R.id.show_notificaion_btn,
                R.id.take_photo_btn,
                R.id.stop_video_btn,
                R.id.mode_photo,
                R.id.mode_video);
        return rootView;
    }

    private void fireGoProCommand(final String sh, final String s) {
        GoProAction.fireGoProCommand(sh, s, false, mRequestQueue);
    }
}
