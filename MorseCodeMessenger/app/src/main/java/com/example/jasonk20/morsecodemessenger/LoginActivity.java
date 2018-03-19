package com.example.jasonk20.morsecodemessenger;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText mEmail_ET;
    private EditText mPassword_ET;
    private Button mLogin_Btn;
    private FirebaseAuth mAuth;
    private TextView needAccount_TV;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail_ET = (EditText) findViewById(R.id.email_ET);
        mPassword_ET = (EditText) findViewById(R.id.password_ET);
        mLogin_Btn = (Button) findViewById(R.id.login_Btn);
        needAccount_TV = (TextView) findViewById(R.id.needAccount_TV);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        /**
         * Gathers email and password entered from the user for sign in
         */
        mLogin_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail_ET.getText().toString();
                String password = mPassword_ET.getText().toString();

                if (email.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Nothing Entered", Toast.LENGTH_SHORT).show();
                } else {

                    signin_existingUser(email, password);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        needAccount_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This method is used to authenticate the user's credentials
     * with the account they have already created in Google Firebase
     * @param email1 This is th user's email
     * @param password1  This is the user's password
     */

    private void signin_existingUser(String email1, String password1) {
        mAuth.signInWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {
                            // Sign in success
                            Intent intent = new Intent(LoginActivity.this, ChatRooms.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Could not Sign In", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
