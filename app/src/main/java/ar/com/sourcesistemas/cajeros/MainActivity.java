package ar.com.sourcesistemas.cajeros;


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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import org.w3c.dom.Text;

import java.sql.SQLDataException;
import java.util.List;

import ar.com.sourcesistemas.database.Constants;
import ar.com.sourcesistemas.database.DatabaseHandler;
import ar.com.sourcesistemas.entities.User;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    //region "variables"

    private static final String TAG = MainActivity.class.getSimpleName();
    final public String TAG_DATABASE = "Database SQL";

    //Facebook
    private CallbackManager callbackManager;
    public TextView facebook_profile ;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;



    //Database connection
    private DatabaseHandler databaseHandler;

    private TextView name;
    private TextView last_name;


    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region initialize
        databaseHandler = new DatabaseHandler(this, null, null, 1);

        Button save_button = (Button) findViewById(R.id.save);
        Button load_button = (Button) findViewById(R.id.load);
        Button clear_button = (Button) findViewById(R.id.clear_fields);

        name = (TextView) findViewById(R.id.name);
        last_name = (TextView) findViewById(R.id.last_name);
        facebook_profile = (TextView) findViewById(R.id.facebook_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //endregion

        //region "BD"
        save_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Profile profile = Profile.getCurrentProfile();
                if(profile != null)
                {
                    User user = new User(profile.getFirstName() + " " + profile.getMiddleName(), profile.getLastName());
                    try{
                        databaseHandler.insertUser(user);
                    }
                    catch (SQLiteDatabaseCorruptException sql){
                        sql.getStackTrace();
                        Log.e(TAG_DATABASE, "No se pudo conectar a la base de datos");
                    }
                    displayMessage("El usuario fue guardado");
                }else{
                    displayMessage("Por favor, loguea antes de guardar");
                }

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

                displayMessage("Welcome back!");
            }
        });

        clear_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name.setText("");
                last_name.setText("");
            }
        });



        //endregion

        //region "Facebook"

        callbackManager = CallbackManager.Factory.create();

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
//                displayMessage(newProfile);
            }
        };


        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        //endregion

        //region "NavigationDrawer"

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //endregion
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
            showProfile(profile);
        }

        @Override
        public void onCancel() {
            displayMessage("Not Logged");

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    private void showProfile(Profile profile)
    {
        String firstName = profile.getFirstName() + " " + profile.getMiddleName();
        String lastName = profile.getLastName();

        Log.i("Facebook", firstName);
        Log.i("Facebook", lastName);
        name.setText(firstName);
        last_name.setText(lastName);
    }

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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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
    public void onResume()
    {
        super.onResume();
    }


}



