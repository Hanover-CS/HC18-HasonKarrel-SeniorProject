package com.example.jasonk20.morsecodemessenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PresetMessagesActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private Button mPreset_Btn;
    private String spinnerItem;
    private EditText mPresetMessage;
    private String userInputMessage;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_messages);
        mSpinner = (Spinner)findViewById(R.id.presetSpinner);
        mPreset_Btn = (Button)findViewById(R.id.presetSubmit_Btn);
        mPresetMessage = (EditText) findViewById(R.id.presetMessage);
        mToolbar = (Toolbar) findViewById(R.id.presetMessage_Toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Preset Messages");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Integer[] spinnerItems = new Integer[]{1,2,3,4,5};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mPreset_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerItem = mSpinner.getSelectedItem().toString();
                userInputMessage = mPresetMessage.getText().toString();

                if (!(mPresetMessage.getText().toString().equals(""))) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(spinnerItem, userInputMessage);
                    editor.commit();
                    mPresetMessage.setText("");
                    Toast.makeText(PresetMessagesActivity.this, "Preset Message Set",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PresetMessagesActivity.this, "Nothing Entered",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
