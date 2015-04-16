package com.emailgame.assets;

/**
 * Created by Ian on 1/13/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.util.Constants;


public class Assets implements Disposable, AssetErrorListener {

    /*
    * This class is very important, here we hold all the textures and stuff needed
    * for the game. By containing all assets in this one file we can save a huge amount
    * of processing power by restricting calls to bind images with GL
    * */
    public static final String TAG = Assets.class.getName();

    /*
    * Each of these represent the assets of different game objects
    * Each is an inner class of this class
    * */
    public DudeAsset dudeAsset;
    public Ground ground;
    public LevelDecoration decoration;
    public GroundTwo groundTwo;
    public Background back;
    public MainMap mainMap;

    /*
    * Assets and asset manager
    * */
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    /*
    * Maps
    * */
    //private final ObjectMap<String, TextureRegion> regions;


    /*
    * Assets is a singleton which means it cannot be instatiated anywhere else
    * there is only one instance and we access it whenever we want, thats why
    * the above fields are all public
    * */
    private Assets() {
    }


    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;

        // set asset manager error handler
        assetManager.setErrorListener(this);

        /*
        * Load the texture atlas
        * */
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
                TextureAtlas.class);

        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: "
                + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);



        /*
        * The atlas was loaded into the asset manager, to use it we need to
        * "get" it from the AssetManager
        * */
        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures())
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        /*
        * Now that the assets are loaded we can just load the inner classes
        * so they are available later on
        * */
        dudeAsset = new DudeAsset(atlas);
        ground = new Ground();
        groundTwo = new GroundTwo(atlas);
        decoration = new LevelDecoration(atlas);
        back = new Background();
        mainMap = new MainMap("android/assets/tiles/base.tmx");
        dudeAsset = new DudeAsset(atlas);

    }

    public MainMap getMap(){
        return mainMap;
    }

    /*
    * Outdated class
    * Originally was used for building PixMap objects on the fly
    * This is a procedural method
    * */
    private Pixmap createProceduralPixmap (int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        // Fill square with red color at 50% opacity
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();


        // Draw a yellow-colored X shape on square
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);

        // Draw a cyan-colored border around square
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);


        return pixmap;
    }

    /*
    * Dump the textures
    * */
    @Override
    public void dispose() {
        assetManager.dispose();
    }

    /*
    @Override
    public void error(String filename, Class type,
    Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '"
                + filename + "'", (Exception)throwable);
    }
    */

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }



    /*
    * Each of the following is an Inner Class which represents all textures
    * a given abstract game object will need
    * */
    public class DudeAsset {
        public final AtlasRegion body;

        public DudeAsset(TextureAtlas atlas) {

            body = atlas.findRegion("dude");
        }

    }

    public class Ground{

        public final TextureRegion ground;

        public Ground(){

            int width = 32;
            int height = 32;
            Pixmap pixmap = createProceduralPixmap(width, height);
            Texture texture = new Texture(pixmap);

            ground = new TextureRegion(texture);
        }


    }

    public class GroundTwo{

        public final AtlasRegion edge;
        public final AtlasRegion middle;

        public GroundTwo(TextureAtlas atlas) {
            edge = atlas.findRegion("ground_side");
            middle = atlas.findRegion("ground_center");
        }

    }

    public class LevelDecoration {

        public final AtlasRegion background;
        public final AtlasRegion cloudOne;
        public final AtlasRegion cloudTwo;

        public LevelDecoration(TextureAtlas atlas){
            background = atlas.findRegion("background");
            cloudOne = atlas.findRegion("cloud_one");
            cloudTwo = atlas.findRegion("cloud_two");
        }
    }

    public class Background {

        public final Texture back;

        public Background(){
            back = new Texture(Gdx.files.internal("android/assets/back.png"));
        }
    }


    public class MainMap {

        public MapProperties prop;
        public final TiledMap map;

        public MainMap(String mapInput){
            map = new TmxMapLoader().load(mapInput);
            prop = map.getProperties();

            /*
            * Use the actual map width to set GAME_WORLD on map load
            * */
            Constants.GAME_WORLD = prop.get("width", Integer.class);
        }
    }



}