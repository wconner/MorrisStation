package com.mygdx.game.levels;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.entities.Player;

/**
 * Created by bill on 1/15/16.
 */
public class BaseLevel extends Level {

    public BaseLevel(){
        super();
        map = new TmxMapLoader().load("android/assets/levels/Base.tmx"); // Type Tiled map
        Assets.instance.setMap(map);
        addActors();
    }
    @Override
    void addActors() {
        actors.add(new Player(0, 5, 5));
    }

    @Override
    public String sensorEvent(String fixture) {
        return "";
    }
}
