package com.example.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etStuName, etStuID, etStuScore;
    StudentScoreRecord scoreData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStuName = findViewById(R.id.etStuName);
        etStuID = findViewById(R.id.etStuID);
        etStuScore = findViewById(R.id.etStuScore);

        scoreData = new StudentScoreRecord(this);
    }

    //onClick ADD
    public void addScore(View view) {

        String inputName = etStuName.getText().toString();
        String inputSID = etStuID.getText().toString();
        String inputScore = etStuScore.getText().toString();

        if (!inputName.equals("") && !inputSID.equals("") && !inputScore.equals("")){
            //pass data to insertData()
            scoreData.insertData(inputName, inputSID, inputScore);
            etStuName.setText("");
            etStuID.setText("");
            etStuScore.setText("");
        }
        else{
            Toast.makeText(getBaseContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
        }

    }

    //onClick DISPLAY
    public void displayAllScore(View view) {

        //go to DisplayAllScore activity
        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        startActivity(intent);

    }

}