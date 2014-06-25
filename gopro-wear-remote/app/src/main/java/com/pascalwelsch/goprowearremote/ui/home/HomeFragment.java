package com.pascalwelsch.goprowearremote.ui.home;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pascalwelsch.goprowearremote.R;
import com.pascalwelsch.goprowearremote.net.GoProAction;
import com.pascalwelsch.goprowearremote.ui.notifications.GoProNotificaionManager;
import com.pascalwelsch.goprowearremote.utils.ViewHelper;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private GoProNotificaionManager mNoticiationManager;

    private TextView mPasswordInput;

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
        mPasswordInput = (TextView) rootView.findViewById(R.id.password);
        String possiblePw = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("password", null);
        if (!TextUtils.isEmpty(possiblePw)) {
            mPasswordInput.setText(possiblePw);
        }
        mPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(final Editable s) {
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                        .putString("password", s.toString()).commit();
            }

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count,
                    final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before,
                    final int count) {
            }
        });
        return rootView;
    }

    private void fireGoProCommand(final String sh, final String s) {
        final String password = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("password", "goprohero");

        GoProAction.fireGoProCommand(sh, s, false, password, mRequestQueue);
    }
}
