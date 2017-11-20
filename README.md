# Morse Code Messenger

Morse Code Messenger is an Android group chat application which gives users the functionality to send full-text Enlgish messages in real-time to all your friends through tapping Morse code. 

## Motivation

* A love and fascination for smartphones and mobile applications
* Develop an application that is helpful, interesting, and fun
* Intrigued by Morse code
* Importance of mobility and cloud services in the business world
* Develop an application that helps guard the user’s privacy and has the potential to keep the user safe and out of harm’s way

## Project Organization

### Authentication

* Front End:
	* Logn Page
	* New user registration page
* Back End:
	* Google Firebase Authentication
	* Requires an instance of FirebaseAuth method
	* Validation request to Firebase to check user’s sign in input
	* Current user authentication listener

### Main Activity

* Front End:
	* Display all messages sent from other users in English
	* List view with the message, user who sent the message, and time it was sent
* Back end:
	* Firebase Realtime Database
	* Instance of FirebaseDatabase
	* Reference pointing to the database
	* Appropriate message structure in JSON stored in database
	* Event listener to read and update in real-time from database
	* Method to push data to database for sending messages

### Settings Activity

* Front End:
	* Logout functionality 
	* Create preset messages functionality
	* Enable/ disable “SOS” feature
* Back End:
	* Instance of FirebaseAuth
	* Permissions to store local data
	* Use Shared Preferences to store key-value pairs



### Translation Algorithm

* Converts tapping the screen into an English message
* On click listener to keep track of the different types of tapping
* Java object for the ability to compare input to numbers and letter in Morse

### Current Location

* Use Google Play services API to make a request for the last known location of the user’s device
* Specify app permissions for the ability of the application to retrieve the current location of the device
* Use the Location Services Client through the FusedLocationProviderClient and call the getLastLocation function
* Used for sending a message containing the user’s current location

## Deployment

Will be available on the Google Play Store once the application is complete

## Built With

* [Google Firebase](http://firebase.google.com/) - Authentication and Database
* [Google APIs](https://developers.google.com/android/guides/overview) - Current Location retrieval

## Author

* **Jason Karrel**
