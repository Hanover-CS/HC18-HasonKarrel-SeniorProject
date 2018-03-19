package com.example.jasonk20.morsecodemessenger;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatRooms extends AppCompatActivity {

    private ListView chatRoomsList;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private EditText mEditText;
    private String chatroomName;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rooms);
//      This is set to true so splash screen isn't showed again
        Global.splashed = true;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Close keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mAuth = FirebaseAuth.getInstance();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        chatRoomsList = (ListView) findViewById(R.id.chatRoomsLV);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_of_rooms);
        chatRoomsList.setAdapter(arrayAdapter);
        mEditText = (EditText) findViewById(R.id.chatRoomsET);
        progressBar = (ProgressBar) findViewById(R.id.progressBarChatRoom);

        /**
         * Adds a new chatroom to the database when floating action button is clicked
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatroomName = mEditText.getText().toString();

                if ( chatroomName.equals("")) {
                    Snackbar.make(view, "Nothing Entered", Snackbar.LENGTH_SHORT).show();
                } else {

                    if (checkifthere(list_of_rooms, chatroomName) == false) {

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(mEditText.getText().toString(), "");
                        dbRef.updateChildren(map);
                        mEditText.setText("");
                        Snackbar.make(view, "Chat Room Added", Snackbar.LENGTH_SHORT).show();

                    } else {
                        Snackbar.make(view, "Chat Room already present", Snackbar.LENGTH_SHORT).show();
                        mEditText.setText("");
                    }

                }
            }
        });

        /**
         * Takes user to the MainActivity when a chatroom is clicked
         */
        chatRoomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ChatRooms.this, MainActivity.class);
                intent.putExtra("ChatRoomName",((TextView)view).getText().toString() );
                startActivity(intent);

            }
        });

        /**
         * Populates the ListView with the chatrooms fetched from the database
         */
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }

                progressBar.setVisibility(View.GONE);
                mEditText.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                list_of_rooms.clear();
                list_of_rooms.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * This method is used to check if the chatroom that is wanting to be
     * added by the user already exists in the list of chatrooms
     * @param list the list of chatrooms
     * @param chatroomName  chatroom name entered by the user
     * @return boolean This returns true is chatroom is already present
     */
    public boolean checkifthere(ArrayList<String> list, String chatroomName ) {

        for (String s: list) {
            if (chatroomName.equals(s)) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Checks if user is signed in
        FirebaseUser currUser = mAuth.getCurrentUser();
        //Takes you to sign in activity from the function below
        if (currUser == null) {
            sendToLogin();
        }
    }
//    Sends user to LoginActivity and calls finish method so they can't
//    return back until they have logged in
    private void sendToLogin() {
        Intent intent = new Intent(ChatRooms.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //    Adds toolbar items to activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //    Actions when selecting an item in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(ChatRooms.this, Settings.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
