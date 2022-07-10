package com.example.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_picName = "picName";
    public static final String KEY_rating ="rating";
    public static final String KEY_comment ="comment";
    public static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE = "ratings";
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_CREATE =
            "create table ratings(_id integer primary key autoincrement,"
                    + "picName text not null, rating text not null, comment text not null);";

    private Context context = null;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db)
        {
            try{
                db.execSQL(DATABASE_CREATE);
            }catch(SQLException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }//end method onCreate

        //upgrade db version
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG,"Upgrade database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS ratings");
            onCreate(db);
        }//end method onUpgrade
    }//end DatabaseHelper class

    //open the database
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //close the database
    public void close()
    {
        DBHelper.close();
    }

    public long insertRating(String picName, String rating, String comment)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_picName, picName);
        initialValues.put(KEY_rating, rating);
        initialValues.put(KEY_comment, comment);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE,KEY_ROWID + "=" + rowId,null) >0;
    }

    //retrieve all the contact
    public Cursor getAllRating()
    {
        return db.query(DATABASE_TABLE,new String[]{KEY_ROWID,KEY_picName,
                KEY_rating, KEY_comment},null,null,null,null,null);
    }


    public boolean updateRating(long rowId,String picName,String rating, String comment)
    {
        ContentValues cval = new ContentValues();
        cval.put(KEY_picName, picName);
        cval.put(KEY_rating, rating);
        cval.put(KEY_comment, comment);
        return db.update(DATABASE_TABLE, cval, KEY_ROWID + "=" + rowId,null) >0;
    }

}//end class DBAdapter
