package org.example.fohormalai;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity_Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__start);

        ImageView imageView = (ImageView) findViewById(R.id.app_logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        imageView.startAnimation(animation);

        Thread startThread = new Thread() {

            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e)
                {
                    //do nothing
                } finally {

                    Intent i = new Intent(MainActivity_Start.this, MainActivity_Second.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        startThread.start();

    }

}
