package com.neerajms99b.neeraj.servermonitor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.StringTokenizer;

/**
 * A placeholder fragment containing a simple view.
 */
public class StatusActivityFragment extends Fragment {

    private static final String TAG = StatusActivityFragment.class.getSimpleName();

    public StatusActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(R.layout.fragment_status, container, false);
        TextView uptimeTextView = (TextView) rootView.findViewById(R.id.uptime_textview);

        Bundle bundle = getArguments();
        String uptime = bundle.getString(getString(R.string.key_uptime));
        StringTokenizer tokenizer = new StringTokenizer(uptime, ",");
        uptime = tokenizer.nextToken();
        StringTokenizer tokenizer1 = new StringTokenizer(uptime, " ");
        tokenizer1.nextToken();
        tokenizer1.nextToken();
        uptime = tokenizer1.nextToken();
        if (tokenizer1.hasMoreTokens()) {
            uptime = uptime + " " + tokenizer1.nextToken();
        }
        StringTokenizer tokenizer2 = new StringTokenizer(uptime,":");
        if (tokenizer2.countTokens() == 2) {
            uptime = tokenizer2.nextToken() + " hours " + tokenizer2.nextToken() + " minutes";
        }
        Log.d(TAG, uptime);
        uptimeTextView.setText(uptime);
        return rootView;
    }
}
