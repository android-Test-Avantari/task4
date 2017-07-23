package com.mirza.avantari.task;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    Typeface type;
    TextView text1, text2, text3, text4, msg, rate;
    View frame1, frame2;
    FloatingActionButton fab;
    double r = 180.5;
    private Thread progressThread;
    private Handler handler;
    ImageView image;


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

        rate.setText(r + "");


        type = Typeface.createFromAsset(getAssets(), "round.ttf");
        text1.setTypeface(type);
        text2.setTypeface(type);
        text3.setTypeface(type);
        text4.setTypeface(type);
        msg.setTypeface(type);
        rate.setTypeface(type);

        handler = new Handler();

        ObjectAnimator objAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.trans);
        objAnim.setTarget(frame1);
        objAnim.setEvaluator(new ArgbEvaluator());
        objAnim.start();

    }

    public void onChartView(View v) {

        r = 180.5;

        Animation midAnim = AnimationUtils.loadAnimation(this, R.anim.mid_anim);
        fab.startAnimation(midAnim);
        fab.setVisibility(View.GONE);

        frame1.setVisibility(View.GONE);
        msg.setText("Last 12 hours average\nelectricity consumption");
        frame2.setVisibility(View.VISIBLE);

        ObjectAnimator objAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.back);
        objAnim.setTarget(frame2);
        objAnim.setEvaluator(new ArgbEvaluator());
        objAnim.start();

        Animation topTobottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);
        msg.startAnimation(topTobottom);

        image = (ImageView) findViewById(R.id.imageView);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animation1.setStartOffset(600);
        image.startAnimation(animation1);

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
                            rate.setText(r + "");
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

        r = 90.5;

        frame2.setVisibility(View.GONE);
        msg.setText("Total electricity consumption\nof Galaxy SOHO");
        frame1.setVisibility(View.VISIBLE);

        Animation topTobottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);
        msg.startAnimation(topTobottom);

        fab.setVisibility(View.VISIBLE);
        Animation downAnim = AnimationUtils.loadAnimation(this, R.anim.down_anim);
        fab.startAnimation(downAnim);

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
                            rate.setText(r + "");
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
