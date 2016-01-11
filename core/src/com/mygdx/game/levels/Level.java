package com.mygdx.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Assets;
import com.mygdx.game.entities.AbstractDynamicObject;

/**
 * Things that need to be done in order to change lvl
 *      Set the "MainMap" in Assets to the new level
 *      In WorldRenderer call: tileRenderer = new OrthogonalTiledMapRenderer(Assets.instance.map.map, Constants.UNIT_SCALE);
 *      Somehow dispose of the Box2d's from the first level
 *          This may require creating a whole new WorldController
 *      bodyManager.destroyPhysics();
 *
 * Things needed to create Level:
 *      The stage               (For UI stuff)
 *      MapBodyManager          (Physics for scenery, created from:  bodyManager = new MapBodyManager(GameInstance.getInstance().world,16, null, Application.LOG_DEBUG);)
 *      The Actors
 *      Actor Assets            (Comes from Assets: player.setRegion(Assets.instance.dudeAsset.body);)
 *      Change MainMap in Assets to new map.
 */

public abstract class Level {

    protected TiledMap map;
    protected Array<AbstractDynamicObject> actors;

    public Level() {
        actors = new Array<>();
    }
    public Array<AbstractDynamicObject> getActors(){ return actors;}
    public TiledMap getMap() {
        return map;
    }
    abstract void addActors();
}