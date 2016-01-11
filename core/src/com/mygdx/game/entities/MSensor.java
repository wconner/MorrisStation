package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by bill on 11/22/15. //TODO Make this part of NPC maybe?? Reusing a lot of code here.
 * MSensor's ID should be > 100
 */
public class MSensor extends AbstractDynamicObject {

    public MSensor(int id, float x, float y){
        super(id, "sensor");
        super.getBody().setUserData(this);
        this.position.set(x, y);
    }


    public void render (SpriteBatch batch) {
        // Reset color to white
        batch.setColor(1, 1, 1, 1);
    }
}
