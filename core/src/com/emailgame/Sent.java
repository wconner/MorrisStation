package com.emailgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

/**
 * Created by Tom on 4/12/15.
 */
public class Sent{
    private ArrayList<Email> sent;
    private TextureAtlas atlas;
    private Skin skin;

    public static final FileLocation fileLocation = FileLocation.ABSOLUTE;

    public Sent(){
        atlas = new TextureAtlas(fileLocation.getFile("android/assets/ui_skin/uiskin.atlas"));
        skin = new Skin(fileLocation.getFile("android/assets/ui_skin/uiskin.json"), atlas);

        sent = new ArrayList<Email>();
    }

    /**
     * makes the sent table to be displayed in the phone state
     * @return t
     */
    public Table makeSent(){
        Table t = new Table();
        for(Email email : sent){
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

    /**
     * makes sure that ArrayList only has a max
     * of 5 items in it
     */
    public void trimList(){
        if(sent.size() > 5){
            sent.remove(sent.size() - 1);
            trimList();
        }
    }

    public ArrayList<Email> getSent(){
        return sent;
    }
}
