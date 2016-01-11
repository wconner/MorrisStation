package com.mygdx.game.levels;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Assets;
import com.mygdx.game.entities.Player;

/**
 * Created by bill on 12/5/15.
 */
public class BridgeLevel extends Level {

        public BridgeLevel() {
            map = new TmxMapLoader().load("android/assets/levels/bridgeLevel.tmx"); // Type Tiled map

            Player player = new Player(0);

            player.setRegion(Assets.instance.dudeAsset.body);
            player.getBody().setTransform(player.position.x + player.getWidth(), player.position.y + player.getHeight(), 0);
        }
    }
