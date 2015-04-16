package com.mstem.virusshootergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by catherine huang on 4/12/15.
 */
public class ShotManager {

    public static final int SHOT_SPEED = 800;
    private static final int SHOT_Y_OFFSET = 35;
    private static final float MINIMUM_TIME_BETWEEN_SHOTS = 1f;

    int shotRemain = 30;

    private final Texture shotTexture;
    private List<AnimatedSprite> shots = new ArrayList<AnimatedSprite>();
    private float timeSinceLastShot = 0;

    /**
     * Constructor
     * @param shotTexture
     */
    public ShotManager(Texture shotTexture) {
        this.shotTexture = shotTexture;

    }

    /**
     * Fire the bullet
     * @param gunCenterXLocation
     */
    public void fire(float gunCenterXLocation) {
        if(canFireShot()){
            Sprite newShot = new Sprite(shotTexture);
            AnimatedSprite newShotAnimated = new AnimatedSprite(newShot);
            newShotAnimated.setPosition(gunCenterXLocation, SHOT_Y_OFFSET);
            newShotAnimated.setVelocity(new Vector2(0, SHOT_SPEED));
            shots.add(newShotAnimated);
            shotRemain--;
            timeSinceLastShot = 0f;
        }
    }

    /**
     * Checking firing condition
     * @return
     */
    private boolean canFireShot() {
        if(shotRemain <= 0) {
            return false;
        }
        return timeSinceLastShot > MINIMUM_TIME_BETWEEN_SHOTS;
    }

    /**
     * Update on the status of the bullet, either move the bullet across the screen or remove the bullet.
     */
    public void update() {
        //Iterator to keep track of the bullets
        Iterator<AnimatedSprite> iter = shots.iterator();
        while(iter.hasNext())
        {
            AnimatedSprite shot = iter.next();
            shot.move();
            if(shot.getY() > MyGdxGame.VIEWPORT_HEIGHT)
                iter.remove();
        }

        timeSinceLastShot += Gdx.graphics.getDeltaTime();
    }

    /**
     * Draw the shot to the screen
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        for(AnimatedSprite shot : shots) {
            shot.draw(batch);
        }
    }

    /**
     * check if the bullet has hit a target
     * @param boundingBox
     * @return
     */
    public boolean bulletHits(Rectangle boundingBox) {
        Iterator<AnimatedSprite> iterShots = shots.iterator();
        Rectangle intersection = new Rectangle();
        if(iterShots.hasNext()) {
            AnimatedSprite shot = iterShots.next();
            if(Intersector.intersectRectangles(shot.getBoundingBox(), boundingBox, intersection)){
                iterShots.remove();
                return true;
            }
        }
        return false;

    }

}//End of ShotManager.java
