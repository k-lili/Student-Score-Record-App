package com.example.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    private static String TAG = "DisplayActivity";

    private GridView gridView1;
    private ArrayList<String> scoreList;
    private ArrayAdapter<String> adapter;
    StudentScoreRecord scoreData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        gridView1 = findViewById(R.id.gridView1);
        scoreList = new ArrayList<String>();
        adapter = new ArrayAdapter<String> (getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, scoreList);

        scoreData = new StudentScoreRecord(this);

        displayDataInGrid();

    }

    private void displayDataInGrid() {
        //get context object from DB


        try{ //retrieve data from db
            Cursor cr = scoreData.getAllData();

            while (cr.moveToNext()) {
                String stu_name = cr.getString(1);
                String stu_id = cr.getString(2);
                String stu_score = cr.getString(3);

                //this is added individually
                scoreList.add(stu_name);
                scoreList.add(stu_id);
                scoreList.add(stu_score);
                gridView1.setAdapter(adapter);

                //what if there is deleted activity

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
        }

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int col_num = position%3; //0=name, 1=id, 2=score
                String data = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Column Number is: " + col_num + " ; " +
                        "Data is: "+ data, Toast.LENGTH_LONG).show(); //runned
                Log.d(TAG, "onItemClick: You clicked on " + data);

                Cursor dataID = scoreData.getDataID(col_num, data); //get _id from DB
                Log.d(TAG, "Return to Display Activity");

                int idGet = -1; //initialize the variable
                String select_name = "";
                String select_sid = "";
                String select_score ="";

                while(dataID.moveToNext()) { //?? to check if it works for last data
                    idGet = dataID.getInt(0);
                    select_name = dataID.getString(1);
                    Log.d(TAG, "in the loop");
                    select_sid = dataID.getString(2);
                    select_score = dataID.getString(3);
                }

                if (idGet > -1){ //when id is valid
                    //Toast.makeText(getApplicationContext(), "Item ID is: " + idGet, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Item ID is: " + idGet);

                    //go to editActivity
                    Intent editIntent = new Intent (DisplayActivity.this, EditActivity.class);
                    Log.d(TAG, "Intent created!");

                    editIntent.putExtra("record_id", idGet);
                    editIntent.putExtra("name",select_name);
                    editIntent.putExtra("sid", select_sid);
                    editIntent.putExtra("score", select_score);
                    Log.d(TAG, "putExtra done");
                    startActivity(editIntent);
                    Log.d(TAG, "Start Activity");
                }
                else{
                    Toast.makeText(getApplicationContext(), "ID not valid", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}