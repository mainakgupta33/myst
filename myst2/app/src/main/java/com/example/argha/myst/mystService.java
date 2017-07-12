package com.example.argha.myst;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class mystService extends IntentService {
    public long rxbytes;
    String saveData;

    public mystService() {
        super("Debojyoti");
    }

    Handler hd1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            rxbytes = TrafficStats.getMobileRxBytes();

            String s;
            if(rxbytes==TrafficStats.UNSUPPORTED)
            {
                s = "UnSupported!";
            }
            else
            {
                s = String.valueOf(rxbytes);
            }
            s = String.valueOf(rxbytes);

            saveData = s;

            if(s=="0")
            {
                try
                {
                    String FILENAME = "mystdata.txt";


                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                    byte[] buf = saveData.getBytes();

                    fos.write(buf);

                    fos.close();
                }
                catch(Exception e)
                {

                }

            }




        }
    };

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try
                    {
                        Thread.sleep(1000);
                        hd1.sendEmptyMessage(0);
                    }
                    catch(Exception e)
                    {}

                }

            }
        };

        Thread th = new Thread(r);
        th.start();
    }
}
