package com.lendlord.manga;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by lendlord on 02.10.15.
 */
public class Connection extends Activity{
    private Socket socket;
    Thread thread = null;

    private BufferedReader in = null;
    private PrintWriter out = null;
    private EditText ConnMsg;
    Context context;

    public static final int BUFFER_SIZE = 2048;
    private static final int SERVERPORT = 8081;
    private static final String SERVER_IP = "81.88.221.123";

    Connection(final Context context) {
        this.context = context;
        this.thread = new Thread(new ClientThread());
        this.thread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                CommunicationThread commThread = new CommunicationThread(socket);
                new Thread(commThread).start();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class CommunicationThread implements Runnable {
        private Socket clientSocket;
        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String read = input.readLine();
                    ConnMsg = (EditText) ((MainActivity)context).findViewById(R.id.editText);
//                    ConnMsg.setText("Client Says: "+ read + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void send(String data){
        if (socket == null || !socket.isConnected()) return;
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
