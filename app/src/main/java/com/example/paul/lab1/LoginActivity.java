package com.example.paul.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

        public static final String PREFS = "examplePrefs"; // variable in which to hold preferences // constant with prefrence set.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In OnCreate()");


    }

    @Override
    protected void onResume(){
        super.onResume();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In OnResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onStart()");

                            // (editText) is casting it back as an EditText
        final EditText et = (EditText)findViewById(R.id.loginText); // editText object et is going to hold the loginText // needs to be final to use inside class.
        Button nextAct = (Button)findViewById(R.id.loginButton); // button object whos id is the loginButton

        nextAct.setOnClickListener(new View.OnClickListener(){ // this is a listener. will check to see if button is clicked or not.
            @Override
            public void onClick(View v) {
                String loginText = et.getText().toString(); //getting whatever is in the EditText widget et.
                SharedPreferences examplePrefs = getSharedPreferences(PREFS,0); // passing the string prefs into object examplePrefs.
                SharedPreferences.Editor editor = examplePrefs.edit(); // allows the examplePrefs to be edited to whatever is set in the editText widget.
                editor.putString("loginMessage",loginText); // the key is loginMessage and he loginText is the value that the user has put.
                editor.commit(); // committing the changes to the EditText

                Intent i = new Intent(getApplicationContext(),StartActivity.class); // this intent will allow you to move from one activity to the other.
                startActivity(i);

            }
        });


    }

    @Override
    protected void onPause(){
        super.onPause();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        String ACTIVITY_NAME = "LoginActivity";
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

}
