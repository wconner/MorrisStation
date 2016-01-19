package com.mygdx.game;

/**
 * Created by Ian on 1/13/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TiledMap;
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
    public TiledMap map;
    public TextureAtlas atlas, atlas2;

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
        assetManager.load("android/assets/sprites/cTest.pack",
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
        atlas = assetManager.get("android/assets/sprites/cTest.pack");


        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures())
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        /**
        * Now that the assets are loaded we can just load the inner classes
        * so they are available later on
        */
        npc = new NPC(atlas,"lisa");
        npc2 = new NPC(atlas,"dude");
        dudeAsset = new DudeAsset(atlas);

    }

    public TextureAtlas getAtlas() { return atlas;}
    public TiledMap getMap(){
        return map;
    }
    public void setMap(TiledMap map){ this.map = map;}

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

            body = atlas.findRegion("Spacece");
            //marioJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 80, 0, 16, 16);
        }

    }

    public class NPC {
        public final AtlasRegion body;

        public NPC(TextureAtlas atlas) {

            body = atlas.findRegion("Spacece");
        }
        public NPC(TextureAtlas atlas, String name) {
            body = atlas.findRegion(name);
        }

    }
}