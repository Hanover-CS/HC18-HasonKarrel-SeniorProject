package com.example.jasonk20.morsecodemessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private Button mSend_Btn;
    private TextView mUserMessage;
    private String temp_key;
    private TextView mMessage;
    private Toolbar mToolbar;
    private Button mBackSpace;
    private Button mDot;
    private Button mDash;
    private Button mSpacebar;
    private ArrayList<String> currLetter = new ArrayList<String>();
    private String[] allLetters = new String[20];
    private int letterTracker = 0;
    private String letter;
    private Translation translation = new Translation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        mUserMessage = (TextView) findViewById(R.id.userMessage_TV);
        mMessage = (TextView) findViewById(R.id.chatMessage);

        mToolbar = (Toolbar) findViewById(R.id.my_Toolbar);
        setSupportActionBar(mToolbar);

        mSend_Btn = (Button) findViewById(R.id.send_Btn);
        mBackSpace = (Button) findViewById(R.id.backspace_Btn);
        mDot = (Button) findViewById(R.id.dot_Btn);
        mDash = (Button) findViewById(R.id.dash_Btn);
        mSpacebar = (Button) findViewById(R.id.spacebar_Btn);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Messages");


        mDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currLetter.add("short");
                letter = translation.Translate(currLetter);
                allLetters[letterTracker] = letter;
                mUserMessage.setText(Arrays.toString(allLetters));
            }
        });

        mDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currLetter.add("long");
                letter = translation.Translate(currLetter);
                allLetters[letterTracker] = letter;
                mUserMessage.setText(Arrays.toString(allLetters));
            }
        });

        mSpacebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterTracker++;
                currLetter.clear();
                letter = "";
            }
        });




//   **** Make send button to be initially not clickable and when there is correct mores code, make button clickable
        mSend_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = myRef.push().getKey();
                myRef.updateChildren(map);

                DatabaseReference message_root = myRef.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();

                DateFormat dateFormat = new SimpleDateFormat("EE hh:mm a");
                Date date = new Date();
                String currDate = dateFormat.format(date);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currUser;
                if (user != null) {
                    currUser = user.getEmail();
                    map2.put("Username", currUser);
                    map2.put("Message", Arrays.toString(allLetters));
                    map2.put("DateSent", currDate);
//                    Adds message to database
                    message_root.updateChildren(map2);
                    letterTracker = 0;
                }
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                add_Message(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                add_Message(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String message, date, user;


    private void add_Message(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            user = (String) ((DataSnapshot)i.next()).getValue();
            message  = (String) ((DataSnapshot)i.next()).getValue();
            date = (String) ((DataSnapshot)i.next()).getValue();

            mMessage.append(user + " : " + message + " : " + date + " \n");
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        //Checks if user is signed in
        FirebaseUser currUser = mAuth.getCurrentUser();
        //Takes you to sign in activity from the function below
        if(currUser == null) {
            sendToLogin();
        }
    }
    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
