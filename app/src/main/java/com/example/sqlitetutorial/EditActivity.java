package com.example.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private static String TAG = "EditActivity";

    EditText etStuName, etStuID, etStuScore;
    StudentScoreRecord scoreData;

    private int record_id;
    private String edit_name, edit_sid, edit_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etStuName = findViewById(R.id.etStuName);
        etStuID = findViewById(R.id.etStuID);
        etStuScore = findViewById(R.id.etStuScore);
        scoreData = new StudentScoreRecord(this);

        //get the selected data to edit
        Intent dataToEdit = getIntent();
        record_id = dataToEdit.getIntExtra("record_id",-1);
        Log.i(TAG, "Record_id = " + record_id);
        edit_name = dataToEdit.getStringExtra("name");
        edit_sid = dataToEdit.getStringExtra("sid");
        edit_score = dataToEdit.getStringExtra("score");

        etStuName.setText(edit_name);
        etStuID.setText(edit_sid);
        etStuScore.setText(edit_score);

    }

    public void editScore(View view) {
        Log.i(TAG, "Enter editScore()");
        String newName = etStuName.getText().toString();
        String newSID = etStuID.getText().toString();
        String newScore = etStuScore.getText().toString();
        Log.i(TAG, "New Data stored");

        if (!newName.equals("") && !newSID.equals("") && !newScore.equals("")){
            Log.i(TAG, "Data all filled");
            //pass data to updateData()
            scoreData.updateData(record_id,newName, newSID, newScore, edit_name, edit_sid, edit_score);
            etStuName.setText("");
            etStuID.setText("");
            etStuScore.setText("");
            Log.i(TAG, "All text set!");
        }
        else{
            Toast.makeText(getBaseContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteScore(View view) {
        scoreData.deleteData(record_id, edit_name, edit_sid, edit_score);
        etStuName.setText("");
        etStuID.setText("");
        etStuScore.setText("");
     }

    public void displayAllScore(View view) {
        //go to DisplayAllScore activity
        Intent intent = new Intent(EditActivity.this, DisplayActivity.class);
        startActivity(intent);
    }
}