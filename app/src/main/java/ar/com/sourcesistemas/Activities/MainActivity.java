package ar.com.sourcesistemas.Activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import ar.com.sourcesistemas.Fragments.DatabaseInteraction;
import ar.com.sourcesistemas.Fragments.GmapsIntegration;
import ar.com.sourcesistemas.Fragments.Login;
import ar.com.sourcesistemas.Services.DrawerLogin;
import ar.com.sourcesistemas.database.DatabaseHandler;
import ar.com.sourcesistemas.entities.User;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    //region "variables"

    final public String TAG = MainActivity.class.getSimpleName();
    final public String TAG_DATABASE = "Database SQL";
    final public String TAG_FACEBOOK = "Facebook api";

    //Facebook
    private CallbackManager callbackManager;
    public TextView facebook_profile ;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;


    //NavViewer
    NavigationView navViewContext;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null)
//        {
//            android.support.v4.app.Fragment login = new Login();
//            android.support.v4.app.FragmentManager fn = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = fn.beginTransaction();
//            fragmentTransaction.replace(R.id.login_frame, login);
//            fragmentTransaction.commit();
//        }

        //region "NavigationDrawer"

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


            //region "message"
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
            //endregion

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navViewContext = (NavigationView) findViewById(R.id.nav_view);
        navViewContext.setNavigationItemSelectedListener(this);
        //endregion


        //region "Facebook"

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken)
            {
                //Log out
                if (newToken == null)
                {
                    DrawerLogin draw = new DrawerLogin(navViewContext);
                    Profile profile = Profile.getCurrentProfile();
                    draw.setDrawer(profile);
                }
            }
        };


        profileTracker = new ProfileTracker() {
            @Override
            //User Log in on new account. Creo que también aplica cuando se loguea por primera vez
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile)
            {
                //TODO Create welcome new user
//                displayMessage(newProfile);
            }
        };



        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        //If it's logged, track it
        DrawerLogin draw = new DrawerLogin(navViewContext);
        Profile profile = Profile.getCurrentProfile();
        draw.setDrawer(profile);
        //endregion


    }


    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, callback);



    }


    public FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>()
    {
        @Override
        //ATENCION : ESTA PORONGA NO CACHEA CUANDO TE LOGUEAS! Ver Activity Result :)
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Log.i(TAG_FACEBOOK, "Conexión con facebook exitosa");
        }

        @Override
        public void onCancel() {
            displayMessage("Not Logged");

        }

        @Override
        public void onError(FacebookException e) {
            Log.i(TAG_FACEBOOK, "Error en la conexión de facebook");
        }
    };

    @Override
    //Este cacheo si se puede ver
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        Profile profile = Profile.getCurrentProfile();
        if (profile != null)
        {
            DrawerLogin draw = new DrawerLogin(navViewContext);
            draw.setDrawer(profile);
        }

    }

    public boolean isLoggedIn(){
        return (AccessToken.getCurrentAccessToken()) != null;
    }

    private void displayMessage(String message){
        facebook_profile.setText(message);
    }


    //region "Navigation methods"

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Fragment login = new Login();
            FragmentManager fn = getFragmentManager();
            FragmentTransaction fragmentTransaction = fn.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, login);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_gallery) {

            Fragment gmaps = new GmapsIntegration();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, gmaps, gmaps.getTag());
            ft.commit();


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //endregion


    @Override
    public void onStop()
    {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();

    }

    @Override
    public void onResume(){
        super.onResume();
    }


}



