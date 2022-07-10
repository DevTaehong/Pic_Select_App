package com.example.assignment3;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.database.*;//for cursor
import java.io.*;

import androidx.appcompat.app.AppCompatActivity;

public class DBActivity extends AppCompatActivity {
    EditText etPicName;
    EditText etComment;
    RatingBar ratingBar;
    Button btnSubmit;
    Button btnRead;
    Button btnUpdate;
    Button btnDelete;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        etPicName = findViewById(R.id.etPicName);
        etComment = findViewById(R.id.etComment);
        ratingBar = findViewById(R.id.ratingBar); // initiate a rating bar
        ratingBar.setNumStars(5); // set total number of stars

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String s = extras.getString("KEY");
        }


        try
        {
            String destPath = "/data/data/" + getPackageName() +"/database/MyDB";
            //Alternate way to do destPath:
            //String destPath = Environment.getExternalStorageDirectory().getPath() +
            //getPackageName() + "/database/MyDB";
            File f = new File(destPath);
            if(!f.exists())
            {
                CopyDB(getBaseContext().getAssets().open("mydb"),
                        new FileOutputStream(destPath));
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        DBAdapter db = new DBAdapter(this);

        //Create
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                long id = db.insertRating(etPicName.getText() +"",
                        ratingBar.getRating()+"",etComment.getText()+"");
                db.close();
            }
        });

        //get all ratings - READ
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                Cursor c = db.getAllRating();
                if(c.moveToFirst())
                {
                    do
                    {
                        DisplayRating(c);
                    }while(c.moveToNext());
                }
                db.close();
            }
        });


        //update a rating - UPDATE
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                if(db.updateRating(1,etPicName.getText()+"",
                        ratingBar.getRating()+"",etComment.getText()+""))
                    Toast.makeText(getBaseContext(), "Update successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getBaseContext(),"Update failed",Toast.LENGTH_LONG).show();
                db.close();
            }
        });

        //delete a rating - DELETE
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                if(db.deleteContact(1))
                    Toast.makeText(getBaseContext(),"Delete successful",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getBaseContext(),"Delete failed",Toast.LENGTH_LONG).show();

                db.close();
            }
        });
    }//end method onCreate

    public void CopyDB(InputStream inputStream,OutputStream outputStream)
            throws IOException{
        //copy 1k bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer,0,length);
        }
        inputStream.close();
        outputStream.close();
    }//end method CopyDB

    public void DisplayRating(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Pic Name: " + c.getString(1) + "\n" +
                        "Rating: " + c.getString(2) + "\n" +
                        "Comment: " + c.getString(3),
                Toast.LENGTH_LONG).show();
    }

}//end class
