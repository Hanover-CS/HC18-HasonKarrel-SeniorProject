package com.example.jasonk20.morsecodemessenger;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This activity is for users to create a new account so they can log into the application
 */

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText regEmail_ET;
    private EditText regPassword_ET;
    private Button reg_Btn;
    private ImageButton backView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail_ET = (EditText) findViewById(R.id.registerEmail_ET);
        regPassword_ET = (EditText) findViewById(R.id.register_Password_ET);
        reg_Btn = (Button) findViewById(R.id.register_Btn);
        backView = (ImageButton) findViewById(R.id.backview);
        progressBar = (ProgressBar) findViewById(R.id.progressBarReg);
        mAuth = FirebaseAuth.getInstance();


        /**
         * Gathers the email and password entered by the user
         * and sends to the createNewUser method
         */
        reg_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regEmail_ET.getText().toString();
                String password = regPassword_ET.getText().toString();

                if(email.length() == 0 || password.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Nothing Entered", Toast.LENGTH_LONG).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    createNewUser(email, password);
                }
            }
        });

//        Sends user to the login activity
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



    /**
     * This method is used to create a new user and
     * their credentials are saved to Google Firebase Authentication
     * @param email1 Email entered by the user
     * @param password1  Password entered by the user
     */
    private void createNewUser(String email1, String password1) {
        mAuth.createUserWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {
                            // Sign in success
                            Intent intent = new Intent(RegisterActivity.this, ChatRooms.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Could not create new account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
