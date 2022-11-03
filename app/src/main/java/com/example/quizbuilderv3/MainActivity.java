package com.example.quizbuilderv3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.Intent;

import com.example.quizbuilderv3.R.id;

public class MainActivity extends AppCompatActivity {

    //Creates the buttons
    TextView textViewWelcome , textViewName, editTextTextPersonName;

    Button btnStart;

    String Name = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link the Buttons
        textViewWelcome =findViewById(R.id.textViewWelcome);
        textViewName = findViewById(R.id.textViewName);
        editTextTextPersonName = findViewById(id.editTextTextPersonName);
        btnStart = findViewById(R.id.btnStartQuizz);

        //Activities
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = editTextTextPersonName.getText().toString();
                //Intent to Quiz Activity
                Intent i = new Intent(MainActivity.this, ActivityQuiz.class);
                Bundle extras = new Bundle();
                extras.putString("Name", Name);//"Key", "Information to send, like variables"
                i.putExtras(extras);
                startActivity(i,extras);
            }
        });
    }
}