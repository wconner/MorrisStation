package com.testoverlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.MainClass;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.TouchUpListener;

import java.util.ArrayList;

/**
 * Created by Justin Shen on 2/22/2016.
 */
public class MastermindGame extends DefaultScreen implements InputProcessor {
    private static final String TAG = MastermindGame.class.getName();
    private Label label;
    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            password = "";
            for(int i = 0; i < pw.length(); i++) {
                if (!inputs.get(i).getText().equals("")) {
                    password += inputs.get(i).getText();
                    if (pw.charAt(i) == password.charAt(i)) {
                        inputs.get(i).setDisabled(true);
                        inputs.get(i).setColor(Color.CHARTREUSE);
                    } else {
                        inputs.get(i).setColor(Color.RED);
                    }

                    if (password.equals(pw))
                        label.setText("Nice job! You hacked the password! \n The password is: " + password);
                    else {
                        label.setText("The correct numbers have been locked in, please fix the remaining numbers");
                    }
                }
                else {
                    inputs.get(i).setColor(Color.GOLDENROD);
                    label.setText("Please fill in every entry");
                }

            }
        }
    };
    private final InputListener backListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            gameScreen.getWorldController().getDialog().hide();
            game.setScreen(gameScreen);
            for(Actor a: stage.getActors()) {
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
    private final Table buttonTable;
    private final String pw;
    private String password;
    private ArrayList<TextField> inputs;

    public MastermindGame(Stage stage, MainClass game, GameScreen screen, String pass) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        gameScreen = screen;

        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        label = new Label("", skin);
        table = new Table(skin);
        buttonTable = new Table(skin);

        inputs = new ArrayList<>();

        TextButton play = new TextButton("Test Password!", skin);
        play.addListener(playListener);

        TextButton back = new TextButton("Return to MorrisTown", skin);
        back.addListener(backListener);
        table.row();
        table.add(label);
        table.row();
        for (int i = 0; i< 4; i++){
            inputs.add(new TextField("",skin));
            inputs.get(i).setMaxLength(1);
            inputs.get(i).setWidth(.5f);
            inputs.get(i).setScaleX(.5f);
            inputs.get(i).setSize(.1f,1f);
            inputs.get(i).pack();
            buttonTable.add(inputs.get(i));
        }
        table.add(buttonTable);
        table.row();
        table.add(play).size(320, 64).space(8);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth(), Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);

        pw = pass;
    }


    public String getPassword() {
        return password;
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
        //delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(.5f, .7f, .5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*time += delta;

        if (time < 1f)
            return;*/

        stage.draw();


    }

    @Override
    public void hide() {

    }
}

