package com.example.cyberwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       new Handler().postDelayed(new Runnable() {


            @Override
           public void run() {

                Intent i = new Intent(MainActivity.this, loginpagee.class);
                startActivity(i);
                finish();
            }
        }, 5000);

    }
}