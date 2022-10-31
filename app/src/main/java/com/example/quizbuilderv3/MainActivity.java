package com.example.quizbuilderv3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    //Creates the buttons
    TextView textViewWelcome , textViewName;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link the Buttons
        textViewWelcome =findViewById(R.id.textViewWelcome);
        textViewName = findViewById(R.id.textViewName);
        btnStart = findViewById(R.id.btnStartQuizz);

        //Activities
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent to Quiz Activity
                Intent i = new Intent(MainActivity.this, ActivityQuiz.class);
                Bundle extras = new Bundle();
                extras.putString("Name", "Gabriel");//"Key", "Information to send, like variables"
                i.putExtras(extras);
                startActivity(i,extras);
            }
        });
    }
}