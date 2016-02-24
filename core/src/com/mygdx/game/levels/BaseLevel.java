package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;

/**
 * Created by bill on 1/15/16.
 */
public class BaseLevel extends Level {

    public BaseLevel(){
        super();
        atlas = new TextureAtlas("android/assets/sprites/cTest.pack");
        map = new TmxMapLoader().load("android/assets/levels/Base.tmx"); // Type Tiled map
        levelName = "base";
    }
    @Override
    public void addActors() {
        Player player;
        NPC npc;

        player = new Player(0, 5, 5);
        player.setRegion(new TextureRegion(atlas.findRegion("Spacece"), 32 * 11, 0, 32, 32));
        actors.add(player);

        npc = new NPC(1, 15, 27, levelName, "friend");   /** Red hat girl */
        npc.setRegion("Spacece", new TextureRegion(atlas.findRegion("Spacece"), 32 * 7, 0, 32, 32),6);
        actors.add(npc);

        npc = new NPC(2, 15, 23, levelName, "robot");   /** Robot */
        npc.setRegion("EBRobotedit3crMatsuoKaito", new TextureRegion(atlas.findRegion("EBRobotedit3crMatsuoKaito"), 32 * 3, 32 * 4, 32, 32),0);
        actors.add(npc);

        npc = new NPC(3, 17, 25, levelName, "boss");   /** Boss man */
        npc.setRegion("Spacece" , new TextureRegion(atlas.findRegion("Spacece"), 32 * 1, 0, 32, 32),3);
        actors.add(npc);
    }

    @Override
    public String sensorEvent(String fixture) {
        if (fixture != null) {
            switch (fixture) {
                case "BRD":
                    return "cl1";
                default:
                    return "";
            }
        }
        return "";
    }
}
