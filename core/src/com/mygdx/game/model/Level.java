package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Things that need to be done in order to change lvl
 * Set the "MainMap" in Assets to the new level
 * In WorldRenderer call: tileRenderer = new OrthogonalTiledMapRenderer(Assets.instance.mainMap.map, Constants.UNIT_SCALE);
 * Somehow dispose of the Box2d's from the first level
 *      This may require creating a whole new WorldController
 */


public class Level {

    protected TiledMap map;
    protected Array<Sprite> sprites;
    protected Array<Sprite> stationarySprites;
    protected HashMap<Vector3, String> exits;
    protected float red;
    protected float green;
    protected float blue;

    public Level() {
        sprites = new Array<Sprite>();
        stationarySprites = new Array<Sprite>();
        exits = new HashMap<Vector3, String>();
    }

    public Array<Sprite> getStationarySprites() {
        return stationarySprites;
    }

    public TiledMap getMap() {
        return map;
    }

    public Array<Sprite> getSprites() {
        return sprites;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }
}