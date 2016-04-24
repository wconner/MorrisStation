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
    private boolean doorActive = false;

    public BedroomLevel() {
        super();
        atlas = new TextureAtlas("sprites/cTest.pack");
        map = new TmxMapLoader().load("levels/FutureBedRoom.tmx"); // Type Tiled map
        levelName = "bedroom";
    }

    @Override
    public void addActors() {
        Player player;
        NPC npc;

        player = new Player(0, 5, 5);
        player.setRegion(new TextureRegion(atlas.findRegion("Spacece"), 32 * 11, 0, 32, 32));
        actors.add(player);

        npc = new NPC(2, 13, 7, levelName, "Bit Daemon", dialogIDs.get(2), 4, 0); /** Robot */
        npc.setRegion("EBRobotedit3crMatsuoKaito", new TextureRegion(atlas.findRegion("EBRobotedit3crMatsuoKaito"), 32 * 3, 32 * 4, 32, 32), 0);
        actors.add(npc);
    }

    public boolean isDoorActive() {
        return doorActive;
    }

    public void setDoorActive(boolean doorActive) {
        this.doorActive = doorActive;
    }
}
