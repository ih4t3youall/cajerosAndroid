package ar.com.sourcesistemas.connector;

import android.content.Context;


import ar.com.sourcesistemas.database.DatabaseHandler;
import okhttp3.OkHttpClient;

public class Connector {
    private Context context;



    final OkHttpClient client = new OkHttpClient();
    private String[] directorios = null;
    private DatabaseHandler databaseHandler;


    public Connector(Context context) throws Exception {

        databaseHandler = new DatabaseHandler(context);


    }


}
