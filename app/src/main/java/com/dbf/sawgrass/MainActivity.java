package com.dbf.sawgrass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MainActivity extends AppCompatActivity
{
    private static final String IP = "192.168.2.70";
    private static final int PORT = 2001;
    private static final int TIMEOUT_MS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void runCommand(String zone, boolean on)
    {
        AsyncTask task = new AsyncCommand(zone, on);
        task.execute();
    }

    public void masterOnClicked(View v) { runCommand("0",
            cjyb android

        true); }
    public void masterOffClicked(View v) { runCommand("0",false); }
    public void zoneAOnClicked(View v) { runCommand("B",true); }
    public void zoneAOffClicked(View v) { runCommand("B",false); }
    public void zoneBOnClicked(View v) { runCommand("C",true); }
    public void zoneBOffClicked(View v) { runCommand("C",false); }
    public void zoneCOnClicked(View v) { runCommand("D",true); }
    public void zoneCOffClicked(View v) { runCommand("D",false); }
    public void zoneDOnClicked(View v) { runCommand("E",true); }
    public void zoneDOffClicked(View v) { runCommand("E",false); }
    public void zoneEOnClicked(View v) { runCommand("F",true); }
    public void zoneEOffClicked(View v) { runCommand("F",false); }
    public void zoneFOnClicked(View v) { runCommand("10",true); }
    public void zoneFOffClicked(View v) { runCommand("10",false); }

    public class AsyncCommand extends AsyncTask
    {
        private String zone;
        private boolean on;

        public AsyncCommand (String zone, boolean on)
        {
            this.zone = zone;
            this.on = on;
        }
        @Override
        protected Object doInBackground(Object[] objects)
        {
            //retry upto 3 times in case of an io exception
            int i = 1;
            while (!runCommand() &&  i <4)
            {
                Log.d("Sawgrass Boombox", "Retrying");
                try
                {
                    Thread.sleep(200);
                }
                catch (InterruptedException e)
                {
                    //Oh well
                }
                i++;
            }
            return null;
        }

        public boolean runCommand()
        {
            StringBuilder outputLineBuilder = new StringBuilder();

            String command = "(00,X,2," + zone + "=" + (on ? "1" :"0") + ";)\r\n";
            try (Socket socket = new Socket(IP, PORT))
            {
                socket.setSoTimeout(TIMEOUT_MS);
                try (PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));)
                {
                    Log.d("Sawgrass Boombox", "Response: " + readResponse(outputLineBuilder, reader));
                    Log.d("Sawgrass Boombox", "Sending command: " + command);
                    writer.print(command);
                    writer.flush();
                    Log.d("Sawgrass Boombox", "Response: " + readResponse(outputLineBuilder, reader));
                }
            }
            catch (IOException e)
            {
                Log.e("Sawgrass Boombox", "Failed to send command: "+ command,e);
                return false;
            }
            return true;
        }

        public String readResponse(StringBuilder outputLineBuilder, BufferedReader reader) throws IOException
        {
            outputLineBuilder.setLength(0);
            try
            {
                int i = reader.read();
                while (i > -1)
                {
                    outputLineBuilder.append((char) i);
                    i = reader.read();
                }
            }
            catch (SocketTimeoutException e)
            {
                //ignore
            }
            return outputLineBuilder.toString();
        }
    }
}