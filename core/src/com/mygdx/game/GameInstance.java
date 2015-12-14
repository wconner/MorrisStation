package com.mygdx.game;

/**
 * Created by Ian on 4/13/2015.
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameInstance {
    public World world = new World(new Vector2(0,0),false);

    public static GameInstance instance;

    public static GameInstance getInstance() {
        if (instance == null) {
            instance = new GameInstance();
        }
        return instance;
    }
}