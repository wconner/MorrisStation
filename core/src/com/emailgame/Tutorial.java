package com.emailgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tom on 4/7/15.
 *
 * this class can extend a table, and that table will have 3 rows and one column
 *  col 1 is sender
 *  col 2 is subject
 *  col 3 is email body
 *
 * will need set methods for the row data
 *
 */
public class Tutorial extends Table{

    public static final FileLocation fileLocation = FileLocation.ABSOLUTE;

    private EmailParser ep;
    private ArrayList<Email> emails;
    private String sender,
                   subject,
                   emailBody;
    private Skin skin;
    private TextureAtlas atlas;
    private Label senderLabel,
                  subjectLabel,
                  emailBodyLabel;

    public Tutorial(){

        ep = new EmailParser();
        emails = ep.getEmails();
        sender = "";
        subject = "";
        emailBody = "";
        atlas = new TextureAtlas(fileLocation.getFile("android/assets/ui_skin/uiskin.atlas"));
        skin = new Skin(fileLocation.getFile("android/assets/ui_skin/uiskin.json"), atlas);

        senderLabel = new Label(sender, skin);
        subjectLabel = new Label(subject, skin);
        emailBodyLabel = new Label(emailBody, skin);

        makeEmailTable();




    }
    /**
     * Creates the email table for visibly displaying emails
     *
     * this could be its own class for later use of showing emails
     */
    public void makeEmailTable(){
        this.setPosition(100, 200);
        this.setSkin(skin);
        //formats each cell and sets the font size and so the text wraps the label width
        //sender cell
        this.add(senderLabel).width(350f);
        senderLabel.setColor(Color.BLUE);
        senderLabel.setFontScale(.85f, .85f);
        senderLabel.setWrap(true);
        senderLabel.setAlignment(Align.bottomLeft);
        this.row();

        //subject cell
        this.add(subjectLabel).width(350f);
        subjectLabel.setFontScale(.85f, .85f);
        subjectLabel.setColor(Color.BLACK);
        subjectLabel.setWrap(true);
        subjectLabel.setAlignment(Align.bottomLeft);
        this.row();

        //email body cell
        this.add(emailBodyLabel).width(350f);
        emailBodyLabel.setWrap(true);
        emailBodyLabel.setColor(Color.BLACK);
        emailBodyLabel.setFontScale(.85f, .85f);
        emailBodyLabel.setAlignment(Align.bottomLeft);
        this.setSize(350, 300);
        this.layout();
    }

    public Email getNextEmail(){
        Random random = new Random();
        int r = random.nextInt(emails.size());
        Email tempEmail = emails.get(r);
        emails.remove(r);
        return tempEmail;
    }

    /**
     * sets the email that will be displayed
     * @param send
     * @param subj
     * @param eb
     */
    public void setEmailInfo(CharSequence send, CharSequence subj, CharSequence eb){
        this.senderLabel.setText(send);
        this.subjectLabel.setText(subj);
        this.emailBodyLabel.setText(eb);
    }

    /**
     * returns the emails that are in the tutorial
     * @return emails
     */
    public ArrayList<Email> getEmails(){
        return emails;
    }
}
