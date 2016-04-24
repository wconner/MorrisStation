package com.mygdx.game.screens.miniGames;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by The Evening Star on 3/30/2016.
 */
public class EmailTable extends Table {

    private TextButton emailButton;
    private CheckBox checkBox;
    private String eMessage, subject, sender, type;

    public EmailTable(TextButton eb, CheckBox cb) {
        emailButton = eb;
        checkBox = cb;
        this.add(checkBox);
        this.add(emailButton).size(400, 30).space(8);
    }

    public void seteMessage(String m) {
        eMessage = m;
    }

    public void setSubject(String s) {
        subject = s;
    }

    public void setSender(String s) {
        sender = s;
    }

    public TextButton getEmailButton() {
        return emailButton;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public String geteMessage() {
        return eMessage;
    }

    public String getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public void setType(String s) {
        type = s;
    }
}