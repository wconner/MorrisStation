package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.JsonTest;

import java.util.ArrayList;


/**
 * Created by Ian on 2/26/2015.
 * NPC's ID should be: 0 < ID < 100
 */
public class NPC extends AbstractDynamicObject {

    private static final int COLS = 3;
    private static final int ROWS = 1;
    private Animation[] animations;
    private float stateTime;

    private static final String TAG = NPC.class.getName();

    /**
     * To hold a texture
     */
    private TextureRegion npcTexture;
    private int dialogID;
    private String name, levelName;
    private static JsonTest jsonTest = new JsonTest();

    /**
     * create NPC with specific location
     */
    public NPC(int id, float x, float y, String levelName, String name) {
        super(id, "NPC");
        super.getBody().setUserData(this);
        this.position.set(x, y);
        this.levelName = levelName;
        this.name = name;
        dialogID = 0;
    }

    public void setDialog(){
         jsonTest.setDialog(name, levelName, dialogID);
    }
    public void setDialogID(int id) { dialogID = id; jsonTest.setDialog(name, levelName, dialogID);}

    public JsonTest getJsonTest(){ return jsonTest;}

    /**
     * intializes the animations for the indicated texture region
     * @param s selects the column to begin reading the animations from
     * @param region selects the region of the texture to use as sprites
     */
    public void initAnim(int s, String region) {
        stateTime = 0f;

        /* initialize a 2d array of 32 * 32 pixel texture images from the texture region */
        TextureRegion[][] tmp = new TextureRegion(atlas.findRegion(region)).split(32, 32);

        TextureRegion[] walkLeft = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkDown = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkRight = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkUp = new TextureRegion[COLS * ROWS];


        /* assign the textures to a texture region array to be used for animation */
        int index = 0;
        for (int i = s; i < s + COLS; i++) {
            walkDown[index++] = tmp[4][i];
        }
        Animation walk_Down = new Animation(.15f, walkDown);
        walk_Down.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = s; i < s + COLS; i++) {
            walkUp[index++] = tmp[7][i];
        }
        Animation walk_Up = new Animation(.15f, walkUp);
        walk_Up.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = s; i < s + COLS; i++) {
            walkLeft[index++] = tmp[5][i];
        }
        Animation walk_Left = new Animation(.15f, walkLeft);
        walk_Left.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = s; i < s + COLS; i++) {
            walkRight[index++] = tmp[6][i];
        }
        Animation walk_Right = new Animation(.15f, walkRight);
        walk_Right.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        Animation stand_Down = new Animation(1f, tmp[4][s + 1]);
        Animation stand_Up = new Animation(1f, tmp[7][s + 1]);
        Animation stand_Left = new Animation(1f, tmp[5][s + 1]);
        Animation stand_Right = new Animation(1f, tmp[6][s + 1]);

        animations = new Animation[8];
        animations[0] = walk_Down;
        animations[1] = walk_Up;
        animations[2] = walk_Left;
        animations[3] = walk_Right;

        animations[4] = stand_Down;
        animations[5] = stand_Up;
        animations[6] = stand_Left;
        animations[7] = stand_Right;

    }


    public Animation[] getAnimations() {
        return animations;
    }

    /**
     * set the texture for the npc, used to create the sprite
     * @param r A string to identify the texture region
     * @param region A static texture to be used as the initial sprite before animations
     * @param s the selector used in initAnim() method
     */
    public void setRegion(String r, TextureRegion region, int s) {
        npcTexture = region;
        initAnim(s, r);
    }

    public float getWidth() {
        return npcTexture.getRegionWidth() * Constants.UNIT_SCALE / 2;
    }

    public float getHeight() {
        return npcTexture.getRegionHeight() * Constants.UNIT_SCALE / 2;
    }


    /**
     * note, the render has its own texture which is grabbed all the time
     * it comes from the spritebatch but it just checks the current texture all the time
     */
    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();


        //sets animation to walking version
        if (this.body.getLinearVelocity().x > this.body.getLinearVelocity().y && this.body.getLinearVelocity().x > 0)
            animState = 3;
        else if (this.body.getLinearVelocity().x < this.body.getLinearVelocity().y && this.body.getLinearVelocity().y > 0)
            animState = 1;
        else if (this.body.getLinearVelocity().x < this.body.getLinearVelocity().y && this.body.getLinearVelocity().x < 0)
            animState = 2;
        else if (this.body.getLinearVelocity().x > this.body.getLinearVelocity().y && this.body.getLinearVelocity().y < 0)
            animState = 0;

        //this checks if the player is moving or not
        TextureRegion currentFrame;
        if ((Math.abs(this.body.getLinearVelocity().x) + Math.abs(this.body.getLinearVelocity().y)) < .1) {
            //sets animation to standing still version
            currentFrame = getAnimations()[animState + 4].getKeyFrame(stateTime, true);

        } else {
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