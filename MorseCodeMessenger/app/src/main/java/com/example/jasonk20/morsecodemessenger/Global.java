package com.example.jasonk20.morsecodemessenger;

import android.app.Application;

/**
 * Created by jasonk20 on 3/16/18.
 *
 * This class is used to initialize global variables for the application
 */

public class Global extends Application {

//    Used as a global variable to be able to know when the splash screen has already been showed onStart
    public static boolean splashed = false;
}
