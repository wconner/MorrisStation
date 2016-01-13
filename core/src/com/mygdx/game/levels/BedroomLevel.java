package com.mygdx.game.levels;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Assets;
import com.mygdx.game.entities.Player;

/**
 * Created by bill on 12/5/15.
 */
public class BedroomLevel extends Level {

        public BedroomLevel() {
            super();
            map = new TmxMapLoader().load("android/assets/levels/FutureBedRoom.tmx"); // Type Tiled map
            Assets.instance.setMap(map);
            addActors();
        }

    @Override
    void addActors() {
        actors.add(new Player(0, 5, 5));
    }
}
