package com.mygdx.game.screens.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.util.DialogController;
/**
 * Created by Jshen94 on 12/7/2015.
 */
public class DialogButtons implements InputProcessor{

    /*
    * For building a window and assigning it a skin
    * */
    private Window window;
    private Skin skin;
    private TextureAtlas atlas;
    private DialogController dialogController;

    /*
    * Text labels
    * */
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;

    private boolean hidden;

    Table root;

    private final ClickListener buttonListener;

    /*
    * Default constructor
    * */
    public DialogButtons(DialogController dialog) {
        atlas = new TextureAtlas("android/assets/ui_skin/uiskin.atlas");
        skin = new Skin(Gdx.files.local("android/assets/ui_skin/uiskin.json"), atlas);
        skin.addRegions(atlas);
        hidden = true;
        dialogController = dialog;
        option1 = new Button(skin);
        option2 = new Button(skin);
        option3 = new Button(skin);
        option4 = new Button(skin);
        buttonListener = new ClickListener();
    }

    /*
    * Build a basic window
    * dialog is temp param
    * */
    public Window makeWindow(int num, String[] dialog) {

        window = new Window("Dialog Options", skin);

        //window.row().prefWidth(Gdx.graphics.getWidth() * 0.5f);
        //window.row().prefHeight(Gdx.graphics.getHeight() * 0.5f);
        root = new Table(skin);
        switch(num) {
            case 4:
                option4.add(dialog[3]);
                option4.addListener(option4.getClickListener());
                root.add(option4);
            case 3:
                option3.add(dialog[2]);
                option3.addListener(option3.getClickListener());
                root.row();
                root.add(option3);
            case 2:
                option2.add(dialog[1]);
                option2.addListener(option2.getClickListener());
                root.row();
                root.add(option2);
            case 1: option1.add(dialog[0]);
                option1.addListener(option1.getClickListener());
                root.row();
                root.add(option1);
                break;

            default: break;
        }
        root.left();
        root.pad(5,0,5,5);
        window.add(root);
        window.pack();
        window.setBounds(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() *.34f),90, Gdx.graphics.getWidth()/3, 140);
        window.left();
        return window;
    }

    public boolean isHidden() {
        return hidden;
    }

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

    /*
    * Getter for window
    * */

    public Window getWindow() {
        return window;
    }


    /*
    * stage.act() calls the act method on all widgets of the stage
    * */
    public void update(Stage stage) {
        stage.act();
        stage.draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}


