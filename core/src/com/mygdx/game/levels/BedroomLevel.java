package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;

/**
 * Created by bill on 12/5/15.
 */
public class BedroomLevel extends Level {

    public static int levelID = 1;


        public BedroomLevel() {
            super();
            atlas = new TextureAtlas("android/assets/sprites/cTest.pack");
            map = new TmxMapLoader().load("android/assets/levels/FutureBedRoom.tmx"); // Type Tiled map
            addActors();
        }

    @Override
    protected void addActors() {
        Player player;
        NPC npc;

        player = new Player(0, 5, 5);
        player.setRegion(new TextureRegion(atlas.findRegion("Spacece"), 32 * 11, 0, 32, 32));
        actors.add(player);

        npc = new NPC(2, 7, 5); /** Robot */
        npc.setRegion(new TextureRegion(atlas.findRegion("EBRobotedit3crMatsuoKaito"), 32 * 3, 32 * 4, 32, 32));
        actors.add(npc);
    }

    @Override
    public String sensorEvent(String sensor) {
        switch (sensor){
            case "door":
                return "cl2";
            default:
                return "";
        }
    }
}
