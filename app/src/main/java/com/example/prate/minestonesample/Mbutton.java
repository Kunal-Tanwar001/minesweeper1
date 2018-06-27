package com.example.prate.minestonesample;


import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;

public class Mbutton extends AppCompatButton {
    public int i;
    public int j;
    public int value;
    public String s="a";
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
    public void setextvalue(String s){
        this.s=s;
    }
public String gettextvalue(){
        return this.s;
}




}
