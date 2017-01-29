package ar.com.sourcesistemas.cajeros;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;


public class MainActivity extends AppCompatActivity
{

    private CallbackManager callbackManager;
    public TextView textview ;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView)findViewById(R.id.textView);

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken)
            {
                //User logged out
                if (newToken == null){
                    displayMessage("You've logged out");
                }

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayName(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

    }

    public FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>()
    {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            displayName(profile);
        }

        @Override
        public void onCancel() {
            Profile profile = Profile.getCurrentProfile();
            displayMessage("Not Logged");

        }

        @Override
        public void onError(FacebookException e) {

        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void displayName(Profile profile)
    {
        if(profile != null){
            textview.setText(profile.getName());
        }
    }

    //No se como hacer overload
    private void displayMessage(String message)
    {
        textview.setText(message);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();

    }

    @Override
    public void onResume()
    {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayName(profile);
    }


}



