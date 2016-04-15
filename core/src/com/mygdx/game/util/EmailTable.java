package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.MainClass;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.TouchUpListener;


/**
 * Created by The Evening Star on 3/30/2016.
 */
public class EmailTable extends Table {

    private TextButton emailButton;
    private CheckBox checkBox;
    private String eMessage;
    private String subject;
    private String sender;
    private String type;

    public EmailTable(TextButton eb, CheckBox cb){
        emailButton = eb;
        checkBox = cb;
        this.add(checkBox);
        this.add(emailButton).size(400, 30).space(8);

    }

    public void seteMessage(String m){
        eMessage = m;
    }

    public void setSubject(String s){ subject = s;}
    public void setSender(String s){ sender =s;}


    public TextButton getEmailButton(){
        return emailButton;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public String geteMessage(){
        return eMessage;
    }

    public String getSender(){
        return sender;
    }

    public String getType(){
        return type;
    }

    public String getSubject(){return subject;}

    public void setType(String s){
        type = s;
    }
}
