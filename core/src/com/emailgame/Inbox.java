package com.emailgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tom on 4/11/15.
 */
public class Inbox extends Table{
    private ArrayList<Email> emails;
    private EmailParser ep;
    private TextureAtlas atlas;
    private Skin skin;

    public static final FileLocation fileLocation = FileLocation.ABSOLUTE;

    public Inbox(){
        atlas = new TextureAtlas(fileLocation.getFile("android/assets/ui_skin/uiskin.atlas"));
        skin = new Skin(fileLocation.getFile("android/assets/ui_skin/uiskin.json"), atlas);
        ep = new EmailParser();

        emails = new ArrayList<Email>();

        for(int i = 0; i < 5; i++){
            Random rand = new Random();
            int n = rand.nextInt(ep.getEmails().size());
            emails.add(ep.getEmails().get(n));
            ep.getEmails().remove(n);
        }
    }

    /**
     * makes the visual table to for the phone state
     * @return t
     */
    public Table makeInbox(){
        Table t = new Table();
        for(Email email : emails){
            CharSequence sender = email.getSender();
            CharSequence subject = email.getSubject();
            Label senderLabel = new Label(sender, skin);
            senderLabel.setFontScale(Constants.EMAIL_TEXT_SIZE);
            senderLabel.setColor(Color.BLACK);
            Label subjectLabel = new Label(subject, skin);
            subjectLabel.setFontScale(Constants.EMAIL_TEXT_SIZE);
            subjectLabel.setColor(Color.BLACK);
            t.add(senderLabel).width(200f);
            t.add(subjectLabel).width(200f);
            t.row();
        }
        return t;
    }

    public Email getNextEmail(){
        return emails.get(0);
    }

    public ArrayList<Email> getEmails(){
        return emails;
    }

    /**
     * makes sure that ArrayList only has a max
     * of 5 items in it
     */
    public void trimList(){
        if(emails.size() > 5){
            emails.remove(emails.size() - 1);
            trimList();
        }
    }

    /**
     * for testing purposed only for filling inbox
     */
    public void getMoreMail(){
        Random rand = new Random();
        EmailParser emailParser = new EmailParser();
        for(int i = 0; i < 5; i++){
            int n = rand.nextInt(emailParser.getEmails().size());
            emails.add(emailParser.getEmails().get(n));
        }
    }
}
