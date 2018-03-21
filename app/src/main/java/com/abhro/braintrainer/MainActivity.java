package com.abhro.braintrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgain;
    TextView sumTextView;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;
    TextView highScoreView;
    RelativeLayout relativeLay;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAns;
    int score=0;
    int numques=0;
    int highScore=0;
    SharedPreferences sharedPreferences;

    public void playAgain(View view){

        score=0;
        numques=0;
        resultTextView.setText("");
        pointsTextView.setText("0/0");
        timerTextView.setText("30s");
        button0.setClickable(true);
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);
        generateQues();
        playAgain.setVisibility(View.INVISIBLE);
        highScoreView.setVisibility(TextView.INVISIBLE);
        new CountDownTimer(30180,1000){
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l/1000)+"s");
            }

            @Override
            public void onFinish() {
                if((numques-score)<3 && numques>20) {
                    resultTextView.setText("well done!! "+Integer.toString(score) + "/" + Integer.toString(numques));

                }
                else if((numques-score)<6 && numques>10){
                    resultTextView.setText("Could be better!! "+Integer.toString(score) + "/" + Integer.toString(numques));

                }
                else if(numques<9){
                    resultTextView.setText("Try to attempt more "+Integer.toString(score) + "/" + Integer.toString(numques));

                }
                else{
                    resultTextView.setText(Integer.toString(score) + "/" + Integer.toString(numques));
                }
                if(highScore<score){
                    highScore=score;
                    sharedPreferences.edit().putInt("highScore",highScore).apply();

                }
                timerTextView.setText("0s");
                playAgain.setVisibility(View.VISIBLE);
                highScoreView.setVisibility(TextView.VISIBLE);
                button0.setClickable(false);
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);
                highScoreView.setText("High Score: "+ Integer.toString(highScore));
            }
        }.start();

    }

    public void generateQues(){

        int incorrectAns;
        Random rand= new Random();
        int a=rand.nextInt(21)+1;
        int b=rand.nextInt(41)+1;

        sumTextView.setText(Integer.toString(a)+" + "+Integer.toString(b));

        locationOfCorrectAns=rand.nextInt(4);
        answers.clear();

        for(int i=0; i<4;i++){

            incorrectAns=rand.nextInt(61);
            if(i==locationOfCorrectAns){
                answers.add(a+b);
            }
            else{
                while(incorrectAns==(a+b)){
                    incorrectAns=rand.nextInt(61);
                }
                answers.add(incorrectAns);
            }

        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }


    public void chooseAnswer(View view){


        if((view.getTag().toString()).equals((Integer.toString(locationOfCorrectAns)))){
            resultTextView.setText("Correct!");
            score++;
        }
        else {
            resultTextView.setText("Wrong!");
        }
        numques++;
        pointsTextView.setText(Integer.toString(score)+"/"+Integer.toString(numques));
        generateQues();
    }


    public void start(View view){
        button.setVisibility(View.INVISIBLE);
        relativeLay.setVisibility(RelativeLayout.VISIBLE);
        playAgain(findViewById(R.id.playAgain));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button=(Button)findViewById(R.id.startButton);
        button0=(Button)findViewById(R.id.button0);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        playAgain=(Button)findViewById(R.id.playAgain);
        sumTextView=(TextView)findViewById(R.id.sumTextView);
        resultTextView=(TextView)findViewById(R.id.resultTextView);
        pointsTextView=(TextView)findViewById(R.id.pointsTextView);
        timerTextView=(TextView)findViewById(R.id.timerTextView);
        highScoreView=(TextView)findViewById(R.id.highScoreView);
        relativeLay=(RelativeLayout)findViewById(R.id.relativeLay);
        playAgain.setVisibility(Button.INVISIBLE);

        sharedPreferences = this.getSharedPreferences("com.abhro.braintrainer", Context.MODE_PRIVATE);

        highScore = sharedPreferences.getInt("highScore",0);


    }
}
