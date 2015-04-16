package com.mstem.virusshootergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Target class is responsible for creating the target objects for the player to shoot at.
 * Created by catherine huang on 4/12/15.
 */
public class Target {

    //speed of the target
    private static float SPEED;
//    private static List<AnimatedSprite> targets = new ArrayList<AnimatedSprite>();
    public Texture texture;
    public TargetAnimated targetAnimated;
    private float respawnTime = 0f;

    //storing values
    private String name;
    private int score;


    /**
     * Default Constructor
     */
    public Target() {
    }

    /**
     * Constructor
     * @param texture
     */
    public Target(Texture texture, float speed, int score, String name) {
        this.texture = texture;
        this.SPEED = speed;
        this.score = score;
        this.name = name;
        randomGen();
    }

    /**
     * Generate a target at random Position
     */
    public void randomGen() {
        Sprite targetSprite = new Sprite(texture);
        targetAnimated = new TargetAnimated(targetSprite);

        int randomX = createRandomXPosition();
        int randomY = createRandomYPosition();
        targetAnimated.setPosition(randomX, randomY);
        targetAnimated.setVelocity(new Vector2(SPEED, 0));
        targetAnimated.setDestroy(false);
    }

    /**
     * find a random x position for the target
     * @return
     */
    private int createRandomXPosition() {
        Random random = new Random();
        int randomNum = random.nextInt(MyGdxGame.VIEWPORT_WIDTH - targetAnimated.getWidth());
        return randomNum + targetAnimated.getWidth()/2;
    }

    /**
     * find a random y position for the target
     */
    private int createRandomYPosition() {
        Random random = new Random();
        int randomNum = random.nextInt((MyGdxGame.VIEWPORT_HEIGHT - 200)) + 100;
        return randomNum + targetAnimated.getHeight()/2;
    }

    /**
     * draw method for the target
     */
    public void draw(SpriteBatch batch) {
        if(!targetAnimated.isHit())
            targetAnimated.draw(batch);
    }


    /**
     * updates the motion of the target sprite
     */
    public void update() {
        if(targetAnimated.isHit()) {
            respawnTime -= Gdx.graphics.getDeltaTime();
            if(respawnTime <= 0){
                randomGen();
            }
        }
        else {
            if (shouldChangeDirection())
                targetAnimated.changeDirection();
            targetAnimated.move();
        }
    }

    /**
     * Check if the target should change it's direction
     * @return
     */
    private boolean shouldChangeDirection() {
        Random random = new Random();
        //change number up to lower frequency or vise versa.
        return random.nextInt(100) == 0;
    }

    /**
     * Get the bounding box of the target
     */
    public Rectangle getBoundingBox() {
        return targetAnimated.getBoundingBox();
    }

    /**
     * What happens when the target is hit by a bullet
     */
    public void hit() {
        targetAnimated.setDestroy(true);
//        respawnTime = 8f;
        respawnTime = 6f;
    }

    /**
     * get the targetAnimated
     */
    public TargetAnimated getTargetAnimated() {
        return targetAnimated;
    }

    /**
     * get the name of the target
     */
    public String getName() {
        return this.name;
    }

    /**
     * check if hit
     */
    public boolean checkDestroy() {
        return targetAnimated.isHit();
    }
}
