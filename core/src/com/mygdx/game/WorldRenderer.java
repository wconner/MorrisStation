package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.AbstractDynamicObject;
import com.mygdx.game.model.WorldController;
import com.mygdx.game.util.Constants;

/**
 * Created by Ian on 12/22/2014.
 */
public class WorldRenderer implements Disposable {


    public static final String TAG = WorldRenderer.class.getName();

    /**
    * Renderer has access to the controller
    */
    private WorldController worldController;

    /**
    * These are the main building blocks of all rendering
    * Stage needs to be local, but points to Controller from MVC
    */
    private OrthographicCamera viewportCamera;
    private SpriteBatch batch;
    private TiledMapRenderer tileRenderer;
    private ShapeRenderer shaper;
    private Stage stage;
    private Box2DDebugRenderer debugRenderer;

    /**
    * Default constructor
    */
    public WorldRenderer(WorldController worldController){
        this.worldController = worldController;
        init();
    }

    public void setLevel(WorldController worldController){
        this.worldController = worldController;
        init();
    }

    /**
    * Initiate all needed rendering utilities
    */
    private void init (){

        //set stage from controller
        stage = worldController.getStage();
        debugRenderer = new Box2DDebugRenderer();

        /**
        * For rendering
        * Batch renders sprites
        * shaper renders the grid
        * tileRenderer is for maps
        * TODO move tiledMapRenderer to new class
        */
        batch = new SpriteBatch();
        shaper = new ShapeRenderer();
        tileRenderer = new OrthogonalTiledMapRenderer(worldController.level.getMap(), Constants.UNIT_SCALE);

        /**
        * Sets the main camera
        */
        viewportCamera = new OrthographicCamera();
        viewportCamera.setToOrtho(false,Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_HEIGHT);
        viewportCamera.position.set(0, 0, 0);
        viewportCamera.update();


    }


    /**
    * render() is basically a helper method of renderWorld
    * renderDisplay() is on tip to ensure it is drawn "on top"
    */
    public void render (){
        renderWorld(batch);
        renderDisplay();
    }


    /**
    * This is the big baby, all the rendering processing goes through
    * here first, mucho importante
    */
    private void renderWorld (SpriteBatch batch){
        /**
        * I'm still not totally sure how applyTo() operates
        */
        worldController.cameraHelper.applyTo(viewportCamera);
        batch.setProjectionMatrix(viewportCamera.combined);

        /**
        * ehhhhhhh
        */
        tileRenderer = new OrthogonalTiledMapRenderer(worldController.getLevel().getMap(), Constants.UNIT_SCALE);
        tileRenderer.setView(viewportCamera);

        /**
         * As of right now (1/15/16), each 'level' or map should have 5 layers + the obstacle layer
         * 2 Background layers (Ground and Ground Detail)
         * 3 Foreground layers (Object, Object Detail, and High)
         * Obstacle layer must be named 'Obstacles'
         * If you don't need all 5 layers, just make the layers empty.
         */

            int[] bgLayers = {0,1};
            int[] fgLayers = {2,3,4,5};
            tileRenderer.render(bgLayers);

            batch.begin();

            for(AbstractDynamicObject dudes : worldController.actors)
                dudes.render(batch);

            batch.end();

            tileRenderer.render(fgLayers);

        /**
        * Render the debug grid
        */
        renderGrid((int) Constants.GAME_WORLD);
        renderWorldBounds();

        debugRenderer.render(GameInstance.getInstance().world, viewportCamera.combined);
    }

    /**
    * Debug method
    * This will draw a grid representing the game world which extends from (0.0) to the
    * dimensions of the GAME_WORLD constants.
    * TODO Constrain camera to the size of the game world --> maybe by using clamp in applyTo()
    */
    private void renderGrid(int divisions){

        shaper.setProjectionMatrix(viewportCamera.combined);
        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(new Color(Color.BLACK));

        float deltaX = Constants.UNIT_SCALE;

        //draw horizontal grid
        for(int i = 0; i < divisions + 1; i++){
            shaper.line(i, 0, i, Constants.GAME_WORLD);
            i += deltaX;
            //Gdx.app.debug(TAG, Integer.toString(i));
        }

        //draw vertical grid for use in rendering
        for(int i = 0; i < divisions + 1; i++){
            shaper.line(0, i, Constants.GAME_WORLD, i);
            i += deltaX;
        }
        shaper.end();
    }

    private void renderWorldBounds(){

        shaper.setProjectionMatrix(viewportCamera.combined);
        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(new Color(Color.CYAN));
        shaper.rect(0, 0, Constants.GAME_WORLD, Constants.GAME_WORLD);
        shaper.end();
    }

    public OrthographicCamera getViewportCamera(){return viewportCamera;
    }

    /**
    * Call the act() method for the stage
    */
    public void renderDisplay(){
        stage.act();
        stage.draw();
    }

    public void resize (int width, int height){
        viewportCamera.viewportHeight = (Constants.VIEWPORT_WIDTH / width) * height;
        viewportCamera.update();
    }

    @Override
    public void dispose (){
        batch.dispose();
    }
}
