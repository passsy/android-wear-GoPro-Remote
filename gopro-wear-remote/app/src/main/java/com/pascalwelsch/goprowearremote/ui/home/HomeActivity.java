package com.pascalwelsch.goprowearremote.ui.home;

import com.pascalwelsch.goprowearremote.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class HomeActivity extends ActionBarActivity {

    private static final String FRAGMENT_TAG_REMOTE = "fragment::remote";

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new HomeFragment(), FRAGMENT_TAG_REMOTE)
                    .commit();
        }
    }
}
