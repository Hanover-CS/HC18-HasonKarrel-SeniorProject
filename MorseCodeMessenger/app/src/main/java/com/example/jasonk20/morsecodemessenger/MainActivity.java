package com.example.jasonk20.morsecodemessenger;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button mSend_Btn;
    private String temp_key;
    private Toolbar mToolbar;
    private Button mBackSpace;
    private Button mDot;
    private Button mDash;
    private Button mSpacebar;
    private Translation translation = new Translation();
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private double lat;
    private double lang;
    private String latLang;
    private LocationManager lm;

    private String finalMessage = "";
    private List<ChatBubble> ChatBubbles;
    private ArrayAdapter<ChatBubble> adapter;
    private ListView listView;
    private TextView userMessageTV;
    private String sosOn_or_Off;
    private SharedPreferences sharedPref;

    private ArrayList<String> morseArr = new ArrayList<>();
    private String chatRoomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChatBubbles = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_msg);

        //set ListView adapter first
        adapter = new MessageAdapter(this, R.layout.left_chat_bubble, ChatBubbles);
        listView.setAdapter(adapter);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        userMessageTV = (TextView) findViewById(R.id.userMessage_TV);





        Bundle extras = getIntent().getExtras();
        chatRoomName = extras.getString("ChatRoomName");

        mToolbar = (Toolbar) findViewById(R.id.my_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(chatRoomName);
        mToolbar.setNavigationIcon(R.drawable.arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        mSend_Btn = (Button) findViewById(R.id.send_Btn);
        mBackSpace = (Button) findViewById(R.id.backspace_Btn);
        mDot = (Button) findViewById(R.id.dot_Btn);
        mDash = (Button) findViewById(R.id.dash_Btn);
        mSpacebar = (Button) findViewById(R.id.spacebar_Btn);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(chatRoomName);


        mDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morseArr.add("short");
                updateUserMessage();
            }
        });

        mDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morseArr.add("long");
                updateUserMessage();
            }
        });

        mSpacebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morseArr.add(" ");
                updateUserMessage();
            }
        });

        mBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (morseArr.size() > 0) {
                    morseArr.remove(morseArr.size()-1);
                    updateUserMessage();
                }
            }
        });


        mSend_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finalMessage = translation.Translate(morseArr);
                morseArr.clear();
                userMessageTV.setText("Enter message");

                if (finalMessage == null || finalMessage == "") {
                    Toast.makeText(MainActivity.this, "No Message Created", Toast.LENGTH_SHORT).show();
                } else {

                    Map<String, Object> temp_Map = new HashMap<String, Object>();
                    temp_key = myRef.push().getKey();
                    myRef.updateChildren(temp_Map);

                    DatabaseReference message_Root = myRef.child(temp_key);
                    Map<String, Object> messageMap = new HashMap<String, Object>();


                    String currDate = getDate();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String currUser;



                    for (int j =1; j < 6; j++ ) {
                        if (finalMessage.equals(Integer.toString(j))) {
                            finalMessage = getPresetMessage(finalMessage);
                        }
                    }

                    sosOn_or_Off = sharedPref.getString("SOS Functionality", "");


                    if (finalMessage.equals("S O S") && sosOn_or_Off.equals("ON")) {

                        finalMessage = sosMessage();
                    }

                    if (user != null) {
                        currUser = removeEmail(user);

                        messageMap.put("Username", currUser);
                        messageMap.put("Message", finalMessage);
                        messageMap.put("DateSent", currDate);
//                    Adds message to database
                        message_Root.updateChildren(messageMap);
                    }
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

    private void updateUserMessage() {

        String userMessage = "";

        if (morseArr.size() == 0) {
            userMessageTV.setText("Enter Message");
        } else {

            for (String str : morseArr)
            {

                if (str.equals("short")) {

                    userMessage += ".";
                } else if (str.equals("long")) {

                    userMessage += "-";
                } else
                userMessage += str;
            }

            userMessageTV.setText(userMessage);

        }

    }

    private String removeEmail(FirebaseUser user) {

        String temp = user.getEmail().toString();
        String userName = "";
        int i =0;

        while(temp.charAt(i) != '@')
        {
            userName = userName + Character.toString(temp.charAt(i));
            i++;
        }
        return userName;
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("EE h:mm a");
        Date date = new Date();
        String currDate = dateFormat.format(date);

        return currDate;

    }

    private String sosMessage() {
//        GET CURRENT LOCATION

        Location();
        String sosTempMessage = "Help I am in trouble and need assistance, here is my location "  + latLang;

        return sosTempMessage;
    }

    private String getPresetMessage(String englishMessage) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String message = sharedPref.getString(englishMessage, "");


        return message;
    }

    private String message, date, user;


    //  ADDS MESSAGE TO THE LIST VIEW
    private void add_Message(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            date = (String) ((DataSnapshot) i.next()).getValue();
            message = (String) ((DataSnapshot) i.next()).getValue();
            user = (String) ((DataSnapshot) i.next()).getValue();

            String chatMessage = date + "\n" + user + "\n" + message;



            //add message to list
            ChatBubble ChatBubble = new ChatBubble(chatMessage);
            ChatBubbles.add(ChatBubble);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAndRequestPermissions();
        Location();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Stop gathering location data when not on the main activity page
        if (lm != null ) {
            lm.removeUpdates(locationListener);
            lm = null;
        }
    }

    private boolean checkAndRequestPermissions() {
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int storage1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (storage1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void Location() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,10,locationListener);


        } else {
            checkAndRequestPermissions();
        }
    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            lat = location.getLatitude();
            lang = location.getLongitude();
            latLang = "Latitude: "+lat + " Longitude: "+lang;
//            Toast.makeText(MainActivity.this,latLang,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };





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
