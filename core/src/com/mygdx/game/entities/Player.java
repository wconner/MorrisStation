package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.util.Constants;


/**
 * Created by Ian on 2/26/2015.
 */
public class Player extends AbstractDynamicObject {

    private static final String TAG = Player.class.getName();

    /**
     * To hold a texture
     */
    private TextureRegion dudeTexture;

    /**
     * Constants for animation
     */
    private static final int COLS = 3, ROWS = 1;
    private Animation walk_Down, walk_Right, walk_Left, walk_Up;
    private Animation stand_Up, stand_Down, stand_Left, stand_Right;
    private Animation[] animations;
    private float stateTime;
    private TextureRegion currentFrame;
    int index = 0;

    /**
     * create dude with specific spawn point
     */
    public Player(int id, float x, float y) {
        super(id, "player");
        super.getBody().setUserData(this);
        this.position.set(x, y);
    }

    /**
     * Initialize animations for dude
     */
    public void initAnim() {
        //TODO try to figure out a way to not have to use a separate object for each animation
        TextureRegion[] walkLeft = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkDown = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkRight = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkUp = new TextureRegion[COLS * ROWS];

        TextureRegion[][] tmp = new TextureRegion(atlas.findRegion("Spacece")).split(32, 32);

        int index = 0;
        for (int i = 9; i < 9 + COLS; i++) {
            walkDown[index++] = tmp[4][i];
        }
        walk_Down = new Animation(.15f, walkDown);
        walk_Down.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = 9; i < 9 + COLS; i++) {
            walkUp[index++] = tmp[7][i];
        }
        walk_Up = new Animation(.15f, walkUp);
        walk_Up.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = 9; i < 9 + COLS; i++) {
            walkLeft[index++] = tmp[5][i];
        }
        walk_Left = new Animation(.15f, walkLeft);
        walk_Left.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = 9; i < 9 + COLS; i++) {
            walkRight[index++] = tmp[6][i];
        }
        walk_Right = new Animation(.15f, walkRight);
        walk_Right.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        stand_Down = new Animation(1f, tmp[4][10]);
        stand_Up = new Animation(1f, tmp[7][10]);
        stand_Left = new Animation(1f, tmp[5][10]);
        stand_Right = new Animation(1f, tmp[6][10]);

        animations = new Animation[8];

        animations[0] = walk_Down;
        animations[1] = walk_Up;
        animations[2] = walk_Left;
        animations[3] = walk_Right;

        animations[4] = stand_Down;
        animations[5] = stand_Up;
        animations[6] = stand_Left;
        animations[7] = stand_Right;

        stateTime = 0f;
    }


    public Animation[] getAnimations() {
        return animations;
    }

    public void setRegion(TextureRegion region) {
        dudeTexture = region;
        initAnim();
    }

    public float getWidth() {
        return dudeTexture.getRegionWidth() * Constants.UNIT_SCALE / 2;
    }

    public float getHeight() {
        return dudeTexture.getRegionHeight() * Constants.UNIT_SCALE / 2;
    }

    /**
     * note, the render has its own texture which is grabbed all the time
     * it comes from the spritebatch but it just checks the current texture all the time
     */
    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        //this checks if the player is moving or not
        if ((Math.abs(this.body.getLinearVelocity().x) + Math.abs(this.body.getLinearVelocity().y)) < .1) {
            //sets animation to standing still version
            currentFrame = getAnimations()[animState + 4].getKeyFrame(stateTime, true);

        } else {
            //sets animation to walking version
            currentFrame = getAnimations()[animState].getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame,
                super.getBody().getPosition().x - dimension.x / 2,
                super.getBody().getPosition().y - dimension.y / 2,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation);

        // Reset color to white
        batch.setColor(1, 1, 1, 1);
    }
}