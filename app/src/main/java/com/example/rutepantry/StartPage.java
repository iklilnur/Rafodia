package com.example.rutepantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                home();
            }
        }, 3000);
    }

    public void home(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}