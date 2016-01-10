package com.mygdx.game.screens.gui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ian on 1/21/2015.
 */
public class Display {

    /**
    * For building a window and assigning it a skin
    */
    private Window window;
    private Skin skin;
    private TextureAtlas atlas;

    /**
    * For holding the UI
    */
    private Stage stage;
    private Table table;

    /**
    * Text labels
    */
    private Label label;
    private Label headerLabel;




    Table root;
    Slider ambientColorR, ambientColorG, ambientColorB;
    Slider lightColorR, lightColorG, lightColorB, lightZ;
    Slider attenuationX, attenuationY, attenuationZ;
    Slider ambientIntensity;
    Slider strength;
    Label text;
    CheckBox useShadow, useNormals, yInvert;

    /**
    * Default constructor
    */
    public Display(){
        //init();
        /*
        * Atlas holds the skin texture data
        * Skin sets a new skin
        * */
        atlas = new TextureAtlas("android/assets/ui_skin/uiskin.atlas");
        skin = new Skin(Gdx.files.local("android/assets/ui_skin/uiskin.json"), atlas);
        skin.addRegions(atlas);

    }
    public Display(Stage stage) {
        //init();
        /*
        * Atlas holds the skin texture data
        * Skin sets a new skin
        * */
        this.stage = stage;
        atlas = new TextureAtlas("android/assets/ui_skin/uiskin.atlas");
        skin = new Skin(Gdx.files.local("android/assets/ui_skin/uiskin.json"), atlas);
        skin.addRegions(atlas);
    }

    /**
    * Construct the stage and call necessary components
    */
    public void init(){
        /**
        * A display must be held by the stage
        */

        //stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);



        /**
        * Label is created and given a skin
        * The window is created and its position set
        */
        label = new Label("", skin);
        window = makeWindow();
        window.addActor(table);



        /**
        * The stage must hold all the ui widgets
        * TODO Stage widget management should be outside of the loadAssets() method for DRY
        */

        stage.addActor(window);
    }




    /**
    * Build a basic window
    */
    public Window makeWindow(){

        window = new Window("Player 1", skin);

        root = new Table(skin);
        root.pad(2, 4, 4, 4).defaults().space(6);
        label = label("Welcome to Morris Town");
        {
            Table table = new Table();
            table.defaults().space(12);
            table.add(useShadow = checkbox("Option A", true));
            table.add(useNormals = checkbox("Option B", true));
            table.add(yInvert = checkbox("Option C", true));
            root.add(table).colspan(2).row();
        }


        root.setScaleX(.4f);
        window.add(root);
        window.setPosition(0, Gdx.graphics.getHeight());
        window.pack();


        return window;
    }

    public Window makeWindow(String name, int x, int y){

        window = new Window(name, skin);

        root = new Table(skin);
        root.pad(2, 4, 4, 4).defaults().space(15);
        root.columnDefaults(0).top();
        root.columnDefaults(1).left();

        label = label(name);

        TextButton okButton = new TextButton("OK", skin);
        okButton.getColor().a = 0.66f;
        window.add(okButton).height(20);

        root.setScaleX(.4f);
        window.add(root);//.expand().fill();
        window.setPosition(x, y);
        window.pack();

        return window;
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
        label.setText(text);
    }



    /**
    * stage.act() calls the act method on all widgets of the stage
    */
    public void update(){
       stage.act();
       stage.draw();
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
        //label.setAlignment(Align.right);
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

        //root.add(name);
        //root.add(table).fill().row();
        return slider;
    }
        //root.columnDefaults(0).top().right();
        //root.columnDefaults(1).left();
        /*ambientColorR = slider("Ambient R", 1);
        ambientColorG = slider("Ambient G", 1);
        ambientColorB = slider("Ambient B", 1);
        ambientIntensity = slider("Ambient intensity", 0.35f);
        lightColorR = slider("Light R", 1);
        lightColorG = slider("Light G", 0.7f);
        lightColorB = slider("Light B", 0.6f);
        lightZ = slider("Light Z", 0.07f);
        attenuationX = slider("Something something", 0.4f);
        attenuationY = slider("Attenuation*d", 3);
        attenuationZ = slider("Attenuation*d*d", 5); */
        //strength = slider("Something else", 1);
    private Label label(String text){
        final Label label = new Label("", skin);
        //label.setAlignment(Align.left);
        label.setText(text);

        Table table = new Table();
        table.add(label).width(35).space(20);
        //table.add(slider);

        //root.add(text);
        root.add(table).fill().row();
        return label;
    }
}
