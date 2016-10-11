package com.example.paul.lab1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        ImageButton cameraAct = (ImageButton)findViewById(R.id.imageButton);
        cameraAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 int REQUEST_IMAGE_CAPTURE = 1;


                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // code used from lab to start camera.
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);



                    }

            }
        });

        // question 8. setting the switch on or off to show Toast object.
        Switch mySwitch = (Switch)findViewById(R.id.switch1);
        //set the switch to on
        mySwitch.setChecked(true);
        // attaching the listerning to check if th state changes

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplicationContext();

                if(isChecked){ // if the switch is checked
                    CharSequence text ="Switch is On"; // the text message that will show in the toast
                    int duration = Toast.LENGTH_SHORT; // setting the duration the toast is on screen for
                    Toast toast = Toast.makeText(context,text,duration); // making Toast object toast
                    toast.show(); // displaying toast object on bottom of screen


                }else{ // if the switch is not checked
                    CharSequence text ="Switch is Off";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show(); // displaying toast object on bottom of screen

                }

            }
        });

        // question 9 - dialog box
        final CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox); // brining in a checkbox object.
        checkBox.setOnClickListener(new View.OnClickListener() { // placing a new click listener to the checkbox.
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.dialog);
                builder.setTitle(R.string.dialog_title);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // if the user decides to quit the app
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Reponse","My Information to share");
                        setResult(Activity.RESULT_OK,resultIntent);
                        finish();

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // if the user decides to continue the app
                        onResume();
                        checkBox.setChecked(false); // method to uncheck the box once question is answered.
                    }
                });
                builder.show();

            }
        });



    }






    @Override
    protected void onResume(){
        super.onResume();
        String ACTIVITY_NAME = "ListItemsActivity";
        Log.i(ACTIVITY_NAME,"In OnResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        String ACTIVITY_NAME = "ListItemsActivity";
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        String ACTIVITY_NAME = "ListItemsActivity";
        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        String ACTIVITY_NAME = "ListItemsActivity";
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        String ACTIVITY_NAME = "ListItemsActivity";
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }
}
