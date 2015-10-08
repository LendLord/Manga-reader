package com.lendlord.manga;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by lendlord on 03.10.15.
 */
public class SocketSend extends AsyncTask<Void, String, Void>{

    private Socket socket;
    private static final int SERVERPORT = 8081;
    private static final String SERVER_IP = "81.88.221.123";

    PrintWriter output;
    BufferedReader input;

    private MainActivity parentActivity = null;

    public SocketSend(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        InetAddress serverAddr = null;
        try {
            serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVERPORT);
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            while (!Thread.currentThread().isInterrupted()){
                String read = input.readLine();
                publishProgress(read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... msg) {
        super.onProgressUpdate();
        EditText text = (EditText) parentActivity.findViewById(R.id.editText);
        text.setText("Server says: "+msg[0]);
    }

    @Override
    protected void onPreExecute() {

    }

    public boolean send(String data){
        if (socket == null || !socket.isConnected())  return false;
        output.println(data);
        return true;
    }

}
