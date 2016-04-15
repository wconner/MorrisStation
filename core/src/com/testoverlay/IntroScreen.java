package com.testoverlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainClass;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.TouchUpListener;

/**
 * Created by Bill on 4/13/16.
 * Intro screen which shows the beginning dialogs.
 */
public class IntroScreen extends DefaultScreen implements InputProcessor {

    private final MainClass game;
    private final Skin skin;
    private final Table table;
    private final Label introText;

    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            switchScreen(new GameScreen(new Stage(), game));
        }
    };

    public IntroScreen (Stage stage, MainClass game){
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        TextButton play = new TextButton("I'm ready!", skin);
        introText = new Label(generateIntroText(), skin);

        play.addListener(playListener);

        table = new Table(skin);
        table.row();
        table.add(introText);
        table.row();
        table.add(play).size(320, 64).space(8);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
    }

    private String generateIntroText(){
        return "You've recently been assigned to be one of a few astronauts manning the Morris Station, a station critical" +
                "to intergalactic communications between the Milky Way and Andromeda galaxies";
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
        Gdx.gl.glClearColor(.5f, .7f, .5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void hide() {

    }

    public AlphaAction fadeOut(float duration) {
        AlphaAction fadeOut = new AlphaAction();
        fadeOut.setAlpha(0f);
        fadeOut.setDuration(duration);

        return fadeOut;
    }

    public void switchScreen(final com.mygdx.game.screens.DefaultScreen newScreen){
        stage.getRoot().getColor().a = 1f;
        RunnableAction runnableAction = new RunnableAction();
        runnableAction.setRunnable(() -> game.setScreen(newScreen));
        fadeOut(.5f);
        runnableAction.run();
    }
}
