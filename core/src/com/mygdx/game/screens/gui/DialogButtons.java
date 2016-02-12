package com.mygdx.game.screens.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.util.DialogController;

import java.util.ArrayList;

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
    private Stage stage;

    /*
    * Text labels
    * */
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;

    private int choice;

    private boolean hidden;

    Table root;

    private final ClickListener buttonListener;

    /*
    * Default constructor
    * */
    public DialogButtons(Stage stage) {
        atlas = new TextureAtlas("android/assets/ui_skin/uiskin.atlas");
        skin = new Skin(Gdx.files.local("android/assets/ui_skin/uiskin.json"), atlas);
        skin.addRegions(atlas);
        hidden = true;
        this.stage = stage;
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
    public Window makeWindow(ArrayList<String> dialog) {
        int num = dialog.size();
        choice = 0; //initialized to 0, then set to 1-4 by the button listeners
        Gdx.input.setInputProcessor(stage);
        window = new Window("Dialog Options", skin);

        //window.row().prefWidth(Gdx.graphics.getWidth() * 0.5f);
        //window.row().prefHeight(Gdx.graphics.getHeight() * 0.5f);
        root = new Table(skin);
        switch(num) { /**variable number of options based on on the amount of answers */
            case 4:
                option4.add(dialog.get(3));
                option4.addListener(choice4);
                root.add(option4);
            case 3:
                option3.add(dialog.get(2));
                option3.addListener(choice3);
                root.row();
                root.add(option3);
            case 2:
                option2.add(dialog.get(1));
                option2.addListener(choice2);
                root.row();
                root.add(option2);
            case 1:
                option1.add(dialog.get(0));
                option1.addListener(choice1);
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
        window.setVisible(true);
        return window;
    }

    public boolean isHidden() {
        return hidden;
    }
    
    public int getChoice()
    {
        return choice;
    }

    /**
     * flips the boolean for hidden
     */
    public void hide() {
        if (!hidden)
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

    private final InputListener choice1 = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            choice = 1;
            System.out.println("choice: " + choice);
        }
    };

    private final InputListener choice2 = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            choice = 2;
            System.out.println("choice: " + choice);
        }
    };

    private final InputListener choice3 = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            choice = 3;
            System.out.println("choice: " + choice);
        }
    };

    private final InputListener choice4 = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            choice = 4;
            System.out.println("choice: " + choice);
        }
    };

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
        return stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
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


