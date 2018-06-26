package com.example.prate.minestonesample;


import android.content.Context;
import android.support.v7.widget.AppCompatButton;

public class Mbutton extends AppCompatButton {
    public int i;
    public int j;
    public int value;
    public String s;
    public boolean revealed;

    public Mbutton(Context context) {
        super(context);

    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }


    public void revealstatus(){
        if(value!=MainActivity.mine){
             if(value==0){
                 setText("");
             }
             else
            setText(""+value);

            revealed=true;
            setEnabled(false);

        }
    }


}
