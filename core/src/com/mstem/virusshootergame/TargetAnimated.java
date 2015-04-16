package com.mstem.virusshootergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by catherinehuang on 4/14/15.
 */
public class TargetAnimated {
    //1 for now since they're not animated sprites, later if have time, will animate the sprite to have
    private static final int FRAMES_COL = 1;
    private static final int FRAMES_ROW = 1;
//    private static final int SPEED = 300;

    private Sprite sprite;
    private Animation animation;
    private TextureRegion[] frames;
    private TextureRegion currentFrame;
    private Vector2 velocity = new Vector2();

    private float stateTime;
    private boolean isHit = false;

    public TargetAnimated(Sprite sprite) {
        this.sprite = sprite;
        Texture texture = sprite.getTexture();
        TextureRegion[][] temp = TextureRegion.split(texture,(int) getSpriteWidth(),
                texture.getHeight() / FRAMES_ROW);
        frames = new TextureRegion[FRAMES_COL * FRAMES_ROW];
        int index = 0;
        for(int i = 0 ; i < FRAMES_ROW; i++)
        {
            for(int j = 0; j <FRAMES_COL; j++) {
                frames[index++] = temp[i][j];
            }
        }

        animation = new Animation(0.1f, frames);
        stateTime = 0f;
    }

    /**
     * Draw method for AnimatedSprite
     * @param spriteBatch
     */
    public void draw(SpriteBatch spriteBatch) {
        //tracking the time change
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);

        spriteBatch.draw(currentFrame, sprite.getX(), sprite.getY());
    }


    /**
     * Set the position of the Object
     * @param x
     * @param y
     */
    public void setPosition(float x, float y)
    {
        sprite.setPosition(x - getSpriteCenterOffset(), y);
    }

    /**
     * Get the midpoint of the sprite
     * @return
     */
    public float getSpriteCenterOffset()
    {
        return getSpriteWidth() / 2;
    }

    /**
     * Get the width of the sprite
     * @return
     */
    public float getSpriteWidth() {
        return sprite.getWidth() / FRAMES_COL;
    }

    /**
     * Move the sprite to the right
     */
    public void moveRight(float speed)
    {
        velocity = new Vector2(speed, 0);
    }

    /**
     * Move the sprite to the left
     */
    public void moveLeft(float speed)
    {
        velocity = new Vector2(-speed, 0);
    }

    /**
     * Get the mid-position of the sprite at x
     * @return
     */
    public int getX() {
        return (int) (sprite.getX() + getSpriteCenterOffset());
    }

    /**
     * Get the y position of the sprite
     * @return
     */
    public int getY() {
        return (int) sprite.getY();
    }

    /**
     * Get the width of the sprite as int
     * @return
     */
    public int getWidth() {
        return (int) getSpriteWidth();
    }

    /**
     * Get the height of the sprite
     * @return
     */
    public int getHeight() {
        return (int) sprite.getHeight() / FRAMES_ROW;
    }

    /**
     * Check the movement of the sprite, so it doesn't move so far
     */
    public void move() {
        int xMovement = (int)(velocity.x * Gdx.graphics.getDeltaTime());
        int yMovement = (int) (velocity.y * Gdx.graphics.getDeltaTime());
        sprite.setPosition(sprite.getX()+xMovement, sprite.getY() + yMovement);

        //sets boundaries on the x-axis
        if(sprite.getX() < 0) {
            sprite.setX(0);
        }
        if(sprite.getX() + getSpriteWidth() > MyGdxGame.VIEWPORT_WIDTH) {
            sprite.setX(MyGdxGame.VIEWPORT_WIDTH - getSpriteWidth());
        }
    }

    /**
     * Set the velocity for the sprite object for it to move
     * @param velocity
     */
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    /**
     * Change the direction of the movement of the sprite
     */
    public void changeDirection() {
        //set the x-
        velocity.x = -velocity.x;
    }

    /**
     * Get the bounding box of the sprite
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
    }

    /**
     * When the sprite is hit
     */
    public void setDestroy(boolean ishit) {
        this.isHit = ishit;
    }

    /**
     * check if the sprite is hit
     */
    public boolean isHit() {
        return isHit;
    }

}
