package com.example.sunny.gameconnect3;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    int counter = 0;
    int[][] matBoard = new int[3][3];



    public void playAgain(View view){
        clearFunc();
    }

    public void clearFunc(){
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout1);
        ll.removeAllViews();
        ll = (LinearLayout) findViewById(R.id.linearLayout2);
        ll.removeAllViews();
        ll = (LinearLayout) findViewById(R.id.LinearLayout3);
        ll.removeAllViews();
        ll = (LinearLayout) findViewById(R.id.LinearLayout4);
        ll.removeAllViews();
        ll = (LinearLayout) findViewById(R.id.LinearLayout5);
        ll.removeAllViews();
        ll = (LinearLayout) findViewById(R.id.LinearLayout6);
        ll.removeAllViews();
        ll = (LinearLayout) findViewById(R.id.LinearLayout7);
        ll.removeAllViews();
        ll = (LinearLayout) findViewById(R.id.LinearLayout8);
        ll.removeAllViews();
        ll = (LinearLayout) findViewById(R.id.LinearLayout9);
        ll.removeAllViews();

        ll = (LinearLayout)findViewById(R.id.LinearLayout10);
        ll.setVisibility(View.INVISIBLE);
        counter = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                matBoard[i][j] = 0;
            }
        }
       // Toast.makeText(this, "Game over my friend!!!!!!", Toast.LENGTH_SHORT).show();
    }

    public boolean linearCheck(int row,int col,int insertedNumber){
        boolean gameStatus = false;
        if(matBoard[row][col]==insertedNumber && matBoard[(row+1)%3][col]==insertedNumber && matBoard[(row+2)%3][col]==insertedNumber){
            gameStatus = true;
        }
        if(matBoard[row][col]==insertedNumber && matBoard[row][(col+1)%3]==insertedNumber && matBoard[row][(col+2)%3]==insertedNumber){
            gameStatus = true;
        }
        return gameStatus;
    }

    public boolean diagonalCheckPos(int row,int col,int insertedNumber){
        boolean gameStatus = false;
        if(matBoard[row][col]==insertedNumber && matBoard[(row+1)%3][(col+1)%3]==insertedNumber && matBoard[(row+2)%3][(col+2)%3]==insertedNumber){
            gameStatus = true;
        }
        return gameStatus;
    }

    public boolean diagonalCheckNeg(int row,int col,int insertedNumber){
        boolean gameStatus = false;
        if(matBoard[row][col]==insertedNumber && matBoard[(row+1)%3][(col+2)%3]==insertedNumber && matBoard[(row+2)%3][(col+1)%3]==insertedNumber){
            gameStatus = true;
        }
        return gameStatus;
    }

    public boolean checkFunc(int row,int col,int insertedNumber){
        if((row==1 || col==1) && !(row==1 && col==1)){
            //check in 2 directions.
            return linearCheck(row,col,insertedNumber);
        }
        else if((row==0 && col==0)||(row==2 && col==2)){
            return linearCheck(row, col, insertedNumber) | diagonalCheckPos(row, col, insertedNumber);
        }
        else if((row==0 && col==2)||(row==2 && col==0)){
            return linearCheck(row, col, insertedNumber) | diagonalCheckNeg(row, col, insertedNumber);
        }
        else{
            Log.i("special case ","I'm here");
            return linearCheck(row, col, insertedNumber) | diagonalCheckNeg(row, col, insertedNumber) | diagonalCheckPos(row, col, insertedNumber);
        }
    }

    public void fun1(View view){
        String viewName = view.getResources().getResourceEntryName(view.getId());
        int number = Character.getNumericValue(viewName.charAt(viewName.length()-1));
        Log.i("Info", "layout that is pressed is " + viewName);
        Log.i("numnber","number is "+number);
        if(findViewById(number)==null) {
            ImageView img = new ImageView(this);
            img.setId(number);

            int m = (number%3+2)%3;
            int n = (number - m - 1)/3;
            Log.i("indices","indices are "+n+" "+m);
            int insertedNumber = 0;
            if(counter%2==0) {
                img.setImageResource(R.drawable.red);
                matBoard[n][m] = 1;
                insertedNumber = 1;
            }
            else{
                img.setImageResource(R.drawable.yellow);
                matBoard[n][m] = 2;
                insertedNumber = 2;
            }
            LinearLayout l1 = (LinearLayout) findViewById(view.getId());
            l1.setGravity(Gravity.CENTER);
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(img, "scaleX", 0.9f);
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(img, "scaleY", 0.9f);
            scaleDownX.setDuration(0);
            scaleDownY.setDuration(0);
            AnimatorSet scaleDown = new AnimatorSet();
            scaleDown.play(scaleDownX).with(scaleDownY);

            scaleDown.start();
            img.animate().rotationBy(180f).setDuration(100);
            l1.addView(img);


            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    System.out.print(matBoard[i][j] + " ");
                }
                System.out.println();
            }
            boolean gameStatus = checkFunc(n,m,insertedNumber);
            if(gameStatus==true){

                LinearLayout l2 = (LinearLayout) findViewById(R.id.LinearLayout10);
                TextView t2 = (TextView) findViewById(R.id.winnerMsg);
                l2.setVisibility(View.VISIBLE);
                if(counter%2==0){
                    t2.setText("Red Won");
                }
                else{
                    t2.setText("Yellow Won");
                }
                return;

            }
            if(counter==8){
                LinearLayout l2 = (LinearLayout) findViewById(R.id.LinearLayout10);
                TextView t2 = (TextView) findViewById(R.id.winnerMsg);
                l2.setVisibility(View.VISIBLE);
                t2.setText("Draw!!!!");
                Log.i("Game over","all the places are over");
            }
            counter++;
            return;
        }
        else{
            Log.i("Repeat", "image already there");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                matBoard[i][j] = 0;
            }
        }
    }
}
