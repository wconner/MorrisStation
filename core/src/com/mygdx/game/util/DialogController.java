package com.mygdx.game.util;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Danny on 11/15/2015.
 */
public class DialogController {

    private ArrayList<Diode> nodes;
    public DialogController(){
        nodes = new ArrayList<>();
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
        //int anum = 0;

        for (String line : lines) {
            if(line.charAt(0)=='*') {
                System.out.println(line.substring(1) + " Answer");
                ans.add(line.substring(1));
                //anum=Integer.parseInt(line.substring(1,2));
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
        FileReader reader = new FileReader("Diolog.txt");
        BufferedReader inputFile = new BufferedReader(reader);

        String line=inputFile.readLine();

        String actor = "";
        String stat = "";
        String q = "";
        int qNum=0;
        int qPointer=0;
        ArrayList<String> ans = new ArrayList<>();
        boolean zone=true;

        int j=0;

        while(zone) {//finds the seen
            if (!(seen == Integer.parseInt(line.substring(1, 2)) && line.substring(0, 1).equals("$")))
                line = inputFile.readLine();
            else
                zone=false;
        }
        while (line != null&&seen>=0) {//makes dionodes for the array
            if (line.equals("")) {//creates a node
                nodes.add(new Diode(seen, actor, stat, q, qNum,ans));
                actor = stat = q = "";//clears Strings
                System.out.println(nodes.get(j).toString());//TESTING
                j++;//TESTING
                ans.clear();
            }
            else if (line.charAt(0) == '*') {//creates an answer
                ans.add(line.substring(1));
            }
            else if (line.charAt(0) == '$'&&(seen+1)==Integer.parseInt(line.substring(1,2))) {//Looks for a sceen #
                seen = -1;
            }
            else {
                if (line.charAt(0) == '@') {//Looks for Actor
                    actor = line.substring(1);
                } else if (line.charAt(0) == '!') {//Looks for a Statment
                    stat = line.substring(1);
                } else if (line.charAt(0) == '?') {//Looks for a Question
                    qNum =Integer.parseInt(line.substring(1,4));
                    q = line.substring(4);
                }
            }
            line=inputFile.readLine();
        }
        seenToString();
    }

    /**
     * Creates a combined string of all noses in a seen.
     * @return String
     */
    public String seenToString(){
        String out="";
        for(Diode dio : nodes)
            out+=dio.toString()+"\n";
        return out;
    }

    /**
     * Gets a question based on actor and Q id number.
     * @param actor String of the actor you are using
     * @param qNum id num of the question. used in finding the question with answers.
     * @return String Q || null if not found
     */
    public String getQuestion(String actor, int qNum){
        for(Diode dio:nodes){
                if(dio.getActor().toLowerCase().equals(actor.toLowerCase())){
                    if (dio.getQnum() == qNum) {
                        return dio.getQ();
                    }
                }
            }

        return null;
    }

    /**
     * Geta all answers in one string.
     * @param q question id
     * @return String of all answers separated by \n
     */
    public String getAs(int q){
        String out="";
        for (Diode dio:nodes){
            if(dio.getQnum()==q){
                for(String answer:dio.getAs()){
                    out+=answer.substring(3)+"\n";
                }
                return out;
            }
        }
        return null;
    }

    public int selectA(int q, int a){
        for(Diode dio: nodes){
            if(dio.getQnum()==q)
                return Integer.parseInt(dio.getAs().get(a));
        }

        return -1;
    }


    /** Created by Danny on 11/15/2015.
     *
     * To be replaced by/use JSON
     */
    private class Diode {

        private int seen;
        private String actor;
        private String stat;
        private String q;
        private int qnum;
        private ArrayList<String> a;
        //private String[][] qa;//question then answers

        public Diode(int seen, String actor,String inter, String q, int qNum,ArrayList<String> ans) {
            this.a=new ArrayList<>();
            this.seen = seen;
            this.actor = actor;
            stat=inter;
            this.q=q;
            this.qnum=qNum;
            this.a.addAll(ans);
        }

        public int getSeen() {
            return seen;
        }

        public String getActor() {
            return actor;
        }

        public String getQ(){
            return q;
        }

        public int getQnum(){
            return qnum;
        }

        public ArrayList<String> getAs(){
            return a;
        }

        @Override
        public String toString() {
            String qout="Answers{ ";
            for(String s: a)
                qout+=s+" | ";
            qout+="}\n";

            String qaH= "QA{" +
                    "Question='" + q + '\'' +
                    ", Answer=" + qout +
                    '}';
            return "Diode{" +
                    "seen=" + seen +
                    ", actor='" + actor + '\'' +
                    ", stat='" + stat + '\'' +
                    "Q="+qaH+"}\n";
        }
    }
}
