package com.mygdx.game.screens.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;


/**
 * Created by Ian on 1/21/2015.
 */
@SuppressWarnings("ALL")
public class DialogWindow {

    /**
    * For building a window and assigning it a skin
    */
    private Window window;
    private Skin skin;
    private TextureAtlas atlas;

    /**
    * Text labels
    */
    private Label label;
    private Label contentLabel;

    private boolean hidden;


    Table root;

    /**
    * Default constructor
    */
    public DialogWindow(){
        atlas = new TextureAtlas("android/assets/ui_skin/uiskin.atlas");
        skin = new Skin(Gdx.files.local("android/assets/ui_skin/uiskin.json"), atlas);
        skin.addRegions(atlas);
        hidden = true;
    }

    /**
    * Build a basic window
    */
    public Window makeWindow(){

        window = new Window("I'm not here", skin);
        window.row().fill().expandX();

        label = new Label("Welcome to MY Town", skin);
        label.setWrap(true);

        window.row().prefWidth(Gdx.graphics.getWidth() * 0.98f);
        contentLabel = new Label("Morristown is a narrative game to help learn how to be safe online"
                + "Players will experience scenarios which challenge them to create strong passwords"
                + ", learn about concepts like viruses and phishing and more.", skin);
        contentLabel.setWrap(true);
        contentLabel.setScale(5f);
        window.add(contentLabel).padLeft(5).padRight(5).padBottom(20).expandX();
//Remember to add the content to the window.

        window.pack();


        return window;
    }
    public boolean isHidden(){return hidden;}

    /**
     * flips the boolean for hidden
     */
    public void hide() {
        if (hidden == false)
            hidden = true;
        else {
            hidden = false;
        }
        window.setVisible(hidden);
    }

    /**
    * Getter for window
    */

    public Window getWindow(){
        return window;
    }

    /**
    * Set the text of the only label on the page
    * TODO introduce multiple labels with variable input streams
    */
    public void setText(String text){
        contentLabel.setText(text);
    }

    /**
    * stage.act() calls the act method on all widgets of the stage
    */
    public void update(Stage stage){
         stage.act();
         stage.draw();
    }

    private Label label(String text){
        final Label label = new Label("", skin);
        //label.setAlignment(Align.left);
        label.setText(text);

        Table table = new Table();
        table.add(label).width(35).space(12);
        //table.add(slider);

        //root.add(text);
        root.add(table).fill().row();
        return label;
    }
}
