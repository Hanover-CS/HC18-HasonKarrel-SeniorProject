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

public class Settings extends AppCompatActivity {

    private Button mLogOut;
    private FirebaseAuth mAuth;
    private Button mPreset_Btn;
    private Switch mCurrLocation_Swtich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mLogOut = (Button) findViewById(R.id.logOut_Btn);
        mAuth = FirebaseAuth.getInstance();
        mPreset_Btn = (Button) findViewById(R.id.preset_Btn);
        mCurrLocation_Swtich = (Switch) findViewById(R.id.currLocation_Switch);

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

        mCurrLocation_Swtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                if (isChecked) {
                    editor.putString("SOS Functionality", "ON");
                    editor.commit();
                    Toast.makeText(Settings.this, "Toggle Switch ON",Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("SOS Functionality", "OFF");
                    editor.commit();
                    Toast.makeText(Settings.this, "Toggle Switch OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
