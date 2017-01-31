package ar.com.sourcesistemas.cajeros;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;

import java.util.List;

import ar.com.sourcesistemas.database.DatabaseHandler;
import ar.com.sourcesistemas.entities.User;



public class MainActivity extends AppCompatActivity
{

    //Facebook
    private CallbackManager callbackManager;
    public TextView facebook_profile ;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;



    //Database connection
    private DatabaseHandler databaseHandler;

    private EditText name;
    private EditText last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        facebook_profile = (TextView)findViewById(R.id.facebook_name);



        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        databaseHandler = new DatabaseHandler(this,null,null,1);

        Button save_button = (Button)findViewById(R.id.save);
        Button load_button = (Button)findViewById(R.id.load);

        name =(EditText) findViewById(R.id.name);
        last_name =(EditText)  findViewById(R.id.last_name);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(name.getText().toString(),last_name.getText().toString(),facebook_profile.getText().toString());

                databaseHandler.insertUser(user);

            }
        });

        load_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> users =  databaseHandler.getUsers();
                User user = users.get(0);
                name.setText(user.getName());
                last_name.setText(user.getLast_name());


            }


        });

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
            facebook_profile.setText(profile.getName());
        }
    }

    //No se como hacer overload
    private void displayMessage(String message)
    {
        facebook_profile.setText(message);
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



