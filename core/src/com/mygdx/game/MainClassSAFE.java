/*
package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Assets;

public class MainClass extends ApplicationAdapter, Screen {

    */
/*
    * MVC basis
    * *//*

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    //not implemented
    private boolean paused;


    */
/*
    * Entry point for mostly everything
    * *//*

    @Override
    public void create () {

        // Set Libgdx log level to DEBUG
        Gdx.app.setLogLevel(Application.LOG_DEBUG);


		*/
/*
		* Loads the assets class
		* Assets is a singleton and can only be instatiated once, all subsequent
		* calls can be made directly to the assets class
		* Must be initialized before the WorldController for assets to be available
		* *//*

        Assets.instance.loadAssets(new AssetManager());


        // Initialize controller and renderer
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);

        // Game world is active on start
        paused = false;
    }


    */
/*
    * Render takes over after create() and is part of the main loop
    * *//*

    @Override
    public void render () {

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




    */
/*
    * The following are implemented from ApplicationListener
    * *//*

    @Override
    public void resize (int width, int height) {
        worldRenderer.resize(width, height);
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
}
*/
