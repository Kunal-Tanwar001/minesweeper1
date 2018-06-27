package com.example.prate.minestonesample;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    public  int row=7;
    public  int col=7;
    ArrayList<LinearLayout> linearLayouts;

    boolean currentstatus=true;
    Mbutton[][] board;
    LinearLayout rootLayout;

    public final static int mine=-1;
    int num_of_mines=14;
    int x[]={-1,-1,-1,0,1,1,1,0};
    int y[]={-1,0,1,1,1,0,-1,-1};


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent ilo=getIntent();
//        String s=ilo.getStringExtra("XY");
//        switch (s) {
//            case "Easy":
//                row = 7;
//                col = 7;
//
//                break;
//            case "Medium":
//                row = 6;
//                col = 6;
//                break;
//            default:
//                row = 5;
//                col = 5;
//                break;
//        }
        rootLayout=findViewById(R.id.root);

        setup();
        setmines();
      // show();



    }
//    private void show() {
//        for(int i=0;i<row;i++){
//            for(int j = 0; j<col; j++){
//                board[i][j].setText(board[i][j].value+"");
//            }
//        }
//    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setup(){
        currentstatus=true;
        rootLayout.removeAllViews();
        board=new Mbutton[row][col];
        linearLayouts=new ArrayList<>();

        for(int i=0;i<row;i++){
            LinearLayout l=new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
            l.setLayoutParams(layoutParams);
            rootLayout.addView(l);
            linearLayouts.add(l);
        }
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                Mbutton button =new Mbutton(this);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(params);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
                LinearLayout rows=linearLayouts.get(i);
                rows.addView(button);

                button.i=i;
                button.j=j;
                button.setValue(0);
                button.revealed=false;
                board[i][j]=button;
              //  button.setBackgroundColor(getResources().getColor(R.color.dargreen));
                button.setBackground(getDrawable(R.drawable.button));
            }
        }

    }

//    public void setBoard() {
//        for (int i = 0; i < row; i++) {
//            for(int j=0;j<col;j++){
//
//                int count=0;
//                if (board[i][j].getValue() != mine) {
//
//                    for(int k=0;k<8;k++){
//                        int v=i+x[k];
//                        int z=i+y[k];
//                        if(v>=0 && v<row && z>=0 && z<col){
//                            if(board[v][z].getValue()==mine)
//                                count++;
//                        }
//                    }
//                }
//
//                board[i][j].setValue(count);
//            }
//        }
  public void setmines(){
        Random rand=new Random();
        int count = 0;
        while(count <= num_of_mines){
            int x,y;
            x = rand.nextInt(row);
            y = rand.nextInt(col);
            if(board[x][y].value != mine){
                board[x][y].value = mine;
                count++;
                setupNeighbour(board[x][y]);
            }
        }

    }

    public void setupNeighbour(Mbutton button){
        int i = button.i;
        int j = button.j;
        int xi,yj;
        for(int k = 0; k<8 ; k++){
            xi = i + x[k];
            yj = j + y[k];
            if(xi < row && yj < col && xi >= 0 && yj >= 0 && board[xi][yj].value != mine){
                board[xi][yj].value++;
            }
        }
    }


    @SuppressLint({"NewApi", "SetTextI18n"})
    public void onClick(View view) {

        Mbutton button =(Mbutton)view;
        if(currentstatus) {


            if (button.getValue() == mine){
                Toast.makeText(this,"you loose",Toast.LENGTH_LONG).show();
                currentstatus=false;

                revealAllmines();
                return;
            }
            if(button.getText().equals("F")){
                return;
            }
            if(button.getValue()==0){
              // button.setBackgroundColor(getResources().getColor(R.color.white));
                button.setBackground(getDrawable(R.drawable.whitebutton));
                button.revealed=true;
                reveal(button.i,button.j);
            }
            else{
                button.setText(""+button.getValue());
               // button.setBackgroundColor(getResources().getColor(R.color.dargreen));
                button.setTextSize(22);
               button.setBackground(getDrawable(R.drawable.button));
            }




        }// checkgame will tell whether game is over or not true means game is on.
        if(!checkgame()){
            Toast.makeText(this, "YOU won the match", Toast.LENGTH_SHORT).show();
            currentstatus=false;

        }
    }


    @SuppressLint({"SetTextI18n", "ResourceAsColor", "NewApi"})
    public void revealAllmines(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(!board[i][j].revealed){
                    if(board[i][j].getValue()==mine) {
                        // board[i][j].setText("*");  //////
                        board[i][j].setBackground(getDrawable(R.drawable.mine_1));
                    }
                    else {
                        if(board[i][j].getValue()==0 ){
                            board[i][j].setBackground(getDrawable(R.drawable.whitebutton));

                    }
                           else
                        board[i][j].setText(board[i][j].getValue() + "");
                        board[i][j].setTextSize(18);
                        board[i][j].setBackground(getDrawable(R.drawable.button));

                    }
                }
            }
        }
    }
    public boolean checkgame(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if( board[i][j].getValue()==mine && board[i][j].revealed){
                    return false;
                }

            }
        }
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"SetTextI18n", "NewApi"})
    public void reveal(int i, int j){
        for(int k=0;k<8;k++){
            int q=i+x[k];
            int r=j+y[k];
            if(q>=0 && q<8 && r>=0 && r<8 && !board[q][r].revealed){
                if(board[q][r].getValue()==mine){
                    //TODO
                }
                else {
                    revealstatus(board[q][r]);
                    if(board[q][r].getValue()==0){
                        reveal(q,r);
                    }
                    else{
                        board[q][r].setText(""+board[q][r].getValue());
                        board[q][r].setTextSize(22);
                       // board[q][r].setBackgroundColor(getResources().getColor(R.color.dargreen));
                        board[q][r].setBackground(getDrawable(R.drawable.button));
                        board[q][r].setEnabled(false);
                    }


                }

            }
        }
    }
    @SuppressLint({"ResourceAsColor", "SetTextI18n", "NewApi"})
    public void revealstatus(Mbutton button) {
        if (button.getValue() != mine) {
            if (button.getValue() == 0) {
               // button.setBackground(getDrawable(R.drawable.whitebutton));
                button.setBackground(getDrawable(R.drawable.whitebutton)
                );
            } else {
               button.setText("" + button.getValue());
               // setBackgroundColor(getResources().getColor(R.color.dargreen));
                button.setBackground(getDrawable(R.drawable.button));
                button.setTextSize(22);
                button.revealed = true;
                button.setEnabled(false);

            }
        }


    }




    @SuppressLint("NewApi")
    @Override
    public boolean onLongClick(View view) {
        Mbutton button =(Mbutton)view;
        if(button.gettextvalue().equals("F")){

            button.setextvalue("a");
            button.setBackground(getDrawable(R.drawable.button));




        }else{

            button.setextvalue("F");
            button.setBackground(getDrawable(R.drawable.flag_1));


        }
        return true;
    }
}

