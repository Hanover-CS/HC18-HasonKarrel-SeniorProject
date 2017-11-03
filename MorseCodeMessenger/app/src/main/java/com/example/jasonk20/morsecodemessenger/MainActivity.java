package com.example.jasonk20.morsecodemessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private ImageButton mSend_Btn;
//    private EditText mUserMessage;
    private String temp_key;
    private TextView mMessage;
    private Toolbar mToolbar;
    private ImageButton mBackSpace;
    private ImageButton mDot;
    private ImageButton mDash;
    private ImageButton mSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


//        mUserMessage = (EditText) findViewById(R.id.userMessage_ET);
        mMessage = (TextView) findViewById(R.id.chatMessage);

        mToolbar = (Toolbar) findViewById(R.id.my_Toolbar);
        setSupportActionBar(mToolbar);

        mSend_Btn = (ImageButton) findViewById(R.id.send_Btn);
        mBackSpace = (ImageButton) findViewById(R.id.backspace_Btn);
        mDot = (ImageButton) findViewById(R.id.short_Btn);
        mDash = (ImageButton) findViewById(R.id.long_Btn);
        mSpace = (ImageButton) findViewById(R.id.spacebar_Btn);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Messages");


//   **** Make send button to be initially not clickable and when there is correct mores code, make button clickable
        mSend_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = myRef.push().getKey();
                myRef.updateChildren(map);

                DatabaseReference message_root = myRef.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
//                String message = mUserMessage.getText().toString();
                DateFormat dateFormat = new SimpleDateFormat("EE hh:mm a");
                Date date = new Date();
                String currDate = dateFormat.format(date);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currUser;
                if (user != null) {
                    currUser = user.getEmail();
                    map2.put("UserName", currUser);
                    map2.put("Message", message);
                    map2.put("DateSent", currDate);
//                    Adds message to database
                    message_root.updateChildren(map2);
                }
//                mUserMessage.setText("");
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
