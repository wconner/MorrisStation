package com.emailgame;

/**
 * Created by Tom on 4/8/15.
 */
public class SpamFilter{

    private float correct,
                total;
    private float percentage;

    public SpamFilter(){
        correct = 0f;
        total = 0f;
        percentage = 0f;
    }

    public void calcPercentage(){
        percentage = correct/total;
    }

    public float getPercentage(){
        return this.percentage;
    }

    public void incrementCorrect(){
        correct++;
    }

    public void incrementTotal(){
        total++;
        calcPercentage();
    }

    public String returnFilterLevel(){
        if(percentage >= .80){
            return "Strong";
        }else if(percentage >=.60 && percentage < .80){
            return "Average";
        }else if(percentage < .60){
            return "Weak";
        }else{
            return "unknown";
        }
    }

    public String getDetails(){
        return "total: "+ correct + "/" + total;
    }
}
