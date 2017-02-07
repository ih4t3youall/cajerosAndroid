package ar.com.sourcesistemas.Fragments;



import android.app.Fragment;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.os.Bundle;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Profile;

import ar.com.sourcesistemas.Activities.R;
import ar.com.sourcesistemas.database.DatabaseHandler;
import ar.com.sourcesistemas.entities.User;

public class Login extends Fragment
{


    //region "Variables"
    final public String TAG_DATABASE = "Database SQL";
    final public String TAG_FACEBOOK = "Facebook api";

    public TextView show_message;

    //Database
    private DatabaseHandler databaseHandler;

    //endregion
    public Login()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        databaseHandler = new DatabaseHandler(getActivity());


        show_message = (TextView) getView().findViewById(R.id.login_message);

        Button save_button = (Button) getView().findViewById(R.id.save);

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
                    displayMessage("Por favor, loguea antes de registrar");
                }

            }
        });



    }

    public void displayMessage(String mensaje){
        show_message.setText(mensaje);
    }

}
