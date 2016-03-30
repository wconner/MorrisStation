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

public class EmailGame extends DefaultScreen implements InputProcessor {
    private static final String TAG = PasswordGame.class.getName();
   // private TextField inputBox;
    private Label label;
    private Label instructions;
    private String password;
    private final InputListener goodListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                label.setText("No Viruses!\n");


        }
    };
    private final InputListener badListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            label.setText("Bad Email!\n");


        }
    };

    private final InputListener checkListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            checkEmails();


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
    //private String pwLog;

    public EmailGame(Stage stage, MainClass game,GameScreen screen) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        gameScreen = screen;

        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        //inputBox = new TextField("",skin);
        label = new Label("", skin);
        instructions = new Label("You've got new emails!", skin);

        table = new Table(skin);
        Table subTA = new Table(skin);
        Table subTB = new Table(skin);
        Table subTC = new Table(skin);
        TextButton goodEmail = new TextButton("gmail", skin);
        TextButton badEmail = new TextButton("bmail", skin);
        TextButton checkEmail = new TextButton("Check Selected Emails", skin);
        CheckBox gCheck = new CheckBox("", skin);
        CheckBox bCheck = new CheckBox("", skin);
        goodEmail.addListener(goodListener);
        badEmail.addListener(badListener);

        TextButton back = new TextButton("Return to MorrisTown", skin);
        back.addListener(backListener);
        table.row();
        table.add(label);
        table.row();
        table.add(instructions);
        table.row();
        subTA.add(gCheck);
        subTA.add(goodEmail).size(320, 30).space(8);
        table.add(subTA);
        table.row();
        subTB.add(bCheck);
        subTB.add(badEmail).size(320, 30).space(8);
        table.add(subTB);
        table.row();
        table.add(checkEmail).size(320, 30).space(8);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth(), Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);
    }

    public boolean checkEmails() {

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


