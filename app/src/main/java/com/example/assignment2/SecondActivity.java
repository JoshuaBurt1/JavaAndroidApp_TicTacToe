package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    TextView player;
    TextView wins;
    private int won=0;
    TextView losses;
    private int lost=0;
    TextView draws;
    private int draw=0;
    private boolean enabled = true;
    private String winner = " ";
    public char turn ;
    private char[] clickedArea = new char[9];
    private ArrayList<TextView> cells;
    TextView label;
    TextView c1r1,c1r2, c1r3,c2r1,c2r2,c2r3,c3r1,c3r2,c3r3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intObj = getIntent();
        String strData = intObj.getStringExtra("name");

        //player variables
        wins = findViewById(R.id.wins);
        losses = findViewById(R.id.losses);
        draws = findViewById(R.id.draws);
        player = findViewById(R.id.player);
        player.setText(strData);
        label = findViewById(R.id.label);

        //grid variables
        c1r1 = findViewById(R.id.c1r1);
        c1r2 = findViewById(R.id.c1r2);
        c1r3 = findViewById(R.id.c1r3);
        c2r1 = findViewById(R.id.c2r1);
        c2r2 = findViewById(R.id.c2r2);
        c2r3 = findViewById(R.id.c2r3);
        c3r1 = findViewById(R.id.c3r1);
        c3r2 = findViewById(R.id.c3r2);
        c3r3 = findViewById(R.id.c3r3);
        cells = new ArrayList<>(Arrays.asList(c1r1, c1r2, c1r3, c2r1, c2r2, c2r3, c3r1, c3r2, c3r3));

        turn = firstTurn();

            //Game loop
            for (int i = 0; i < cells.size(); i++) {
                TextView cell = cells.get(i);
                final int index = i;
                if(enabled) {
                    cell.setOnClickListener(view -> {
                        if (cell.getText().toString().isEmpty() && cell.getCompoundDrawables()[0] == null) {
                            if (turn == '1' && enabled) { //allows user to place drawable on grid
                                playerTurn(index, cell);
                            }
                            if (!checkWinner()) {
                                if (draw()) { //checks for draw
                                    enabled = false;
                                } else {
                                    computerTurn();
                                }
                            } else {
                                winOrLose();
                            }
                        } else {
                            label.setText("Occupied");
                        }
                    });
                }
            }
        }

    public char firstTurn() {
        Random initialTurn = new Random();
        int randomNumber = initialTurn.nextInt(2);
        if (randomNumber == 0) {
            label.setText("Computer turn");
            computerTurn();
            return '0';
        } else {
            label.setText("Player turn");
            return '1';
        }
    }
    public void reset(View view){
        clickedArea = new char[9];
        winner = " ";
        enabled = true;
        for (TextView cell: cells){
            cell.setText("");
            cell.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        turn = firstTurn();
    }
    public void playerTurn(int index, TextView cell){
        clickedArea[index] = turn; // this fills the array corresponding with grid line to detect win
        //cell.setText("1"); //use number instead of blue dot
        playerAnimation(cell);
        label.setText("Computer turn");
    }

    public void computerTurn() {
        new Handler().postDelayed(() -> {

            for (int i = 0; i < 3; i++) {
                // CHECK FOR WIN (3 0'S)
                // Check rows
                if (clickedArea[i * 3] == '0' && clickedArea[i * 3 + 1] == '0' && clickedArea[i * 3 + 2] == 0) {
                    clickedArea[i * 3 + 2] = '0';
                    updateCell(i * 3 + 2, '0');
                    return;
                }
                if (clickedArea[i * 3] == '0' && clickedArea[i * 3 + 2] == '0' && clickedArea[i * 3 + 1] == 0) {
                    clickedArea[i * 3 + 1] = '0';
                    updateCell(i * 3 + 1, '0');
                    return;
                }
                if (clickedArea[i * 3 + 1] == '0' && clickedArea[i * 3 + 2] == '0' && clickedArea[i * 3] == 0) {
                    clickedArea[i * 3] = '0';
                    updateCell(i * 3, '0');
                    return;
                }

                // Check columns
                if (clickedArea[i] == '0' && clickedArea[i + 3] == '0' && clickedArea[i + 6] == 0) {
                    clickedArea[i + 6] = '0';
                    updateCell(i + 6, '0');
                    return;
                }
                if (clickedArea[i] == '0' && clickedArea[i + 6] == '0' && clickedArea[i + 3] == 0) {
                    clickedArea[i + 3] = '0';
                    updateCell(i + 3, '0');
                    return;
                }
                if (clickedArea[i + 3] == '0' && clickedArea[i + 6] == '0' && clickedArea[i] == 0) {
                    clickedArea[i] = '0';
                    updateCell(i, '0');
                    return;
                }
            }
            // Check diagonals COMPUTER WIN
            if (clickedArea[0] == '0' && clickedArea[4] == '0' && clickedArea[8] == 0) {
                clickedArea[8] = '0';
                updateCell(8, '0');
                return;
            }
            if (clickedArea[0] == '0' && clickedArea[8] == '0' && clickedArea[4] == 0) {
                clickedArea[4] = '0';
                updateCell(4, '0');
                return;
            }
            if (clickedArea[4] == '0' && clickedArea[8] == '0' && clickedArea[0] == 0) {
                clickedArea[0] = '0';
                updateCell(0, '0');
                return;
            }
            if (clickedArea[2] == '0' && clickedArea[4] == '0' && clickedArea[6] == 0) {
                clickedArea[6] = '0';
                updateCell(6, '0');
                return;
            }
            if (clickedArea[2] == '0' && clickedArea[6] == '0' && clickedArea[4] == 0) {
                clickedArea[4] = '0';
                updateCell(4, '0');
                return;
            }
            if (clickedArea[4] == '0' && clickedArea[6] == '0' && clickedArea[2] == 0) {
                clickedArea[2] = '0';
                updateCell(2, '0');
                return;
            }
            for (int i = 0; i < 3; i++) {
                //BLOCK PLAYER
                if (clickedArea[i * 3] == '1' && clickedArea[i * 3 + 1] == '1' && clickedArea[i * 3 + 2] == 0) {
                    clickedArea[i * 3 + 2] = '0';
                    updateCell(i * 3 + 2, '0');
                    return;
                }
                if (clickedArea[i * 3] == '1' && clickedArea[i * 3 + 2] == '1' && clickedArea[i * 3 + 1] == 0) {
                    clickedArea[i * 3 + 1] = '0';
                    updateCell(i * 3 + 1, '0');
                    return;
                }
                if (clickedArea[i * 3 + 1] == '1' && clickedArea[i * 3 + 2] == '1' && clickedArea[i * 3] == 0) {
                    clickedArea[i * 3] = '0';
                    updateCell(i * 3, '0');
                    return;
                }

                // Check columns
                if (clickedArea[i] == '1' && clickedArea[i + 3] == '1' && clickedArea[i + 6] == 0) {
                    clickedArea[i + 6] = '0';
                    updateCell(i + 6, '0');
                    return;
                }
                if (clickedArea[i] == '1' && clickedArea[i + 6] == '1' && clickedArea[i + 3] == 0) {
                    clickedArea[i + 3] = '0';
                    updateCell(i + 3, '0');
                    return;
                }
                if (clickedArea[i + 3] == '1' && clickedArea[i + 6] == '1' && clickedArea[i] == 0) {
                    clickedArea[i] = '0';
                    updateCell(i, '0');
                    return;
                }
            }
            // Check diagonals BLOCK PLAYER
            if (clickedArea[0] == '1' && clickedArea[4] == '1' && clickedArea[8] == 0) {
                clickedArea[8] = '0';
                updateCell(8, '0');
                return;
            }
            if (clickedArea[0] == '1' && clickedArea[8] == '1' && clickedArea[4] == 0) {
                clickedArea[4] = '0';
                updateCell(4, '0');
                return;
            }
            if (clickedArea[4] == '1' && clickedArea[8] == '1' && clickedArea[0] == 0) {
                clickedArea[0] = '0';
                updateCell(0, '0');
                return;
            }
            if (clickedArea[2] == '1' && clickedArea[4] == '1' && clickedArea[6] == 0) {
                clickedArea[6] = '0';
                updateCell(6, '0');
                return;
            }
            if (clickedArea[2] == '1' && clickedArea[6] == '1' && clickedArea[4] == 0) {
                clickedArea[4] = '0';
                updateCell(4, '0');
                return;
            }
            if (clickedArea[4] == '1' && clickedArea[6] == '1' && clickedArea[2] == 0) {
                clickedArea[2] = '0';
                updateCell(2, '0');
                return;
            }
            //THIS AREA WOULD BE WIN STRATEGIES
            //OR SELECT RANDOM CELL
            int[] emptyCell = getRandomEmptyCell();
            int rowIndex = emptyCell[0];
            int colIndex = emptyCell[1];
            clickedArea[rowIndex * 3 + colIndex] = '0';
            TextView computerTextView = cells.get(rowIndex * 3 + colIndex);
            //computerTextView.setText("0"); //use number instead of red dot
            computerAnimation(computerTextView);
            winOrLose();
            if (!checkWinner()) {
                turn = '1'; //player turn
                label.setText("Player turn");
                if (draw()) {
                    enabled = false;
                }
            }
        }, 400);
        turn = '1';
    }

    public void updateCell(int cell, char val){
        /*if (cell < 0 || cell >= cells.size()) {
            Log.e("updateCell", "Invalid cell index: " + cell);
            return;
        }*/
        TextView computerTextView = cells.get(cell);
        //computerTextView.setText(String.valueOf(val)); //use number instead of red dot
        computerAnimation(computerTextView);
        winOrLose();
        if (!checkWinner()) {
            turn = '1'; //player turn
            label.setText("Player turn");
            if (draw()) {
                enabled = false;
            }
        }
    }

    public void computerAnimation(TextView computerTextView){
        Drawable drawable = getResources().getDrawable(R.drawable.red);
        computerTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500); // Duration in milliseconds
        computerTextView.setAnimation(scaleAnimation);
    }

    public void playerAnimation(TextView computerTextView){
        Drawable drawable = getResources().getDrawable(R.drawable.blue);
        computerTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500); // Duration in milliseconds
        computerTextView.setAnimation(scaleAnimation);
    }

    private boolean draw(){
            for (char a : clickedArea){
                if (a == 0){
                    return false;
                }
            }
            label.setText("It's a draw!");
            draw++;
            draws.setText("Draws: " + draw);
            return true;
        }

        public int[] getRandomEmptyCell() {
        Random random = new Random();
        int[] cell = new int[2];
        do {
            cell[0] = random.nextInt(3);
            cell[1] = random.nextInt(3);
        } while (clickedArea[cell[0] * 3 + cell[1]] != 0);

        return cell;
    }

    public boolean checkWinner() {
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            if (clickedArea[i * 3] != 0 && clickedArea[i * 3] == clickedArea[i * 3 + 1] && clickedArea[i * 3] == clickedArea[i * 3 + 2]) {
                win = true;
                winner = String.valueOf(clickedArea[i * 3]);
                break;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (clickedArea[i] != 0 && clickedArea[i] == clickedArea[i + 3] && clickedArea[i] == clickedArea[i + 6]) {
                win = true;
                winner = String.valueOf(clickedArea[i]);
                break;
            }
        }
        if (clickedArea[0] != 0 && clickedArea[0] == clickedArea[4] && clickedArea[0] == clickedArea[8]) {
            win = true;
            winner = String.valueOf(clickedArea[0]);
        } else if (clickedArea[2] != 0 && clickedArea[2] == clickedArea[4] && clickedArea[2] == clickedArea[6]) {
            win = true;
            winner = String.valueOf(clickedArea[2]);
        }
        return win;
    }
    public void winOrLose(){
        if (checkWinner()) { //check for winner if 1,1,1 or 0,0,0 grid line present
            if (winner.equals("1")) {
                label.setText("Player 1 wins!");
                won++;
                wins.setText("Wins: " + won);
                enabled = false;
            } else if (winner.equals("0")) {
                label.setText("Computer wins!");
                lost++;
                losses.setText("Losses: " + lost);
                enabled = false;
            }
        }
    }

    public void home(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}