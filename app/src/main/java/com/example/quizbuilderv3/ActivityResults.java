package com.example.quizbuilderv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityResults extends AppCompatActivity {

    //Creates the buttons
    TextView textViewResult;
    Button btnRestart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Link the Buttons
        btnRestart = findViewById(R.id.btnRestart);

        btnRestart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent to Main Activity
                Intent i = new Intent(ActivityResults.this, MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("Name", "Gabriel");//"Key", "Information to send, like variables"
                i.putExtras(extras);
                startActivity(i,extras);
            }
        });

        //Bundle to get content from previous page
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String s = extras.getString("Key");
            int i = extras.getInt("Score");
            textViewResult.setText(i);
        }
    }

}