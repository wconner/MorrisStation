package com.mygdx.game.screens.gui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ian on 1/21/2015.
 */
public class DialogWindow {

    /*
    * For building a window and assigning it a skin
    * */
    private Window window;
    private Skin skin;
    private TextureAtlas atlas;

    /*
    * Text labels
    * */
    private Label label;
    private Label contentLabel;




    Table root;
    Slider ambientColorR, ambientColorG, ambientColorB;
    Slider lightColorR, lightColorG, lightColorB, lightZ;
    Slider attenuationX, attenuationY, attenuationZ;
    Slider ambientIntensity;
    Slider strength;
    Label text;
    CheckBox useShadow, useNormals, yInvert;

    /*
    * Default constructor
    * */
    public DialogWindow(){
        atlas = new TextureAtlas("android/assets/ui_skin/uiskin.atlas");
        skin = new Skin(Gdx.files.local("android/assets/ui_skin/uiskin.json"), atlas);
        skin.addRegions(atlas);
    }

    /*
    * Build a basic window
    * */
    public Window makeWindow(){

        window = new Window("Player 1", skin);
        window.align(Align.center);
        window.row().fill().expandX();

        window.row().prefWidth(Gdx.graphics.getWidth() * 0.95f);
        label = new Label("Welcome to Morris Town", skin);
        label.setWrap(true);
        window.add(label).align(Align.center).padLeft(5).padRight(5).padBottom(20).expandX();

        window.row().prefWidth(Gdx.graphics.getWidth() * 0.95f);
        contentLabel = new Label("Morristown is a narrative game to help learn how to be safe online"
                + "Players will experience scenarios which challenge them to create strong passwords"
                + ", learn about concepts like viruses and phishing and more.", skin);
        contentLabel.setWrap(true);
        window.add(contentLabel).align(Align.center).padLeft(5).padRight(5).expandX();


        window.pack();


        return window;
    }

    /*
    * Getter for window
    * */
    public Window getWindow(){
        return window;
    }

    /*
    * Set the text of the only label on the page
    * TODO introduce multiple labels with variable input streams
    * */
    public void setText(String text){
        label.setText(text);
    }

    /*
    * stage.act() calls the act method on all widgets of the stage
    * */
    public void update(){
        //stage.act();
        //stage.draw();
    }







    private CheckBox checkbox (final String name, boolean defaultValue) {
        final CheckBox checkbox = new CheckBox(name, skin);
        checkbox.setChecked(true);

        checkbox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                //prefs.putBoolean(name, checkbox.isChecked());
                //prefs.flush();
            }
        });

        return checkbox;
    }

    private Slider slider (final String name, float defaultValue) {
        final Slider slider = new Slider(0, 1, 0.01f, false, skin);
        slider.setValue(0);

        final Label label = new Label("", skin);
        label.setAlignment(Align.right);
        label.setText(Float.toString((int)(slider.getValue() * 100) / 100f));

        slider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                label.setText(Float.toString((int)(slider.getValue() * 100) / 100f));
                if (!slider.isDragging()) {
                    //prefs.putFloat(name, slider.getValue());
                    //prefs.flush();
                }
            }
        });

        Table table = new Table();
        table.add(label).width(35).space(12);
        table.add(slider);

        root.add(name);
        root.add(table).fill().row();
        return slider;
    }

    private Label label(String text){
        final Label label = new Label("", skin);
        label.setAlignment(Align.left);
        label.setText(text);

        Table table = new Table();
        table.add(label).width(35).space(12);
        //table.add(slider);

        //root.add(text);
        root.add(table).fill().row();
        return label;
    }
}
