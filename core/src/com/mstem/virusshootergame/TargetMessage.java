package com.mstem.virusshootergame;

import com.mstem.virusshootergame.Target;

import java.util.ArrayList;


/**
 * Created by catherinehuang on 4/15/15.
 */
public class TargetMessage {

    //TODO: move all these to a file to read from
    String[] adwareMessageT = {"Adware is commonly known as software that is embedded with advertisements.",
            "Adware may be consider a spyware if it collects information about the user.",
            "Some adware may track your Internet surfing habits in order to serve ads related to you."};
    String[] spywareMessageT = {"Spyware monitors user activity on the Internet and transmits that information in the background to someone else.",
                            "A  common way to become a victim of spyware is to download peer-to-peer file that are available today.",
                            "Spyware uses computer's memory and resources to perform task, which can lead to system crashes."};
    String[] adMessageT = {"Randomly click on website advertisement may lead you to harmful site that steal your web surfing information",
                            "Clicking on ads was 182 times more likely to install a virus on a user's computer than surfing the Internet for porn.",
                            "Advertisers often use technology, such as web bugs and re-spawning cookies, to maximizing their abilities to track consumers."};
    String[] passwordMessageT = {"There are a number of common techniques used to crack passwords made up of simple and widely used passwords.",
                                "Don’t tell anyone your password.",
                                "Do use at least eight characters of lowercase and uppercase letters, numbers, and symbols in your password.\n Remember, the more the merrier."};
    String[] facebookappT = {"Download apps from unknown sources is more dangerous.",
                            "Most digital device and browsers allow app verification before download or install an app",
                            "App verification will verify apps when you install them, as well as periodically scan for potentially harmful apps."};
    ArrayList<String> adwareMessage = new ArrayList<String>();
    ArrayList<String> spywareMessage = new ArrayList<String>();
    ArrayList<String> adMessage = new ArrayList<String>();
    ArrayList<String> passwordMessage = new ArrayList<String>();
    ArrayList<String> facebookapp = new ArrayList<String>();

    private String message = "";

    public TargetMessage() {

        setupStringMessages();

        for (int a = 0; a < 3; a ++) {
            for (int i = 0; i < adwareMessageT.length; i++) {
                adwareMessage.add(adwareMessageT[i]);
                spywareMessage.add(spywareMessageT[i]);
                adMessage.add(adMessageT[i]);
                passwordMessage.add(passwordMessageT[i]);
                facebookapp.add(facebookappT[i]);
            }
        }
    }

    private void setupStringMessages() {


    }


    public String randomPick(String name, CollisionDetect cd) {
        chooseFrom(name, cd);
        return message;

    }

    /**
     * Return different message according to the hit type
     * @param name
     * @param cd
     */
    private void chooseFrom(String name, CollisionDetect cd) {
        message ="";
        if(name == "adware" || name.equals("adware")) {
            int i = cd.getNumberOfHits()-1;
            message = adwareMessage.get(i);
        }
        else if(name == "spyware" || name.equals("spyware")){
            int i = cd.getNumberOfHits()-1;
            message = spywareMessage.get(i);
        }
        else if(name == "changepassword" || name.equals("changepassword")) {
            int i = cd.getNumberOfHits()-1;
            message = passwordMessage.get(i);
        }
        else if(name == "ad" || name.equals("ad")){
            int i = cd.getNumberOfHits()-1;
            message = adMessage.get(i);
        }
        else {
            message = "not yet implemented";
        }
    }

//    public static void main(String[] args) {
//        TargetMessage tm = new TargetMessage();
//        System.out.println(tm.randomPick("adware"));
//        System.out.println(tm.randomPick("spyware"));
//        System.out.println(tm.randomPick("spyware"));
//        System.out.println(tm.randomPick("spyware"));
//
//
//    }


}
