package com.mygdx.game.util;
import java.util.ArrayList;

/** Created by Danny on 11/15/2015.
 *
 * To be replaced by/use JSON
 */
public class Diode {

    private int seen;
    private String actor;
    private String stat;
    private QA qA;
    //private String[][] qa;//question then answers

    public Diode(int seen, String actor,String inter, String q, int qNum,ArrayList<String> a) {
        this.seen = seen;
        this.actor = actor;
        stat=inter;
        qA=new QA(q,qNum,a);
    }

    public int getSeen() {
        return seen;
    }

    public String getActor() {
        return actor;
    }

    public String getQ(){
        return qA.getQ();
    }

    public int getQnum(){
        return qA.getQNum();
    }

    public String getAs(){
        String out="";
        for(String answer:qA.getA()){
            out+=answer+"\n";
        }
        return out;
    }

    public String selectA(int choice){
        return qA.selectA(choice);
    }

    @Override
    public String toString() {
        return "Diode{" +
                "seen=" + seen +
                ", actor='" + actor + '\'' +
                ", stat='" + stat + '\'' +
                "Q="+qA.toString()+"}\n";
    }

 public class QA {
    String q;
     int qNum;
    ArrayList<String> a;

    public QA(String q,int qNum, ArrayList<String> a) {
        this.q = q;
        this.qNum = qNum;
        this.a = a;
    }

    public String getQ() {
        return q;
    }

     public int getQNum(){
         return qNum;
     }

     public ArrayList<String> getA(){
         return a;
     }


    public String selectA(int selection) {
        String out="";
        for(int i = 0; i<a.size();i++)
            if(i==selection)
                out=a.get(i);
        return out;
    }

     @Override
     public String toString() {
         String out="Answers{ ";
         for(String s: a)
             out+=s+" | ";
         out+="}\n";

         return "QA{" +
                 "Question='" + q + '\'' +
                 ", Answer=" + out +
                 '}';
     }
 }
}