package com.emailgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.util.ArrayList;


/**
 * Created by Tom on 2/19/2015.
 */
public class Email extends Table
{

    public static final FileLocation fileLocation = FileLocation.ABSOLUTE;

    private String emailType,
                         sender,
                         subject,
                         message;

    private Label senderLabel,
                  subjectLabel,
                  messageLabel;

    private Skin skin;

    private TextureAtlas atlas;


    /**
     * no arg constructor
     */
    public Email()
    {
        emailType = " ";
        sender = " ";
        subject = " ";
        message = " ";
    }

    /**
     * creates the email object with required fields and
     * prepares the labels for display
     * @param emailType
     * @param sender
     * @param subject
     * @param message
     */
    public Email(String emailType, String sender, String subject, String message)
    {
        atlas = new TextureAtlas(fileLocation.getFile("android/assets/ui_skin/uiskin.atlas"));
        skin = new Skin(fileLocation.getFile("android/assets/ui_skin/uiskin.json"), atlas);

        this.emailType = emailType;
        this.message = message;
        this.sender = sender;
        this.subject = subject;

        senderLabel = new Label(sender, skin);
        subjectLabel = new Label(subject, skin);
        messageLabel = new Label(message, skin);

        //formatting the labels
        //color
        senderLabel.setColor(Color.BLUE);
        subjectLabel.setColor(Color.BLACK);
        messageLabel.setColor(Color.BLACK);
        //wrap text
        senderLabel.setWrap(true);
        subjectLabel.setWrap(true);
        messageLabel.setWrap(true);
        //alignment
        senderLabel.setAlignment(Align.topLeft);
        subjectLabel.setAlignment(Align.topLeft);
        messageLabel.setAlignment(Align.topLeft);
        //font size
        senderLabel.setFontScale(Constants.EMAIL_TEXT_SIZE);
        subjectLabel.setFontScale(Constants.EMAIL_TEXT_SIZE);
        messageLabel.setFontScale(Constants.EMAIL_TEXT_SIZE);


    }

    /**
     * makes the full message view for the phone
     * @return t
     */
    public Table getFullMessageView(){
        Table t = new Table();
        t.add(senderLabel).width(Constants.ROW_WIDTH);
        t.row();
        t.add(subjectLabel).width(Constants.ROW_WIDTH);
        t.row();
        t.add(messageLabel).width(Constants.ROW_WIDTH);
        return t;
    }

    /**
     * makes the table view for the inbox component of the phone
     * @return t
     */
    public Table getInboxView(){
        Table t = new Table();
        t.add(senderLabel).width(Constants.PREVIEW_CELL_WIDTH);
        t.add(subjectLabel).width(Constants.PREVIEW_CELL_WIDTH);
        return t;
    }

    /**
     * Used for checking answers from the user
     * @return emailType
     */
    public String getEmailType()
    {
        return emailType.toLowerCase();
    }

    /**
     * returns the sender of the email object
     * @return sender
     */
    public String getSender(){
        return "From : " +sender;
    }

    /**
     * returns the subject of the email object
     * @return subject
     */
    public String getSubject(){
        return "Subject: " + subject;
    }

    /**
     * returns the message of the email object
     * @return message
     */
    public String getMessage(){
        return message;
    }
}