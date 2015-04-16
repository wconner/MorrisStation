package com.mygdx.game;

/**
 * Created by Tom on 3/29/15.
 */
public class EmailTextButtons{

    private float x,
                y;

    private String name;

    public EmailTextButtons(String name, float x, float y){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public float getXCoord(){
        return this.x;
    }

    public float getYCoord(){
        return this.y;
    }

    public void setXCoord(float x){
        this.x = x;
    }

    public void setYCoord(float y){
        this.y = y;
    }

}
