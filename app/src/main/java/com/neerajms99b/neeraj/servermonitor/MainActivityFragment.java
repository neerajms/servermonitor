package com.neerajms99b.neeraj.servermonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {
                executeCommand();
                return null;
            }
        }.execute(1);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void executeCommand() {
        JSch jSch = new JSch();
        try {
            Session session = jSch.getSession("neeraj", "192.168.0.105", 22);
            session.setPassword("nrj@1234");

            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            session.connect();


            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            channelExec.setOutputStream(baos);
            channelExec.setCommand("ls");
            channelExec.connect();
            InputStream inputStream = channelExec.getInputStream();
            int i = inputStream.read();
            String outputBuffer = null;
            while (i != -1) {
                outputBuffer = outputBuffer + ((char) i);
                i = inputStream.read();
            }
            Log.d(TAG, outputBuffer);
            channelExec.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
