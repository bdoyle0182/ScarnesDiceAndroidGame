package com.example.brendan.scarnesdice;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import java.util.Random;
import android.os.Handler;
import static java.lang.Thread.sleep;

public class DiceActivity extends AppCompatActivity implements View.OnClickListener {

    public int currentTurnScore = 0;
    public int computerTurnScore = 0;
    public int totalScore = 0;
    public int computerTotalScore = 0;
    private Random random = new Random();
    public ImageView diceImage;
    public boolean computerTurn = false;
    public Button rollButton;
    public Button holdButton;
    public Button resetButton;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {

            if ((computerTurnScore < 20) && computerTurn) {

                roll();
                timerHandler.postDelayed(this, 2000);
            }

            if (computerTurnScore >= 20) {
                hold();
            }



        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        //default dice image
        diceImage = (ImageView) findViewById(R.id.diceImage);
        diceImage.setImageResource(R.drawable.dice1);

        //Initialize Buttons
        rollButton = (Button) findViewById(R.id.rollButton);
        holdButton = (Button) findViewById(R.id.holdButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        rollButton.setOnClickListener(DiceActivity.this);
        holdButton.setOnClickListener(DiceActivity.this);
        resetButton.setOnClickListener(DiceActivity.this);

    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rollButton:
                roll();
                break;

            case R.id.holdButton:
                hold();
                break;

            case R.id.resetButton:
                reset();
                break;

            default:
                break;
        }

    }

    private void updateGameStatus() {
        TextView gameStatus = (TextView) findViewById(R.id.game_status);

        String status = "Game Score: " + totalScore + " Computer Score: " + computerTotalScore;

        gameStatus.setText(status);
    }

    public void roll() {
        Integer randomInt = random.nextInt(6);
        Log.d("In roll:", "hi");
        if(randomInt == 0) {
            diceImage.setImageResource(R.drawable.dice1);
            if(!computerTurn) {
                Log.d("Lost Turn", ":(");
                currentTurnScore = 0;
                holdButton.setEnabled(false);
                rollButton.setEnabled(false);
                startComputerTurn();
                //computerTurn();
            }
            else {
                computerTurnScore = 0;
                holdButton.setEnabled(true);
                rollButton.setEnabled(true);
                computerTurn = false;
            }
        }
        if(randomInt == 1) {
            diceImage.setImageResource(R.drawable.dice2);
            if(!computerTurn)
                currentTurnScore += 2;
            else
                computerTurnScore += 2;
        }
        if(randomInt == 2) {
            diceImage.setImageResource(R.drawable.dice3);
            if(!computerTurn)
                currentTurnScore += 3;
            else
                computerTurnScore += 3;
        }
        if(randomInt == 3) {
            diceImage.setImageResource(R.drawable.dice4);
            if(!computerTurn)
                currentTurnScore += 4;
            else
                computerTurnScore += 4;
        }
        if(randomInt == 4) {
            diceImage.setImageResource(R.drawable.dice5);
            if(!computerTurn)
                currentTurnScore += 5;
            else
                computerTurnScore += 5;
        }
        if(randomInt == 5) {
            diceImage.setImageResource(R.drawable.dice6);
            if(!computerTurn)
                currentTurnScore += 6;
            else
                computerTurnScore += 6;
        }
        String current = "Current Score: " + currentTurnScore;
        TextView currentScore = (TextView) findViewById(R.id.currentScore);
        currentScore.setText(current);
    }

    public void hold() {

        if(!computerTurn) {
            totalScore += currentTurnScore;
            currentTurnScore = 0;
            updateGameStatus();
            checkGameOver();
            holdButton.setEnabled(false);
            rollButton.setEnabled(false);
            //computerTurn();
            startComputerTurn();
        }
        else {
            computerTotalScore += computerTurnScore;
            computerTurnScore = 0;
            updateGameStatus();
            checkGameOver();
            holdButton.setEnabled(true);
            rollButton.setEnabled(true);
            computerTurn = false;
        }


    }

    public void reset() {
        currentTurnScore = 0;
        computerTurnScore = 0;
        totalScore = 0;
        computerTotalScore = 0;
        updateGameStatus();
        computerTurn = false;
        TextView winnerText = (TextView) findViewById(R.id.winnerText);
        winnerText.setText("");

        holdButton.setEnabled(true);
        rollButton.setEnabled(true);
    }

    public void startComputerTurn() {
        computerTurn = true;
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void computerTurn() {
        //computerTurn = true;

        /*while ((computerTurnScore < 20) && computerTurn) {


            roll();
        }

        if (computerTurn) {
            hold();
        }*/


    }

    public void checkGameOver() {
        TextView winnerText = (TextView) findViewById(R.id.winnerText);
        if(totalScore >= 100) {
            holdButton.setEnabled(false);
            rollButton.setEnabled(false);
            winnerText.setText("You Win!");
        }

        if(computerTotalScore >= 100) {
            holdButton.setEnabled(false);
            rollButton.setEnabled(false);
            winnerText.setText("You Lose!");
        }
    }
}
