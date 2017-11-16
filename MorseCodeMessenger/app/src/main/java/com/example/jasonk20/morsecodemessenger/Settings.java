package com.example.jasonk20.morsecodemessenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {

    private Button mLogOut;
    private FirebaseAuth mAuth;
    private Button mPreset_Btn;
    private Switch mCurrLocation_Switch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mLogOut = (Button) findViewById(R.id.logOut_Btn);
        mAuth = FirebaseAuth.getInstance();
        mPreset_Btn = (Button) findViewById(R.id.preset_Btn);
        mCurrLocation_Switch = (Switch) findViewById(R.id.currLocation_Switch);

        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(Settings.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mPreset_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, PresetMessagesActivity.class);
                startActivity(intent);
            }
        });


        mCurrLocation_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                if (isChecked) {
                    editor.putString("SOS Functionality", "ON");
                    editor.commit();
                } else {
                    editor.putString("SOS Functionality", "OFF");
                    editor.commit();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sosOn_or_Off = sharedPref.getString("SOS Functionality","");
        if (sosOn_or_Off.equals("ON")) {
            mCurrLocation_Switch.setChecked(true);
        }else {
            mCurrLocation_Switch.setChecked(false);
        }
    }


}
