package ar.com.sourcesistemas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import ar.com.sourcesistemas.entities.ATM;
import ar.com.sourcesistemas.entities.User;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ATM.db";
    private static final String TABLE_USER = "user";
    private static final String TABLE_ATM = "ATM";
    private static final String TABLE_COMMENT="comment";


    public static final String COLUMN_ID = "_id";

    private SQLiteDatabase db;


    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = this.getWritableDatabase();

    }





    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE=" CREATE TABLE  `user` ( `iduser` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,   " +
                "`name` VARCHAR(45) NULL, " +
                "`last_name` VARCHAR(45) NULL);";
        String CREATE_ATM_TABLE=" CREATE TABLE  `ATM` (`idATM` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "`address` VARCHAR(45) NULL," +
                "`lat` VARCHAR(100) NULL," +
                "`lon` VARCHAR(100) NULL," +
                "`atm_type` VARCHAR(45) NULL," +
                "`bank_name` VARCHAR(45) NULL," +
                "`added_by` long(5)," +
                "FOREIGN KEY(added_by) REFERENCES user(iduser));";
        String CREATE_COMMENT_TABLE = "CREATE TABLE `comment` (`idcomment`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "`text` VARCHAR(1500) NULL," +
                "`raiting` VARCHAR(45) NULL," +
                "`user_iduser` INT NOT NULL," +
                "`ATM_idATM` INT NOT NULL," +
                "`comment_raiting` INT(5) NULL, " +
                "FOREIGN KEY(user_iduser) REFERENCES user(iduser)," +
                "FOREIGN KEY(ATM_idATM) REFERENCES ATM(idATM));";

        db.execSQL(CREATE_ATM_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_COMMENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            //todo
        }
    }


    public void restartDatabase(Context context)
    {
        closeDatabase();
        context.deleteDatabase(DATABASE_NAME);
    }


    public List<User>  getUsers(){

        String query = "select * from "+TABLE_USER+";";
        Cursor cursor = db.rawQuery(query, null);

        List<User> users = new LinkedList<User>();
        while (cursor.moveToNext()){

            users.add(new User(cursor.getLong(0),cursor.getString(1),cursor.getString(2)));

        }


        return users;


    }
    public long insertUser(User user){

        ContentValues contentValues  = new ContentValues();

        //contentValues.put("iduser",user.getId());
        contentValues.put("name",user.getName());
        contentValues.put("last_name",user.getLast_name());

        long id = db.insert(TABLE_USER, null, contentValues);
        return id;


    }


    public List<ATM> getATM(){

        String query = "select * from "+TABLE_ATM+";";
        Cursor cursor = db.rawQuery(query, null);

        List<ATM> atms = new LinkedList<ATM>();
        while (cursor.moveToNext()){

            atms.add(new ATM(cursor.getLong(0),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),new User(cursor.getLong(6))));

        }

        return atms;

    }

    public long insertATM(ATM atm){

        ContentValues contentValues  = new ContentValues();

        contentValues.put("idATM",atm.getId());
        contentValues.put("address",atm.getAddress());
        contentValues.put("lat",atm.getLat());
        contentValues.put("lon",atm.getLon());
        contentValues.put("atm_type",atm.getType());
        contentValues.put("bank_bame",atm.getBank_name());
        contentValues.put("added_by",atm.getAdded_by().getId());
        long id = db.insert(TABLE_ATM, null, contentValues);
        return id;

    }

    public void closeDatabase(){

        db.close();

    }


}
