package com.neerajms99b.neeraj.servermonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    public MainActivity callBack;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = (View) inflater.inflate(R.layout.fragment_main, container, false);

        callBack = (MainActivity) getActivity();
        final EditText userNameEditText = (EditText) rootView.findViewById(R.id.user_name);
        final EditText ipAddressEditText = (EditText) rootView.findViewById(R.id.ip_address);
        final EditText passwordEditText = (EditText) rootView.findViewById(R.id.password);
        Button connectButton = (Button) rootView.findViewById(R.id.connect_button);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = userNameEditText.getText().toString();
                final String ipAddress = ipAddressEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... integers) {
                        executeCommand(userName, ipAddress, password);
                        return null;
                    }
                }.execute(1);
            }
        });

        return rootView;
    }

    public void executeCommand(String userName, String ipAddress, String password) {
        JSch jSch = new JSch();
        try {
            Session session = jSch.getSession(userName, ipAddress, 22);
            session.setPassword(password);

            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            session.connect();


            ChannelExec channelExec = getExecutionChannel(session, "uptime");
            channelExec.connect();
//            channelExec.disconnect();

//            channelExec = getExecutionChannel(session, "");

            InputStream inputStream = channelExec.getInputStream();
            String outputBuffer = receiveResponse(inputStream);
            Log.d(TAG, outputBuffer);
            callBack.showStatus(outputBuffer);
//            channelExec.disconnect();
            session.disconnect();
        } catch (JSchException e) {
//            Toast.makeText(callBack,
//                    "Could not connect or Permission denied", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveResponse(InputStream inputStream) {
        String outputBuffer = "";
        try {
            int i = inputStream.read();
            while (i != -1) {
                outputBuffer = outputBuffer + ((char) i);
                i = inputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputBuffer;
    }

    public ChannelExec getExecutionChannel(Session session, String command) {
        ChannelExec channelExec = null;
        try {
            channelExec = (ChannelExec) session.openChannel("exec");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            channelExec.setOutputStream(baos);
            channelExec.setCommand(command);
        } catch (JSchException e) {
//            Toast.makeText(callBack,
//                    "Could not connect or Permission denied", Toast.LENGTH_SHORT).show();
        }
        return channelExec;
    }
}
