package com.testoverlay;

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
 * Created by Justin Shen on 2/22/2016.
 */
public class PasswordGame extends DefaultScreen implements InputProcessor {
    private static final String TAG = PasswordGame.class.getName();
    private TextField inputBox;
    private Label label;
    private String password;
    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            password = inputBox.getText();
            System.out.println("The password is currently: " + password);
            if(checkPassword()){
                label.setText("Good Password!\n" + pwLog);
            }
            else {
                label.setText("Bad Password\n" + pwLog);

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
    private String pwLog;

    public PasswordGame(Stage stage, MainClass game,GameScreen screen) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        gameScreen = screen;

        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        inputBox = new TextField("",skin);
        label = new Label("", skin);

        table = new Table(skin);
        TextButton play = new TextButton("Check Password!", skin);
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
        if(password.length() > 8){
            pwLog = pwLog + "-Password is proper length \n";
            if(password.matches(".*\\d+.*")){  //logic needs to be changed, Regular expression needed
                pwLog = pwLog + "-Password contains a number \n";
                return true;
            }
            else {
                pwLog = pwLog + "-Password does not contain a number \n";
            }
            return false;
        }
        else {
            pwLog = pwLog + "-Password is not proper length \n";
        }
        return false;
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


