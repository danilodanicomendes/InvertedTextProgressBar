package com.danilomendes.ivertedtextprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.danilomendes.progressbar.InvertedTextProgressbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.itp_animation: {
                final InvertedTextProgressbar itp = (InvertedTextProgressbar) view;
                itp.startAnimation(10000);
                break;
            }
            case R.id.itp_progress: {
                final InvertedTextProgressbar itp = (InvertedTextProgressbar) view;
                itp.setMaxProgress(100);
                itp.setMinProgress(0);
                //final TextView textView = ((TextView) findViewById(R.id.tv_black));

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
        }
    }
}
