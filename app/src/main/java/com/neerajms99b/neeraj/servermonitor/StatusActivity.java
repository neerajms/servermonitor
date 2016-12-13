package com.neerajms99b.neeraj.servermonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String uptime = intent.getStringExtra(getString(R.string.key_uptime));
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.key_uptime),uptime);
        StatusActivityFragment statusActivityFragment = new StatusActivityFragment();
        statusActivityFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.status_fragment,statusActivityFragment);
        transaction.commit();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
