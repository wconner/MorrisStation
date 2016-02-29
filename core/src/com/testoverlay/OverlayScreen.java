package com.testoverlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.TouchUpListener;
import com.mygdx.game.MainClass;

/**
 * Created by Jshen94 on 11/9/2015.
 */
public class OverlayScreen extends DefaultScreen implements InputProcessor {
    private static final String TAG = OverlayScreen.class.getName();

    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //game.setScreen(new GameScreen(stage,game));
            //gameScreen.resume();
            gameScreen.getWorldController().getDialog().hide();
            gameScreen.getWorldController().initInput();
            gameScreen.resume();
            game.setScreen(gameScreen);
            table.setVisible(false);

        }
    };

    private final InputListener menuListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(new PasswordGame(stage,game,gameScreen));
            table.setVisible(false);
        }
    };

    private final InputListener exitListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.exit();
        }
    };

    private GameScreen gameScreen;
    private final MainClass game;
    private final Skin skin;
    private final Table table;


    public OverlayScreen(Stage stage, MainClass game, GameScreen screen) {
        super(stage,game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        gameScreen = screen;
        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        TextButton play = new TextButton("Return to Morris Town", skin);
        TextButton pwGame = new TextButton("test PWgame", skin);
        TextButton exit = new TextButton("Exit", skin);

        play.addListener(playListener);
        pwGame.addListener(menuListener);
        exit.addListener(exitListener);

        table = new Table(skin);
        table.row();
        table.add(play).size(320, 64).space(8);
        table.row();
        table.add(pwGame).size(320, 64).space(8);
        table.row();
        table.add(exit).size(320, 64).space(8);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);


    }
    public OverlayScreen(Stage stage, MainClass game) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        TextButton play = new TextButton("Return to Morris Town", skin);
        TextButton pwGame = new TextButton("Password Game", skin);
        TextButton exit = new TextButton("Exit", skin);

        play.addListener(playListener);
        pwGame.addListener(menuListener);
        exit.addListener(exitListener);

        table = new Table(skin);
        //table.add(rorLogo).size(600, 200).space(32);
        table.row();
        table.add(play).size(320, 64).space(8);
        table.row();
        table.add(pwGame).size(320, 64).space(8);
        table.row();
        table.add(exit).size(320, 64).space(8);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
        //hide();

    }

  /*  public void addGameScreen(GameScreen screen) {
        gameScreen = screen;
    }*/

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
    public void show(){

    }
/*
    public MoveByAction slideUp() {
        MoveByAction mAction = new MoveByAction(0,500);
        return mAction;
    }*/




    @Override
    public void render(float delta){
        //delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(.5f, .7f, .5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*time += delta;

        if (time < 1f)
            return;*/

        stage.draw();


    }

    @Override
    public void hide(){

    }
}
