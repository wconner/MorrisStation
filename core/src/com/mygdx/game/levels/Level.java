package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.AbstractDynamicObject;

/**
 * Things that need to be done in order to change lvl
 * Things needed to create Level:
 * The stage               (For UI stuff)
 * MapBodyManager          (Physics for scenery, created from:  bodyManager = new MapBodyManager(GameInstance.getInstance().world,16, null, Application.LOG_DEBUG);)
 * The Actors
 * Actor Assets            (now stored inside of the level)
 */

public abstract class Level {

    protected TiledMap map;
    protected Array<AbstractDynamicObject> actors;
    protected TextureAtlas atlas;
    protected String levelName;
    protected Array<Integer> dialogIDs = null;

    public Level() {
        actors = new Array<>();

        dialogIDs = new Array<Integer>();
        for (int i = 0; i < 16; i++)
            dialogIDs.insert(i, 0);
    }

    public abstract void addActors();

    public void returnDialogIDs(Array<Integer> dialogIDs) {
        this.dialogIDs = dialogIDs;
    }

    public Array<AbstractDynamicObject> getActors() {
        return actors;
    }

    public TiledMap getMap() {
        return map;
    }
}
