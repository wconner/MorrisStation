package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;


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