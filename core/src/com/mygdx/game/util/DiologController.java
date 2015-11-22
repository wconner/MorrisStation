package com.mygdx.game.util;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Danny on 11/15/2015.
 */
public class DiologController {

    private ArrayList<Diode> nodes;
    private FileReader reader;
    private BufferedReader inputFile;

    public DiologController(){
        nodes = new ArrayList<Diode>();
    }

    /**
     * Test method to see if txt loading effective.
     * Loads all info into nodes.
     * @param lines Arraylist of all the info by line
     * @throws FileNotFoundException
     */
    public void makeDiolog(ArrayList<String> lines)throws FileNotFoundException {
        int seen = 0;
        String actor = "";
        String stat = "";
        String q = "";
        int qNum = 0;
        ArrayList<String> ans = new ArrayList<String>();
        int anum = 0;

        for (String line : lines) {
            if(line.charAt(0)=='*') {
                System.out.println(line.substring(1) + " Answer");
                ans.add(line.substring(1));
            }
            else if(line.charAt(0)=='\n'){
                nodes.add(new Diode(seen,actor,stat,q,qNum,ans));
            }
            else {
                if (line.charAt(0) == '$') {//Looks for a sceen #
                    System.out.println(line.substring(1) + "Seen");
                    seen = Integer.parseInt(line.substring(1));
                }
                else if (line.charAt(0) == '@') {//Looks for Actor
                    System.out.println(line.substring(1) + " Actor");
                    actor = line.substring(1);
                }
                else if (line.charAt(0) == '!') {//Looks for a Statment
                    System.out.println(line.substring(1) + " Satement");
                    stat = line.substring(1);
                }
                else if (line.charAt(0) == '?') {//Looks for a Question
                    System.out.println(line.substring(2) + " Question");
                    qNum = Integer.parseInt(line.substring(1, 2));
                    q = line.substring(2);
                }
            }
        }

    }

    /**
     * Reads in one seen at a time and stores them into nodes
     * To be run on seen change.
     * @param seen int representing the disired seen
     * @throws IOException
     */
    public void makeSeen(int seen) throws IOException {
        reader = new FileReader("Diolog.txt");
        inputFile = new BufferedReader(reader);

        String line=inputFile.readLine();

        String actor = "";
        String stat = "";
        String q = "";
        int qNum=0;
        ArrayList<String> ans = new ArrayList<String>();
        boolean zone=true;

        while(zone) {//finds the seen
            if (!(seen == Integer.parseInt(line.substring(1, 2)) && line.substring(0, 1).equals("$")))
                line = inputFile.readLine();
            else
                zone=false;
        }
        while (line != null&&seen>=0) {//makes dionodes for the array
            if (line.equals("")) {//creates a node
                nodes.add(new Diode(seen, actor, stat, q, qNum,ans));
                actor = stat = q = "";
                ans = null;
            }
            else if (line.charAt(0) == '*') {//creates an answer
                //System.out.println(line.substring(1) + " Answer");
                ans.add(line.substring(1));
            }

            else if (line.charAt(0) == '$'&&(seen+1)==Integer.parseInt(line.substring(1,2))) {//Looks for a sceen #
                //System.out.println(line.substring(1) + "Seen");
                seen = -1;
            }
            else {
                if (line.charAt(0) == '@') {//Looks for Actor
                   // System.out.println(line.substring(1) + " Actor");
                    actor = line.substring(1);
                } else if (line.charAt(0) == '!') {//Looks for a Statment
                    //System.out.println(line.substring(1) + " Satement");
                    stat = line.substring(1);
                } else if (line.charAt(0) == '?') {//Looks for a Question
                   // System.out.println(line.substring(2) + " Question");
                    qNum =Integer.parseInt(line.substring(1,2));
                    q = line.substring(2);
                }
            }
            line=inputFile.readLine();
        }
        seenToString();
    }

    public String seenToString(){
        String out=null;
        for(Diode dio : nodes)
            out+=dio.toString()+"\n";
        return out;
    }

    public String getQuestion(int sceen, String actor, int qNum){
        for(Diode dio:nodes){
            if(dio.getSeen()==sceen){
                if(dio.getActor().equals(actor)) {
                    if (dio.getQnum() == qNum) {
                        return dio.getQ();
                    }
                }
            }
        }
        return null;
    }
    public String[] getA(){
        return null;
    }
    }
