package com.mygdx.game.screens;

/**
 * Created by Ian on 4/2/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainClass;
import com.mygdx.game.screens.gui.TouchUpListener;
import com.testoverlay.OverlayScreen;

/**
 * @author matheusdev
 *
 */
public class MainMenu extends DefaultScreen implements InputProcessor {

    private static final String TAG = MainMenu.class.getName();

    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            switchScreen(new GameScreen(new Stage(),game));
        }
    };
    private final InputListener emailListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(new OverlayScreen(new Stage(), game));
        }
    };
    private final InputListener shooterListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            //game.setScreen(new ShooterScreen(new Stage(), game));
        }
    };
    private final InputListener exitListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.exit();
        }
    };

    private final MainClass game;

    private final Skin skin;
    private final Table table;

    public MainMenu(final MainClass game) {
        super(new Stage(), game);
        Gdx.input.setInputProcessor(this);
        this.game = game;

        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));

        TextButton play = new TextButton("Morris Town", skin);
        TextButton email = new TextButton("Test", skin);
        //TextButton shooter = new TextButton("Shooter", skin);
        TextButton exit = new TextButton("Exit", skin);

        play.addListener(playListener);
        email.addListener(emailListener);
        //shooter.addListener(shooterListener);
        exit.addListener(exitListener);

        table = new Table(skin);
        table.row();
        table.add(play).size(320, 64).space(8);
        table.row();
        table.add(email).size(320, 64).space(8);
        table.row();
        table.add(exit).size(320, 64).space(8);
        table.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stage.addActor(table);
    }

    public MainClass getGame() {
        return game;
    }

    @Override
    public void render(float delta) {
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    public void switchScreen(final DefaultScreen newScreen){
        stage.getRoot().getColor().a = 1f;
        RunnableAction runnableAction = new RunnableAction();
        runnableAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                game.setScreen(newScreen);
            }
        });
        fadeOut(.5f);
        runnableAction.run();
        /*SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(fadeOut(0.5f));
        sequenceAction.addAction(runnableAction);
        stage.getRoot().addAction(sequenceAction);*/
    }





    @Override
    public void resize(int width, int height) {
        //stage.setViewport(width, height, true);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
        stage.getRoot().getColor().a = 1f;
        stage.getRoot().addAction(fadeOut(1f));
    }
    public AlphaAction fadeOut(float duration) {
        AlphaAction fadeOut = new AlphaAction();
        fadeOut.setAlpha(0f);
        fadeOut.setDuration(duration);

        return fadeOut;
    }

    @Override
    public void dispose() {
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

    /*@Override
    public boolean isParentVisible() {
        return false;
    }*/

}
