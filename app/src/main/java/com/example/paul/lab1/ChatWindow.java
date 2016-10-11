package com.example.paul.lab1;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatWindow extends AppCompatActivity {

    ListView listView;
    Button sendButton;
    ArrayList<String> arrayList = new ArrayList<String>(); // arrayList to hold "chat"
    ChatDatabaseHelper Cdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);


        listView = (ListView)findViewById(R.id.chatListView);
        final EditText editText = (EditText)findViewById(R.id.messageText);
        sendButton = (Button)findViewById(R.id.sendButton);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
        Cdb = new ChatDatabaseHelper(this);

        Cursor cursor = Cdb.getMessages();

        // cursor will move through the database to find the next text if there is any.
        while (cursor.moveToNext()) { arrayList.add(cursor.getString(cursor.getColumnIndex(Cdb.KEY_MESSAGE))); }


        sendButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) { // on click listener for the send button

                    String chatText =  editText.getText().toString(); // changing editText to String
                    arrayList.add(chatText); // adding the string from EditTest to arrayList
                    boolean isInserted =  Cdb.insertData(chatText); // inserting the message text into the database
                    if(isInserted = true){ Toast.makeText(ChatWindow.this,"Message Sent",Toast.LENGTH_SHORT).show(); } // if the message inserts into the database this toast will show
                    else { Toast.makeText(ChatWindow.this,"Message not Sent",Toast.LENGTH_SHORT).show(); } // if message does not nter the database this toast will show
                    messageAdapter.notifyDataSetChanged(); // notifying the adapter that a message has been sent, changing from incoming to outgoing
                    editText.setText(" "); // set the text on the send button to blank.

                } // end of onClick view

            }); // end of onClickListener




    }
    // for loop class.
    class ChatAdapter extends ArrayAdapter<String>{ // custom adapter class // when youc all the adapter it forms the for loop for you.

        public ChatAdapter(ChatWindow ctx) {
            super(ctx, 0);
        } // default constructor

        // method to return the number of rows that will be in your array
        // will tell how many times to run a for loop
        public int getCount(){ return arrayList.size(); } // will return the size of the array
        public String getItem(int position){ return arrayList.get(position); } // will return the item at position

        // getview method
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater Inflater = ChatWindow.this.getLayoutInflater(); // an inflater inflates the xml layout into a view.
            View result = null;
            if(position%2 == 0){ // if position number in the array is odd do this, if number is even, do this.
                result = Inflater.inflate(R.layout.chat_row_incoming, null); // depending on the position, show layout incoming

            } else {
                result = Inflater.inflate(R.layout.chat_row_outgoing,null); // depending on the position, show layout outgoing

            }

            TextView message = (TextView)result.findViewById(R.id.messageText); // creating a message of type TextView connected to messageText
            message.setText(getItem(position));                                 // the messageText is the input typed in.

            return result; // return the view which is the Inflater.


        }
    } // end of chat adapter class

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Cdb.close(); // closing the database when the onDestroy is called.
        String ACTIVITY_NAME = "ChatWindow";
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }



}
