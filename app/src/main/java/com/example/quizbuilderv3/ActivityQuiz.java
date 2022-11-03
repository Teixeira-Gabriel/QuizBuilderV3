package com.example.quizbuilderv3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActivityQuiz extends AppCompatActivity implements View.OnClickListener
{
    //Buttons and Text Views
    TextView textViewCount , textViewQuestions;
    Button btnChoice1, btnChoice2, btnChoice3, btnChoice4, btnNext;

    //Array Lists
    ArrayList<String> keyA = new ArrayList<String>();
    ArrayList<String> valueA = new ArrayList<String>();
    ArrayList<String> valueAClone = new ArrayList<String>();

    //Hash map
    Map<String,String> map = new HashMap<String,String>();//create map

    //variables
    int score = 0;
    int totalQuestions = 10;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    String question = "";
    String s = "";

    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Link the Buttons
        textViewCount = findViewById(R.id.textViewCounts);
        textViewQuestions = findViewById(R.id.textViewQuestion);
        btnChoice1 = findViewById(R.id.btnChoice1);
        btnChoice2 = findViewById(R.id.btnChoice2);
        btnChoice3 = findViewById(R.id.btnChoice3);
        btnChoice4 = findViewById(R.id.btnChoice4);
        btnNext = findViewById(R.id.btnNext);

        //Listeners
        btnChoice1.setOnClickListener(this);
        btnChoice2.setOnClickListener(this);
        btnChoice3.setOnClickListener(this);
        btnChoice4.setOnClickListener(this);
        btnNext.setOnClickListener(this);


        //Bundle to get content from previous page
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            s = extras.getString("Name");
//            textViewResult.setText(s);
        }

        //File i/o
        String str = null;
        BufferedReader br = null;

        try
        {
            InputStream is = getResources().openRawResource(R.raw.quiz);
            br = new BufferedReader(new InputStreamReader(is));
            System.out.println("File in RAW is open");

            int i = 1;
            while ((str = br.readLine()) != null)
            {
//                System.out.println(str);//just for testing read line

                //Splitting the string
                for (String actualElement : str.split(":") )
                {
                    //puting the string into the arrays
                    String i1 = new String(actualElement);

                    if ( i % 2 == 0)
                    {
                        valueA.add(i1);
                    }
                    else
                    {
                        keyA.add(i1);
                    }
                    i++;
                }

            }
//            System.out.println("The content of the Key Arraylist is: " + keyA);
//            System.out.println("The content of the Valule Arraylist is: " + valueA);

            //Putting array into hash map
            for (int j = 0; j <= 9; j++)
            {
                String arrayIndexKey = keyA.get(j);
                String arrayIndexValue = valueA.get(j);
                map.put(arrayIndexKey, arrayIndexValue);//map.put("Key", "Value");
            }
            System.out.println("Map: "+map);

            //File Closing
            is.close();
            System.out.println("File in RAW is closed");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        int totalQuestions = keyA.size();

        //Adding more elements to the value array.
//        valueA.add("Answer 11.");
//        valueA.add("Answer 12.");
//        valueA.add("Answer 13.");
//        valueA.add("Answer 14.");

        //Shuffling the arrays
        Collections.shuffle(keyA);

        //Setting the buttons for the first time//
        textViewCount.setText(currentQuestionIndex + "/" + totalQuestions);
        loadNewQuestion();

    }//End OnCreate

    //On Click Method
    @Override
    public void onClick(View view)
    {
        //starting the colors
        btnChoice1.setBackgroundColor(Color.WHITE);
        btnChoice2.setBackgroundColor(Color.WHITE);
        btnChoice3.setBackgroundColor(Color.WHITE);
        btnChoice4.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.btnNext)
        {
            if(selectedAnswer.equals(map.get(question)))
            {
                score++;
            }
            currentQuestionIndex++;
            textViewCount.setText(currentQuestionIndex + "/" + totalQuestions);
            loadNewQuestion();
        }
        else
        {
            //choices button clicked
            selectedAnswer  = clickedButton.getText().toString();
            question = textViewQuestions.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }

    } //End On Click Method

    //FUNCTIONS//

    //Load new Question
    void loadNewQuestion()
    {
        if(currentQuestionIndex == totalQuestions )
        {
            finishQuiz();
            return;
        }

        //Setting the Question
        String arrayIndexKey = keyA.get(currentQuestionIndex);
        textViewQuestions.setText(arrayIndexKey);

        //Getting the right answer
        String rightChoice = map.get(arrayIndexKey);

        //Cloning the Value Array to get answers bank
        valueAClone = (ArrayList)valueA.clone();

        //Removing for no duplicates and shuffling
        valueAClone.remove(rightChoice);
        Collections.shuffle(valueAClone);

        //Getting wrong choices
        String wrongChoice1 = valueAClone.get(0);
        String wrongChoice2 = valueAClone.get(1);
        String wrongChoice3 = valueAClone.get(2);

        //getting a random number
        int randomNumber = getRandomNumber(1,4);

        //Setting the buttons
        if (randomNumber == 1)
        {
            btnChoice1.setText(rightChoice);
            btnChoice2.setText(wrongChoice1);
            btnChoice3.setText(wrongChoice2);
            btnChoice4.setText(wrongChoice3);
        }
        else if (randomNumber == 2)
        {
            btnChoice1.setText(wrongChoice1);
            btnChoice2.setText(rightChoice);
            btnChoice3.setText(wrongChoice2);
            btnChoice4.setText(wrongChoice3);
        }
        else if (randomNumber == 3)
        {
            btnChoice1.setText(wrongChoice1);
            btnChoice2.setText(wrongChoice2);
            btnChoice3.setText(rightChoice);
            btnChoice4.setText(wrongChoice3);
        }
        else if (randomNumber == 4)
        {
            btnChoice1.setText(wrongChoice1);
            btnChoice2.setText(wrongChoice2);
            btnChoice3.setText(wrongChoice3);
            btnChoice4.setText(rightChoice);
        }
    } //END Load new Question

    //Finish Quiz
    void finishQuiz()
    {
        String passStatus = "";
        if(score > totalQuestions*0.60){
            passStatus = "Passed";
        }else
        {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(s + " Score is "+ score+" / "+ totalQuestions)
                .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();

    } // END Finish Quiz

    // Restart Quiz
    void restartQuiz()
    {
        //    score = 0;
        currentQuestionIndex =0;
//        loadNewQuestion(); // Change to send to first view
//        //Intent to Main Activity
//        Intent i = new Intent(ActivityQuiz.this, ActivityResults.class);
//        Bundle extras = new Bundle();
//        //extras.putString("Name", "Gabriel");//"Key", "Information to send, like variables"
//        extras.putInt("Score", score);
//        i.putExtras(extras);
//        startActivity(i,extras);
    } // END Restart Quiz

    //Function to get a random number
    public int getRandomNumber (int min, int max)
    {
        return (int) ((Math.random() * (max - min))+min);
    };

}//END main



