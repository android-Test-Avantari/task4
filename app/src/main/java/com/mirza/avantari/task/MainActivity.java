package com.mirza.avantari.task;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Typeface type;
    TextView text1, text2, text3, text4, msg, rate;
    View frame1, frame2;
    FloatingActionButton fab;
    double r = 180.5;
    private Thread progressThread;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        msg = (TextView) findViewById(R.id.msg);
        rate = (TextView) findViewById(R.id.rate);

        frame1 = findViewById(R.id.frame1);
        frame2 = findViewById(R.id.frame2);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        rate.setText(r + " kWh");


        type = Typeface.createFromAsset(getAssets(), "round.ttf");

        text1.setTypeface(type);
        text2.setTypeface(type);
        text3.setTypeface(type);
        text4.setTypeface(type);
        msg.setTypeface(type);
        rate.setTypeface(type);

        handler = new Handler();
    }

    public void onChartView(View v) {
        r = 180.5;

        msg.setText("Last 12 hours average\nelectricity consumption");
        fab.setVisibility(View.GONE);

        frame1.setVisibility(View.GONE);
        frame2.setVisibility(View.VISIBLE);

        progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (r != 90.5) {
                    if (r == 90.5) {
                        return;
                    }
                    r = r - 1.5;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            rate.setText(r + " kWh");
                            if (r == 90.5)
                                return;
                        }
                    });

                }
            }
        });
        progressThread.start();


    }

    public void doBack(View v) {
        msg.setText("Total electricity consumption\nof Galaxy SOHO");
        r = 90.5;

        fab.setVisibility(View.VISIBLE);
        frame2.setVisibility(View.GONE);
        frame1.setVisibility(View.VISIBLE);

        progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (r != 180.5) {
                    if (r == 180.5) {
                        return;
                    }
                    r = r + 1.5;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            rate.setText(r + " kWh");
                            if (r == 180.5) {
                                return;
                            }
                        }
                    });

                }
            }
        });
        progressThread.start();
    }
}
