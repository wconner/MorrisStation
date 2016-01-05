package com.mygdx.game;

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
import com.mygdx.game.entities.NPC;
import com.mygdx.game.util.Constants;


public class Assets implements Disposable, AssetErrorListener {

    /**
    * This class is very important, here we hold all the textures and stuff needed
    * for the game. By containing all assets in this one file we can save a huge amount
    * of processing power by restricting calls to bind images with GL
    */
    public static final String TAG = Assets.class.getName();

    /**
    * Each of these represent the assets of different game objects
    * Each is an inner class of this class
    */
    public DudeAsset dudeAsset;
    public NPC npc;
    public NPC npc2;
    public MainMap mainMap;

    /**
    * Assets and asset manager
    * Assets is a singleton, all calls are made on the final of the instance directly
    */
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    /**
    * Assets is a singleton which means it cannot be instatiated anywhere else
    * there is only one instance and we access it whenever we want, thats why
    * the above fields are all public
    */
    private Assets() {
    }


    public void loadAssets(AssetManager assetManager) {
        this.assetManager = assetManager;

        // set asset manager error handler
        assetManager.setErrorListener(this);

        /**
        * Load the texture atlas
        */
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
                TextureAtlas.class);

        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: "
                + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);


        /**
        * The atlas was loaded into the asset manager, to use it we need to
        * "get" it from the AssetManager
        */
        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures())
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        /**
        * Now that the assets are loaded we can just load the inner classes
        * so they are available later on
        */
        npc = new NPC(atlas,"lisa");
        npc2 = new NPC(atlas,"dude");
        mainMap = new MainMap("android/assets/tiles/base.tmx");
        dudeAsset = new DudeAsset(atlas);

    }

    public MainMap getMap(){
        return mainMap;
    }
    public void setMap(){ mainMap = new MainMap("android/assets/levels/blankLevel.tmx");}

    /**
    * Dump the textures
    */
    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }



    /**
    * Each of the following is an Inner Class which represents all textures
    * a given abstract game object will need
    */
    public class DudeAsset {
        public final AtlasRegion body;

        public DudeAsset(TextureAtlas atlas) {

            body = atlas.findRegion("dude");
        }

    }

    public class NPC {
        public final AtlasRegion body;

        public NPC(TextureAtlas atlas) {

            body = atlas.findRegion("lisa");
        }
        public NPC(TextureAtlas atlas, String name) {
            body = atlas.findRegion(name);
        }

    }

    public class MainMap {

        public MapProperties prop;
        public final TiledMap map;

        public MainMap(String mapInput){
            map = new TmxMapLoader().load(mapInput);
            prop = map.getProperties();

            /**
            * Use the actual map width to set GAME_WORLD on map load
            */
            Constants.GAME_WORLD = prop.get("width", Integer.class);
        }
    }
}