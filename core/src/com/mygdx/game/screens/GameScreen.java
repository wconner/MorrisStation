package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MainClass;
import com.mygdx.game.WorldController;
import com.mygdx.game.WorldRenderer;
import com.mygdx.game.levels.BaseLevel;
import com.mygdx.game.levels.BedroomLevel;
import com.mygdx.game.levels.Level;
import com.mygdx.game.screens.gui.TouchUpListener;
import com.mygdx.game.screens.miniGames.EmailGame;

import java.util.ArrayList;

public class GameScreen extends com.mygdx.game.screens.DefaultScreen implements InputProcessor {

    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private MainClass game;
    private Group phoneDisplay;
    private ArrayList<Level> levels;

    public static final World world = new World(new Vector2(0, 0), false);

    private boolean paused;

    public GameScreen(Stage stage, MainClass game) {
        super(stage, game);
        this.game = game;
        initLevels();
        setLevel(0);                                        /** setLevel now initializes worldController and worldRenderer */

        phoneDisplay = new Group();
        phoneDisplay.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Game world is active on start
        phoneDisplay.setPosition(Gdx.graphics.getWidth() / 2, 0);

        //@TODO Move phone to its own separate class
        //createPhoneButtons
        Skin skin = new Skin(Gdx.files.internal("ui_skin/uiskin.json"));
        Window window = new Window("Phone", skin);
        TextButton play = new TextButton("Return to Morris Station", skin);
        TextButton email = new TextButton("E-Mail", skin);
        TextButton pwGame = new TextButton("Password Game", skin);
        TextButton exit = new TextButton("Quit Game", skin);

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
        window.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backgrounds/phonebackground.png")))));
        phoneDisplay.addActor(window);
        stage.addActor(phoneDisplay);
        phoneDisplay.setVisible(false);
        paused = false;
    }

    private void initLevels() {
        levels = new ArrayList<>();
        levels.add(new BedroomLevel());
        levels.add(new BaseLevel());
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    /**
     * Clear the screen, last method draws the new updated world
     */
    @Override
    public void render(float delta) {

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
        if (worldRenderer == null) {                             /** For first time you load a level when there is no worldcontroller */
            worldRenderer = new WorldRenderer(worldController);
            worldRenderer.setLevel(worldController);
        } else
            worldRenderer.setLevel(worldController);
        game.setScreen(this);
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void resize(int width, int height) {
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
    public void pause() {
        paused = true;
    }

    public void unPause() { paused = false;}

    //android requires assets be reloaded on resume
    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void dispose() {
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
            pause();
            worldController.getDialog().hide();
            phoneDisplay.setVisible(false);
            screenSwap("EmailGame");
            //game.setScreen(new EmailGame(stage, game, worldController.getGameScreen()));
        }
    };

    private final InputListener pwGameListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            screenSwap("Password");
        }
    };


    private final InputListener exitListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.exit();
        }
    };

    public void screenSwap(String type) {
        pause();
        worldController.getDialog().hide();
        phoneDisplay.setVisible(false);
        game.setScreen(new TransitionScreen(stage, game, this, type));
    }

    public void messageScreenSwap(String type, EmailGame g) {
        g.returnToGameWrap();
        pause();
        worldController.getDialog().hide();
        phoneDisplay.setVisible(false);
        TransitionScreen t = new TransitionScreen(stage, game, this, type);
        t.setActiveEGame(g);
        game.setScreen(t);

    }

    //@TODO move all phone stuff to another class
    public void toggle() {
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
            unPause();
        } else {
            Actions a = new Actions();
            a.alpha(0f);
            phoneDisplay.addAction(a.alpha(0f));
            phoneDisplay.setPosition(0, -(Gdx.graphics.getHeight()));
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
            pause();
        }
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