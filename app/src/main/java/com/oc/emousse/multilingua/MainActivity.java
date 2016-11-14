package com.oc.emousse.multilingua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oc.emousse.multilingua.pref.UserShared;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserShared.getInstance(getApplicationContext()).checkLogin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
