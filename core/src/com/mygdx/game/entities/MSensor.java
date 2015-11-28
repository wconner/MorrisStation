package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by bill on 11/22/15. //TODO Make this part of NPC maybe?? Reusing a lot of code here.
 */
public class MSensor extends AbstractDynamicObject {

    public MSensor(int id, float x, float y){
        super(id, "sensor");
        super.getBody().setUserData(this);
        this.position.set(x, y);
    }


    public void render (SpriteBatch batch) {
        TextureRegion reg = null;

        batch.draw(reg.getTexture(),
                super.getBody().getPosition().x - .5f, super.getBody().getPosition().y - .5f,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);

        // Reset color to white
        batch.setColor(1, 1, 1, 1);

    }
}
