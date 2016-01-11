package com.mygdx.game.levels;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Assets;
import com.mygdx.game.entities.MSensor;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;

/**
 * Created by bill on 1/11/16.
 */
public class FirstLevel extends Level {

    public FirstLevel(){
        super();
        map = new TmxMapLoader().load("android/assets/tiles/base.tmx"); // Type Tiled map
        Assets.instance.setMap(map);
        addActors();
    }

    @Override
    protected void addActors(){
        actors.add(new Player(0));
        actors.add(new NPC(1,20,15));
        actors.add(new NPC(2));
        actors.add(new MSensor(100, 15.5f, 19f));
    }
}
