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
public class MarkedAsSpam{
    private ArrayList<Email> spam;
    private TextureAtlas atlas;
    private Skin skin;

    public static final FileLocation fileLocation = FileLocation.ABSOLUTE;

    public MarkedAsSpam(){
        atlas = new TextureAtlas(fileLocation.getFile("android/assets/ui_skin/uiskin.atlas"));
        skin = new Skin(fileLocation.getFile("android/assets/ui_skin/uiskin.json"), atlas);

        spam = new ArrayList<Email>();
    }

    /**
     * makes the visual table to for the phone state
     * @return t
     */
    public Table makeSpam(){
        Table t = new Table();
        for(Email email : spam){
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
     * makes sure that the ArrayList
     * only has a maximum of 5 items in it
     */
    public void trimList(){
        if(spam.size() > 5){
            spam.remove(spam.size() - 1);
            trimList();
        }
    }

    public ArrayList<Email> getSpam(){
        return spam;
    }
}
