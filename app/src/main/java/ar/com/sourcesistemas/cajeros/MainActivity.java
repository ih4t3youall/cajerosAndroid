package ar.com.sourcesistemas.cajeros;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.facebook.login.widget.LoginButton;

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


        databaseHandler = new DatabaseHandler(this, null, null, 1);

        Button save_button = (Button) findViewById(R.id.save);
        Button load_button = (Button) findViewById(R.id.load);

        name = (EditText) findViewById(R.id.name);
        last_name = (EditText) findViewById(R.id.last_name);
        facebook_profile = (TextView) findViewById(R.id.facebook_name);


        //BD
        save_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                User user = new User(name.getText().toString(), last_name.getText().toString(), facebook_profile.getText().toString());

                databaseHandler.insertUser(user);

            }
        });

        load_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                List<User> users = databaseHandler.getUsers();
                User user = users.get(0);
                name.setText(user.getName());
                last_name.setText(user.getLast_name());

                //Te deslogueas y pedis el load para ver el facebook
                facebook_profile.setText(user.getFacebook());
            }


        });

        //Facebok magic
        callbackManager = CallbackManager.Factory.create();

        /* La bardie NO inicializando estas variables antes de meter los tracking */
        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken)
            {
                //Log out
                if (newToken == null){
                    displayMessage("You've logged out");
                }

            }
        };


        profileTracker = new ProfileTracker() {
            @Override
            //User Log in on new account. Creo que también aplica cuando se loguea por primera vez
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };


        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, callback);
    }

    //Ni idea que hace
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    //Al hacer click en el boton
    public FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>()
    {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            displayMessage(profile);
        }

        @Override
        public void onCancel() {
            displayMessage("Not Logged");

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    private void displayMessage(Profile profile)
    {
        if(profile != null){
            facebook_profile.setText(profile.getName());
        }
    }


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
        displayMessage(profile);
    }


}



