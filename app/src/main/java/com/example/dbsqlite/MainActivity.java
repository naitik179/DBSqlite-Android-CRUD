package com.example.dbsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBManager db;
    EditText ed1, ed2, ed3;
    Button b1, b2, b3, b4;
    String idu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBManager(this);
        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.email);
        ed3 = findViewById(R.id.roll);


        b1 = findViewById(R.id.insert);
        b2 = findViewById(R.id.read);
        b3= findViewById(R.id.update);
        b4 = findViewById(R.id.delete);

        addData();
        viewAll();
        update();
        delete();


    }

    public void addData()
    {

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result;
                result = db.insert(ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString());
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                ed1.setText("");
                ed2.setText("");
                ed3.setText("");
            }
        });
    }

    public void viewAll()
    {

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res= db.getAllData();

                if(res.getCount()==0)
                {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("Id : " + res.getString(0)+"\n");
                    buffer.append("Name : " + res.getString(1)+"\n");
                    buffer.append("Email : " + res.getString(2)+"\n");
                    buffer.append("Roll No : " + res.getString(3)+"\n\n");

                }

                showMessage("Data",buffer.toString());
            }
        });
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void update()
    {
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailtoupdate;
                emailtoupdate=ed2.getText().toString();

                Log.i("ID","Value Email is "+emailtoupdate);

                Cursor res=db.getarow(emailtoupdate);

                if(res.getCount() == 0 )
                {
                    showMessage("Error ", "Invalid Email Address");
                }


                if(res.moveToFirst())
                {
                    idu=res.getString(0);

                    Log.i("ID","Value is "+idu);
                    String re=  db.updateData(idu,ed1.getText().toString(), ed2.getText().toString(),ed3.getText().toString());

                    Toast.makeText(getApplicationContext(), re, Toast.LENGTH_SHORT).show();

                    ed1.setText("");
                    ed2.setText("");
                    ed3.setText("");

                }
            }
        });
    }


    public void delete()
    {
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailtodelete;
                emailtodelete=ed2.getText().toString();

                Log.i("ID","Value Email is "+emailtodelete);

                Cursor res=db.getarow(emailtodelete);

                if(res.getCount() == 0 )
                {
                    showMessage("Error ", "Invalid Email Address");
                }

                if(res.moveToFirst())
                {
                    idu=res.getString(0);

                    Log.i("ID","Value is "+idu);
                    String re=  db.deleteData(idu);

                    Toast.makeText(getApplicationContext(), re, Toast.LENGTH_SHORT).show();

                    ed1.setText("");
                    ed2.setText("");
                    ed3.setText("");

                }
            }
        });
    }


}

