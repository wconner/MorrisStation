package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.util.Constants;


/**
 * Created by Ian on 2/26/2015.
 */
public class Player extends AbstractDynamicObject {

    private static final String TAG = Player.class.getName();
    /**
    * Dude is the little guy sprite being built
    * This is the player character class and has a long way to go
    */

    /**
    * To hold a texture
    */
    private TextureRegion dudeTexture;

    /**
    * Basic dialog tree proof, will cycle text
    */
    String[] dialog = {"Careful Man",
        "The dude is not, in",
        "El Duderino",
        "The Kanootsins",
        "They say my work is..."};
    int index = 0;

    /**
    * set dude spawn point and make dude
    */
    public Player(int id) {
        super(id, "player");
        super.getBody().setUserData(this);
        this.position.set(15, 15);
    }

    /**
     * create dude with specific spawn point
     */
    public Player(int id, float x, float y) {
        super(id, "player");
        super.getBody().setUserData(this);
        this.position.set(x, y);
    }

    /**
    * Set dude current text
    */
    public String randomText(){
        index = (index + 1) % dialog.length;
        return dialog[index];
    }


    public void setRegion (TextureRegion region) {

        dudeTexture = region;
    }

    public float getWidth(){
        return dudeTexture.getRegionWidth() * Constants.UNIT_SCALE/2;
    }

    public float getHeight(){
        return dudeTexture.getRegionHeight() * Constants.UNIT_SCALE/2;
    }


    /**
    * note, the render has its own texture which is grabbed all the time
    * it comes from the spritebatch but it just checks the current texture all the time
    */
    @Override
    public void render (SpriteBatch batch) {
        TextureRegion reg = null;

        reg = dudeTexture;
        batch.draw(reg.getTexture(),
                super.getBody().getPosition().x-.5f, super.getBody().getPosition().y-.5f,
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