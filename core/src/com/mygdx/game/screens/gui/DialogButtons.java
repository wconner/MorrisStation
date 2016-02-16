package com.mygdx.game.screens.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.model.WorldController;

import java.util.ArrayList;

/**
 * Created by Jshen94 on 12/7/2015.
 */
public class DialogButtons implements InputProcessor{

    /**
     * For building a window and assigning it a skin
     */
    private Window window;
    private Skin skin;
    private Stage stage;
    private WorldController worldController;

    /**
     * Text labels
     */
    private TextButton option1; private TextButton option2; private TextButton option3; private TextButton option4;

    private boolean hidden;

    Table root;

    /**
     * Default constructor
     */
    public DialogButtons(Stage stage, WorldController worldController) {
        this.worldController = worldController;
        TextureAtlas atlas = new TextureAtlas("android/assets/ui_skin/uiskin.atlas");
        skin = new Skin(Gdx.files.local("android/assets/ui_skin/uiskin.json"), atlas);
        skin.addRegions(atlas);
        hidden = true;
        this.stage = stage;
        option1 = new TextButton("",skin); option2 = new TextButton("",skin); option3 = new TextButton("",skin); option4 = new TextButton("",skin);
        option1.addListener(new OptionListener(1)); option2.addListener(new OptionListener(2));
        option3.addListener(new OptionListener(3)); option4.addListener(new OptionListener(4));
    }

    /**
     * Build a basic window
     */
    public Window makeWindow(ArrayList<String> dialog) {

        Gdx.input.setInputProcessor(stage);
        window = new Window("Dialog Options", skin);

        //window.row().prefWidth(Gdx.graphics.getWidth() * 0.5f);
        //window.row().prefHeight(Gdx.graphics.getHeight() * 0.5f);
        root = new Table(skin);
        switch(dialog.size()) { /**variable number of options based on on the amount of answers */
            case 4:
                option4.setText(dialog.get(3));
                option4.addListener(option4.getClickListener());
                root.add(option4);
            case 3:
                option3.setText(dialog.get(2));
                option3.addListener(option3.getClickListener());
                root.row();
                root.add(option3);
            case 2:
                option2.setText(dialog.get(1));
                option2.addListener(option2.getClickListener());
                root.row();
                root.add(option2);
            case 1:
                option1.setText(dialog.get(0));
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
        window.setBounds(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() * .34f), 90, Gdx.graphics.getWidth() / 3, 140);
        window.left();
        window.setVisible(true);
        window.setName("DB");
        return window;
    }

    public boolean isHidden() {
        return hidden;
    }

    /**
     * flips the boolean for hidden
     */
    public void hide() {
        hidden = !hidden;
        window.setVisible(hidden);
    }

    /**
     * Getter for window
     */
    public Window getWindow() {
        return window;
    }

    /**
     * stage.act() calls the act method on all widgets of the stage
     */
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

    class OptionListener extends InputListener{
        int optionNum;

        public OptionListener(int optionNum){ this.optionNum = optionNum;}

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            worldController.updateDialog(optionNum);
            Gdx.app.debug("DialogButton : OptionListener", "Button number " + optionNum + " hit.");
            return super.touchDown(event, x, y, pointer, button);
        }
    }
}
