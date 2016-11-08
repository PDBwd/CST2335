package com.example.paul.lab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    public static final String PREFS = "examplePrefs"; // variable in which to hold preferences // constant with prefrence set.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
         String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In OnCreate()");


        Button nextAct = (Button)findViewById(R.id.button);
        nextAct.setOnClickListener(new View.OnClickListener(){ // this is a listener. will check to see if button is clicked or not.
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),ListItemsActivity.class); // this intent will allow you to move from one activity to the other.
                startActivityForResult(i,5);


            }
        });

        Button chatButton = (Button)findViewById(R.id.startChat);
        chatButton.setOnClickListener(new View.OnClickListener(){ // this is a listener. will check to see if button is clicked or not.
            @Override
            public void onClick(View v) {

                Log.i("OnCreate","User clicked Start Chat");
                Intent i = new Intent(getApplicationContext(),ChatWindow.class);
                startActivityForResult(i,5);


            }
        });

        Button weatherButton = (Button)findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),WeatherForecast.class);
                startActivityForResult(i,5);
            }

        });

    }

    protected void onActivityResult(int requestCode,int responseCode,Intent data){
        if (requestCode == 5){
            Log.i("ACTIVITY_NAME","Returned to StartActivityResult");

            // question 11
            if(requestCode == Activity.RESULT_OK){
                Context context = getApplicationContext();
                String messagePassed = data.getStringExtra("Response");
                int duration = Toast.LENGTH_SHORT; // setting the duration the toast is on screen for
                Toast toast = Toast.makeText(context,messagePassed,duration); // making Toast object toast
                toast.show(); // displaying toast object on bottom of screen
            }
        };
    }

    @Override
    protected void onResume(){
        super.onResume();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In OnResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onStart()");

        TextView tv = (TextView)findViewById(R.id.textView1);

        SharedPreferences example = getSharedPreferences(PREFS,0);

        String userEmail = example.getString("loginMessage","Email@domain.com");
        tv.setText(userEmail);
    }

    @Override
    protected void onPause(){
        super.onPause();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        String ACTIVITY_NAME = "StartActivity";
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }





}
