package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.AbstractDynamicObject;

/**
 * Things that need to be done in order to change lvl
 * Things needed to create Level:
 *      The stage               (For UI stuff)
 *      MapBodyManager          (Physics for scenery, created from:  bodyManager = new MapBodyManager(GameInstance.getInstance().world,16, null, Application.LOG_DEBUG);)
 *      The Actors
 *      Actor Assets            (now stored inside of the level)
 */

public abstract class Level {

    protected TiledMap map;
    protected Array<AbstractDynamicObject> actors;
    protected TextureAtlas atlas;
    protected String levelName;

    public Level() {
        actors = new Array<>();
    }

    public abstract void addActors();
    public abstract String sensorEvent(String sensor);

    public Array<AbstractDynamicObject> getActors(){ return actors;}
    public TiledMap getMap() {
        return map;
    }

}