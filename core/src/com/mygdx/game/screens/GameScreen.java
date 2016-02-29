package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MainClass;
import com.mygdx.game.WorldRenderer;
import com.mygdx.game.levels.BaseLevel;
import com.mygdx.game.levels.BedroomLevel;
import com.mygdx.game.levels.FirstLevel;
import com.mygdx.game.levels.Level;
import com.mygdx.game.model.WorldController;
import com.mygdx.game.screens.gui.TouchUpListener;
import com.mygdx.game.util.JsonTest;
import com.testoverlay.OverlayScreen;

import java.util.ArrayList;


public class GameScreen extends DefaultScreen implements InputProcessor {

    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private MainClass game;
    private Group phoneDisplay;
    private ArrayList<Level> levels;


    private boolean paused;
    // field to disable manual pausing
    private boolean pauseEnabled;

    public GameScreen(Stage stage, MainClass game) {
        super(stage, game);
        new JsonTest();     /** Testing remove this line later */
        this.game = game;
        initLevels();
        setLevel(1);                                        /** setLevel now initializes worldController and worldRenderer */

        phoneDisplay = new Group();
        phoneDisplay.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Game world is active on start
        phoneDisplay.setPosition(Gdx.graphics.getWidth()/2,0);

        //createPhoneButtons
        Skin skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        Window window = new Window("Phone", skin);
        TextButton play = new TextButton("Return to Morris Town", skin);
        TextButton email = new TextButton("E-Mail", skin);
        TextButton pwGame = new TextButton("Password Game", skin);
        TextButton exit = new TextButton("Exit", skin);

        play.addListener(playListener);
        email.addListener(emailListener);
        pwGame.addListener(pwGameListener);
        exit.addListener(exitListener);

        Table table = new Table(skin);
        table.row();
        table.add(play).size(200, 48).space(8);
        table.row();
        table.add(email).size(200, 48).space(8);
        table.row();
        table.add(exit).size(150, 32).space(8);
        table.pad(14, 14, 14, 14).defaults().space(16);

        window.add(table);
        window.setPosition((Gdx.graphics.getWidth() / 2) - window.getWidth(), (Gdx.graphics.getHeight() / 2) - window.getHeight());
        window.pad(20, 20, 100, 20);
        window.pack();
        window.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("android/assets/phonebackground.png")))));
                phoneDisplay.addActor(window);
        stage.addActor(phoneDisplay);
        phoneDisplay.setVisible(false);
        paused = false;
        pauseEnabled = true;
    }

    private void initLevels(){
        levels = new ArrayList<>();
        levels.add(new FirstLevel());
        levels.add(new BedroomLevel());
        levels.add(new BaseLevel());
    }

    /**
    * Clear the screen, last method draws the new updated world
    */
    @Override
    public void render(float delta) {

        // Update game world by the time that has passed
        // since last rendered frame.
        handleDebugInput();
        if (!paused) {
            // Update game
            worldController.update(Gdx.graphics.getDeltaTime());
        }
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game world to screen
        worldRenderer.render();
    }

    public void setLevel(int level) {
        levels.get(level).addActors();
        worldController = new WorldController(stage, this, levels.get(level));
        if (worldRenderer == null){                             /** For first time you load a level when there is no worldcontroller */
            worldRenderer = new WorldRenderer(worldController);
            worldRenderer.setLevel(worldController);
        }
        else
            worldRenderer.setLevel(worldController);
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public Group getPhoneDisplay() { return phoneDisplay; }
    public Game getGame() {
        return game;
    }

    @Override
    public void resize (int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show() {
        AlphaAction fadeIn = new AlphaAction();
        fadeIn.setAlpha(1f);
        fadeIn.setDuration(.7f);
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(fadeIn);
    }

    @Override
    public void hide() {
    }


    @Override
    public void pause () {
        paused = true;
    }

    //android requires assets be reloaded on resume
    @Override
    public void resume () {
        paused = false;
    }
    @Override
    public void dispose () {
        worldRenderer.dispose();
    }
    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            toggle();
        }
    };

    private final InputListener emailListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            screenSwap();
        }
    };

    private final InputListener pwGameListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            pwGameSwap();
        }
    };


    private final InputListener exitListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.exit();
        }
    };


    public void pauseSwap() {
        if(paused)
            resume();
        else
            pause();
    }
    public void unPause() {
        resume();
    }
    public void screenSwap() {
        pause();
        //hide();
        worldController.getDialog().hide();
        phoneDisplay.setVisible(false);
        game.setScreen(new OverlayScreen(stage,game,this));
    }
    public void pwGameSwap() {
        pause();
        //hide();
        worldController.getDialog().hide();
        phoneDisplay.setVisible(false);
        game.setScreen(new OverlayScreen(stage,game,this));
    }

    //move all phone stuff to another class maybe
    private void toggle() {
        MoveToAction moveToAction = new MoveToAction();
        ParallelAction parallelAction = new ParallelAction();
        if (phoneDisplay.isVisible()) {
            //moveToAction.setX(Gdx.graphics.getWidth()/2);
            DelayAction delayAction = new DelayAction();
            AlphaAction fadeOut = new AlphaAction();
            delayAction.setDuration(.5f);
            fadeOut.setAlpha(0f);
            fadeOut.setDuration(.3f);
            moveToAction.setY(-(Gdx.graphics.getHeight()));
            moveToAction.setDuration(.5f);
            parallelAction.addAction(moveToAction);
            parallelAction.addAction(fadeOut);
            SequenceAction sequenceAction = new SequenceAction();
            RunnableAction runnableAction = new RunnableAction();
            runnableAction.setRunnable(new Runnable() {
                @Override
                public void run() {
                    phoneDisplay.setVisible(false);
                }
            });
            sequenceAction.addAction(parallelAction);
            sequenceAction.addAction(delayAction);
            sequenceAction.addAction(runnableAction);
            phoneDisplay.addAction(sequenceAction);
            worldController.initInput();
            pauseEnabled = true;

        }
        else {
            Actions a = new Actions();
            a.alpha(0f);
            phoneDisplay.addAction(a.alpha(0f));
            phoneDisplay.setPosition(0,-(Gdx.graphics.getHeight()));
            AlphaAction fadeIn = new AlphaAction();
            fadeIn.setAlpha(1f);
            fadeIn.setDuration(.5f);
            moveToAction.setY(0);
            moveToAction.setDuration(.5f);
            parallelAction.addAction(moveToAction);
            parallelAction.addAction(fadeIn);
            phoneDisplay.addAction(parallelAction);
            phoneDisplay.setVisible(true);
            Gdx.input.setInputProcessor(stage);
            pauseEnabled = false;

        }
        pauseSwap();
    }

    private void handleDebugInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            screenSwap();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            if(pauseEnabled)
                pauseSwap();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            if(pauseEnabled)
                toggle();
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) { return false; }

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
