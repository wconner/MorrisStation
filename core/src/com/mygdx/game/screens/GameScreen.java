package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Assets;
import com.mygdx.game.model.WorldController;
import com.mygdx.game.WorldRenderer;

public class GameScreen extends DefaultScreen implements InputProcessor {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    //not implemented
    private boolean paused;

    public GameScreen(Stage stage, Game game) {
        super(stage, game);
        Assets.instance.loadAssets(new AssetManager());

        // Initialize controller and renderer
        worldController = new WorldController(stage);
        worldRenderer = new WorldRenderer(worldController);

        // Game world is active on start
        paused = false;
    }

    /*
    * Clear the screen, laste method draws the new updated world
    * */
    @Override
    public void render (float delta) {

        // Update game world by the time that has passed
        // since last rendered frame.
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


    @Override
    public void resize (int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show() {

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
        Assets.instance.loadAssets(new AssetManager());
        paused = false;
    }
    @Override
    public void dispose () {

        worldRenderer.dispose();
        Assets.instance.dispose();
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
}
