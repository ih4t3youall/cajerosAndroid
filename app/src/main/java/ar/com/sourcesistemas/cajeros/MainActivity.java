package ar.com.sourcesistemas.cajeros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import ar.com.sourcesistemas.database.DatabaseHandler;
import ar.com.sourcesistemas.entities.User;

public class MainActivity extends AppCompatActivity {


    private DatabaseHandler databaseHandler;

    private EditText name;
    private EditText last_name;
    private EditText password;
    private EditText facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandler = new DatabaseHandler(this,null,null,1);

        Button save_button = (Button)findViewById(R.id.save);
        Button load_button = (Button)findViewById(R.id.load);

        name =(EditText) findViewById(R.id.name);
        last_name =(EditText)  findViewById(R.id.last_name);
        password = (EditText) findViewById(R.id.password);
        facebook =(EditText)  findViewById(R.id.facebook);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(name.getText().toString(),last_name.getText().toString(),password.getText().toString(),facebook.getText().toString());

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
                facebook.setText(user.getFacebook());
                password.setText(user.getPassword());


            }
        });




    }
}
