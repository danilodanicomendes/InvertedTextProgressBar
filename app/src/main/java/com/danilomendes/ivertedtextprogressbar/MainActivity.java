package com.danilomendes.ivertedtextprogressbar;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danilomendes.progressbar.InvertedTextProgressbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final InvertedTextProgressbar image = (InvertedTextProgressbar) findViewById(R.id.progress_static);
        image.setScaleType(ImageView.ScaleType.FIT_START);
        image.setText("Please wait... Enjoy this cool effect...");
        image.setOnClickListener(new View.OnClickListener() {
            boolean aaa = true;
            int pr = 0;
            {image.setMaxProgress(100);
                image.setMinProgress(0);}

            @Override
            public void onClick(View v) {
                //if (aaa) {
                //    image.startAnimation(10000);
                //    aaa = false;
                //} else {
                //    aaa = true;
                final TextView textView = ((TextView) findViewById(R.id.tv_black));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            Log.i("MainActivity", "Index: " + i);
                            image.setText("Loading..." + i + "%");
                            final int a = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("Loading..." + a + "%");
                                    image.setProgress(a);
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



//                }
            }
        });

    }
}
