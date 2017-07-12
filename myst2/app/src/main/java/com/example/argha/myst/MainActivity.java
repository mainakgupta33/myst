package com.example.argha.myst;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.net.ConnectivityManager;
import android.os.Message;
import android.os.StrictMode;
import android.net.TrafficStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import static android.net.TrafficStats.getMobileRxBytes;

public class MainActivity extends AppCompatActivity {
        public long rxbytes;
        public String saveData;
    Context context=this;
    Button save,show;
        TextView showData;
        Handler hd1 = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

               /* boolean mobileDataEnabled = false; // Assume disabled
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                try {
                    Class cmClass = Class.forName(cm.getClass().getName());
                    Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
                    method.setAccessible(true); // Make the method callable
                    // get the setting for "mobile data"
                    mobileDataEnabled = (Boolean)method.invoke(cm);
                } catch (Exception e) {
                    // Some problem accessible private API
                    // TODO do whatever error handling you want here
                }
                if(mobileDataEnabled==true)
                {*/
                    rxbytes = TrafficStats.getMobileRxBytes();

                    TextView rxdata = (TextView) findViewById(R.id.rxdata);
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
                    rxdata.setText(s);

                    String FILENAME = "mystdata.txt";
                    saveData = s;
                //}





            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MainActivity.this,mystService.class);

        startService(i);

        save = (Button) findViewById(R.id.save);
        show = (Button) findViewById(R.id.show);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData = (TextView) findViewById(R.id.showdata);
                try
                {
                    String FILENAME = "mystdata.txt";


                    FileInputStream fis = openFileInput(FILENAME);
                    int read=-1;
                    StringBuffer buffer = new StringBuffer();
                    while((read=fis.read())!=-1)
                    {
                        buffer.append((char)read);
                    }

                    showData.setText(buffer.toString());

                    fis.close();
                }
                catch(Exception e)
                {

                }
            }
        });

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
