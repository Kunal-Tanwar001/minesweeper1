package com.example.prate.minestonesample;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    public  int row=8;
    public  int col=8;
    ArrayList<LinearLayout> linearLayouts;

    boolean currentstatus=true;
    Mbutton[][] board;
    LinearLayout rootLayout;

    public final static int mine=-1;
    int num_of_mines=15;
    int x[]={-1,-1,-1,0,1,1,1,0};
    int y[]={-1,0,1,1,1,0,-1,-1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    @SuppressLint("SetTextI18n")
    @Override
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
                button.setText("");
                button.revealed=true;
                reveal(button.i,button.j);
            }
            else{
                button.setText(""+button.getValue());
            }




        }// checkgame will tell whether game is over or not true means game is on.
        if(!checkgame()){
            Toast.makeText(this, "YOU won the match", Toast.LENGTH_SHORT).show();
            currentstatus=false;

        }
    }


    @SuppressLint("SetTextI18n")
    public void revealAllmines(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(!board[i][j].revealed){
                    if(board[i][j].getValue()==mine)
                        board[i][j].setText("*");
                    else
                        board[i][j].setText(board[i][j].getValue()+"");
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

    @SuppressLint("SetTextI18n")
    public void reveal(int i, int j){
        for(int k=0;k<8;k++){
            int q=i+x[k];
            int r=j+y[k];
            if(q>=0 && q<8 && r>=0 && r<8 && !board[q][r].revealed){
                if(board[q][r].getValue()==mine){
                    //TODO
                }
                else {
                    board[q][r].revealstatus();
                    if(board[q][r].getValue()==0){
                        reveal(q,r);
                    }
                    else{
                        board[q][r].setText(""+board[q][r].getValue());
                        board[q][r].setEnabled(false);
                    }


                }

            }
        }
    }





    @Override
    public boolean onLongClick(View view) {
        Mbutton button =(Mbutton)view;
        if(button.getText().equals("F")){

            button.setText(" ");
            button.setTextColor(Color.rgb(0,0,0));
        }else{

            button.setText("F");
            button.setTextColor(Color.rgb(255,0,0));

        }
        return true;
    }
}

