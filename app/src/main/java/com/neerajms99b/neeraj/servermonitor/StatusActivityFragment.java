package com.neerajms99b.neeraj.servermonitor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        TextView loadAverageTextView = (TextView) rootView.findViewById(R.id.load_average_textview);
        TextView usersTextView = (TextView) rootView.findViewById(R.id.users_textview);

        Bundle bundle = getArguments();
        String uptime = bundle.getString(getString(R.string.key_uptime));
        String uptimeProcessed = processUptime(uptime);
        StringTokenizer tokenizer = splitOnPercentage(uptimeProcessed);
        uptimeTextView.setText(tokenizer.nextToken());
        usersTextView.setText(tokenizer.nextToken());
        loadAverageTextView.setText(tokenizer.nextToken());

        return rootView;
    }

    public String processUptime(String uptimeFullStr) {
        StringTokenizer tokenizer = splitOnComma(uptimeFullStr);
        String uptime = null;
        String users = null;
        String loadAverage = null;
        if (tokenizer.countTokens() == 6) {
            uptime = tokenizer.nextToken() + tokenizer.nextToken();
        } else if (tokenizer.countTokens() == 5) {
            uptime = tokenizer.nextToken();
        }

        users = tokenizer.nextToken();
        users = users.substring(2, users.length());
        String loadAverage1 = tokenizer.nextToken();
        String loadAverage2 = tokenizer.nextToken();
        String loadAverage3 = tokenizer.nextToken();
        loadAverage = loadAverage1.substring(16, loadAverage1.length()) +
                loadAverage2 + loadAverage3.substring(0, loadAverage3.length() - 1);
        return uptime + "%" + users + "%" + loadAverage;

//        uptime = tokenizer.nextToken();
//        String uptimeExtended = tokenizer.nextToken();
//        StringTokenizer tokenizer3 = new StringTokenizer(uptimeExtended, ":");
//        if (tokenizer3.countTokens() == 2) {
//            uptime = uptime + uptimeExtended;
//            tokenizer.nextToken();
//        }
//        String loadAverage1 = tokenizer.nextToken();
//        loadAverage1 = loadAverage1.substring(15, loadAverage1.length());
//        String loadAverage2 = tokenizer.nextToken();
//        String loadAverage3 = tokenizer.nextToken();
//        StringTokenizer tokenizer1 = new StringTokenizer(uptime, " ");
//        tokenizer1.nextToken();
//        tokenizer1.nextToken();
//        uptime = tokenizer1.nextToken();
//        if (tokenizer1.hasMoreTokens()) {
//            uptime = uptime + " " + tokenizer1.nextToken();
//        }
//        StringTokenizer tokenizer2 = new StringTokenizer(uptime, ":");
//        if (tokenizer2.countTokens() == 2) {
//            uptime = tokenizer2.nextToken() + " hours " + tokenizer2.nextToken() + " minutes";
//        }
//        Log.d(TAG, uptime);
//
////        loadAverage1 = loadAverage1.substring(17, loadAverage1.length());
////        StringTokenizer tokenizer3 = new StringTokenizer(loadAverage1, ",");
//        loadAverage3 = loadAverage3.substring(0, loadAverage3.length() - 1);
//        String averages = loadAverage1 + loadAverage2 + loadAverage3;

    }

    private StringTokenizer splitOnComma(String splitStr) {
        return new StringTokenizer(splitStr, ",");
    }

    private StringTokenizer splitOnSpace(String splitStr) {
        return new StringTokenizer(splitStr, " ");
    }

    private StringTokenizer splitOnColon(String splitStr) {
        return new StringTokenizer(splitStr, ":");
    }

    private StringTokenizer splitOnPercentage(String splitStr) {
        return new StringTokenizer(splitStr, "%");
    }

}
