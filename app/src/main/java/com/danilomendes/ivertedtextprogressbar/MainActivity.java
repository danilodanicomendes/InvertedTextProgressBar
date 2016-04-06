package com.danilomendes.ivertedtextprogressbar;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.danilomendes.progressbar.InvertedTextProgressbar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.itp_progress:
            case R.id.itp_progress_2: {
                final InvertedTextProgressbar itp = (InvertedTextProgressbar) view;
                itp.startAnimation(11000);
                break;
            }
            case R.id.itp_progress_1: {
                final InvertedTextProgressbar itp = (InvertedTextProgressbar) view;
                itp.setTextPivot(20, -1);
                itp.getTextPaint().setTextAlign(Paint.Align.LEFT);
                itp.getTextInvertedPaint().setTextAlign(Paint.Align.LEFT);
                itp.startAnimation(11000);
                break;
            }
            case R.id.itp_progress_3: {
                final InvertedTextProgressbar itp = (InvertedTextProgressbar) view;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            itp.setText("Loading..." + i + "%");
                            final int a = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    itp.setText("Loading..." + a + "%");
                                    itp.setProgress(a);
                                }
                            });

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            }
            case R.id.itp_progress_4: {
                final InvertedTextProgressbar itp = (InvertedTextProgressbar) view;

                new Thread(new Runnable() {
                    Random r = new Random();

                    int getRandomInt(int min, int max) {
                        return r.nextInt((max - min) + 1) + min;
                    }

                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            itp.setText("Loading..." + i + "%");
                            final int a = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    itp.setText("Loading..." + a + "%");
                                    itp.setTextSize(getRandomInt(32, 40)); // Convert to desired dimension.
                                    itp.getTextPaint().setARGB(255, getRandomInt(0, 255),
                                            getRandomInt(0, 255), getRandomInt(0, 255));
                                    itp.getTextInvertedPaint().setARGB(255, getRandomInt(0, 255),
                                            getRandomInt(0, 255), getRandomInt(0, 255));
                                    itp.setProgress(a);
                                }
                            });

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            }
        }
    }
}
