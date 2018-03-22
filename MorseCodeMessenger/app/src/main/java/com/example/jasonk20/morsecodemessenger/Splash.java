package com.example.jasonk20.morsecodemessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.Menu;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

/**
 * Created by jasonk20 on 3/15/18.
 *
 * This class is used to display an initial loading splash screen once
 * the application is initially opened and not already running in the background
 */

public class Splash extends Activity {

//    Splash wait time
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

//        Check if app is being created or resuming
        if (Global.splashed) {
            Intent intent = new Intent(Splash.this, ChatRooms.class);
            startActivity(intent);
            finish();
        } else {
//         Display splash screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this, ChatRooms.class);
                    Splash.this.startActivity(intent);
//                  Fades the splash screen out into the ChatRoom activity
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    Splash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}
