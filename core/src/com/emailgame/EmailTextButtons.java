package com.emailgame;

/**
 * Created by Tom on 3/29/15.
 */
public class EmailTextButtons{

    private int x,
                y;

    private String name;

    public EmailTextButtons(String name, int x, int y){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getXCoord(){
        return this.x;
    }

    public int getYCoord(){
        return this.y;
    }

    public void setXCoord(int x){
        this.x = x;
    }

    public void setYCoord(int y){
        this.y = y;
    }

}
