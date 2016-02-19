package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.entities.MSensor;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;

/**
 * Created by bill on 1/11/16.
 */
public class FirstLevel extends Level {

    public FirstLevel(){
        super();
        atlas = new TextureAtlas("android/assets/atlas/test.pack");
        map = new TmxMapLoader().load("android/assets/tiles/base.tmx"); // Type Tiled map
        levelName = "first";
    }

    @Override
    public void addActors(){
        Player player;
        NPC npc;
        MSensor sensor;

        player = new Player(0, 15, 15);
        player.setRegion(new TextureRegion(atlas.findRegion("dude")));
        actors.add(player);

        npc = new NPC(1,20,15,"first", "lisa");
        npc.setRegion(new TextureRegion(atlas.findRegion("lisa")));
        actors.add(npc);

        npc = new NPC(2,20,15,"first", "dude");
        npc.setRegion(new TextureRegion(atlas.findRegion("dude")));
        actors.add(npc);

        sensor = new MSensor(100, 15.5f, 19f);
        actors.add(sensor);
    }

    @Override
    public String sensorEvent(String fixture) {
        return "";
    }
}
