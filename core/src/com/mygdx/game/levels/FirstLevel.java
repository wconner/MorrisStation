package com.mygdx.game.levels;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;

/**
 * Created by bill on 1/11/16.
 */
public class FirstLevel extends Level {

    public FirstLevel(){
        map = new TmxMapLoader().load("android/assets/tiles/base.tmx"); // Type Tiled map
        addActors();
    }

    private void addActors(){
        actors.add(new Player(0));
        actors.add(new NPC(1,20,15));
        actors.add(new NPC(2));
    }
}
