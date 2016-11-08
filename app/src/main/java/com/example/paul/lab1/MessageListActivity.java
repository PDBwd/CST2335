package com.example.paul.lab1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.paul.lab1.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Messages. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MessageDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MessageListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    ListView listView;
    Button sendButton;
    ArrayList<String> arrayList = new ArrayList<String>(); // arrayList to hold "chat"
    ChatDatabaseHelper Cdb;
    private boolean mTwoPane;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
      //  toolbar.setTitle(getTitle());

       // listView = (ListView)findViewById(R.id.chatListView);
        listView = (ListView)findViewById(R.id.chatListView);
        final EditText editText = (EditText)findViewById(R.id.messageText);
        sendButton = (Button)findViewById(R.id.sendButton);
        final ChatAdapter messageAdapter = new ChatAdapter(this); // chatAdapter is a built in adapater
        listView.setAdapter(messageAdapter);
        Cdb = new ChatDatabaseHelper(this);


        Cursor cursor = Cdb.getMessages(); // get messages method is of type Cursor from database helper class

        // cursor will move through the database to find the next text if there is any.
        while (cursor.moveToNext()) { arrayList.add(cursor.getString(cursor.getColumnIndex(Cdb.KEY_MESSAGE))); }


        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { // on click listener for the send button

                String chatText =  editText.getText().toString(); // changing editText to String
                arrayList.add(chatText); // adding the string from EditTest to arrayList
                boolean isInserted =  Cdb.insertData(chatText); // inserting the message text into the database
                if(isInserted = true)
                { Toast.makeText(MessageListActivity.this,"Message Sent",Toast.LENGTH_SHORT).show(); } // if the message inserts into the database this toast will show
                else {
                    Toast.makeText(MessageListActivity.this,"Message not Sent",Toast.LENGTH_SHORT).show(); } // if message does not nter the database this toast will show
                messageAdapter.notifyDataSetChanged(); // notifying the adapter that a message has been sent, changing from incoming to outgoing
                editText.setText(" "); // set the text on the send button to blank.

            } // end of onClick view

        }); // end of onClickListener


        if (findViewById(R.id.message_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    class ChatAdapter extends ArrayAdapter<String> { // custom adapter class // when youc all the adapter it forms the for loop for you.

        public ChatAdapter(MessageListActivity ctx) {
            super(ctx, 0);
        } // default constructor

        // method to return the number of rows that will be in your array
        // will tell how many times to run a for loop
        public int getCount(){ return arrayList.size(); } // will return the size of the array
        public String getItem(int position){ return arrayList.get(position); } // will return the item at position

        // getview method
        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {// inner class


            LayoutInflater Inflater = MessageListActivity.this.getLayoutInflater(); // an inflater inflates the xml layout into a view.
            View result = null;

            if(position%2 == 0){ // if position number in the array is odd do this, if number is even, do this.
                result = Inflater.inflate(R.layout.chat_row_incoming, null); // depending on the position, show layout incoming

            } else {
                result = Inflater.inflate(R.layout.chat_row_outgoing,null); // depending on the position, show layout outgoing

            }

            TextView message = (TextView)result.findViewById(R.id.messageText); // creating a message of type TextView connected to messageText
            final String messageText = getItem(position) ;
            message.setText(messageText);


            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MessageDetailFragment.ARG_ITEM_ID,messageText );
                        MessageDetailFragment fragment = new MessageDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.message_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MessageDetailActivity.class);
                        intent.putExtra(MessageDetailFragment.ARG_ITEM_ID,messageText );

                        context.startActivity(intent);
                    }

                }
            });

            return result; // return the view which is the Inflater.


        }
    } // end of chat adapter class

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MessageDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        MessageDetailFragment fragment = new MessageDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.message_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MessageDetailActivity.class);
                        intent.putExtra(MessageDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
