package com.mygdx.game.screens.miniGames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
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
 * Created by Justin Shen on 2/22/2016.
 */
public class PasswordGame extends com.mygdx.game.screens.DefaultScreen implements InputProcessor {
    private static final String TAG = PasswordGame.class.getName();
    private TextField inputBox;
    private Label label;
    private String password;

    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            password = inputBox.getText();
            System.out.println("The password is currently: " + password);
            if (checkPassword()) {
                label.setText("Good Password!\n" + pwLog);
                inputBox.setDisabled(true);
                play.setText("Return to MorrisTown");
                play.addListener(backListener);
                inputBox.setColor(Color.CHARTREUSE);
            } else {
                label.setText("Bad Password\n" + pwLog);
                inputBox.setDisabled(false);
                inputBox.setColor(Color.RED);
            }
        }
    };

    private final InputListener backListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            gameScreen.getWorldController().getDialog().hide();
            game.setScreen(gameScreen);
            for (Actor a : stage.getActors()) {
                a.setVisible(false);
            }
            gameScreen.getWorldController().initInput();
            gameScreen.resume();
        }
    };

    private GameScreen gameScreen;
    private final MainClass game;
    private final Skin skin;
    private final Table table;
    private TextButton play;
    private String pwLog;

    public PasswordGame(Stage stage, MainClass game, GameScreen screen) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        gameScreen = screen;

        skin = new Skin(Gdx.files.internal("ui_skin/uiskin.json"));
        inputBox = new TextField("", skin);
        label = new Label("Enter a password that is more than 8 characters long and contains at least one number", skin);

        table = new Table(skin);
        play = new TextButton("Check Password!", skin);
        play.addListener(playListener);

        TextButton back = new TextButton("Return to MorrisTown", skin);
        back.addListener(backListener);
        table.row();
        table.add(label);
        table.row();
        table.add(inputBox);
        table.row();
        table.add(play).size(320, 64).space(8);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth(), Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);
    }

    public boolean checkPassword() {
        pwLog = "";
        if (password.length() > 8) {
            pwLog = pwLog + "-Password is proper length \n";
            if (password.matches(".*\\d+.*")) {  //logic needs to be changed, Regular expression needed
                pwLog = pwLog + "-Password contains a number \n";
                return true;
            } else {
                pwLog = pwLog + "-Password does not contain a number \n";
            }
            return false;
        } else {
            pwLog = pwLog + "-Password is not proper length \n";
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return stage.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return stage.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return stage.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stage.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return stage.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, .3f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void hide() {
    }
}