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
    private Animation walk_Down;
    private Animation walk_Right;
    private Animation walk_Left;
    private Animation walk_Up;
    private Animation stand_Up;
    private Animation stand_Down;
    private Animation stand_Left;
    private Animation stand_Right;
    private Animation[] animations;
    private float stateTime;
    private TextureRegion currentFrame;
    private int selector;

    private static final String TAG = NPC.class.getName();

    /**
     * To hold a texture
     */
    private TextureRegion npcTexture;
    private int levelID, dialogID;
    private String name;
    private static JsonTest jsonTest = new JsonTest();

    /**
     * create NPC with specific location
     */
    public NPC(int id, float x, float y, int levelID, String name) {
        super(id, "NPC");
        super.getBody().setUserData(this);
        this.position.set(x, y);
        this.levelID = levelID;
        this.name = name;
        dialogID = 0;
    }

    public void setDialog(){
         jsonTest.setDialog(name, levelID, dialogID);
    }
    public void setDialogID(int id) { dialogID = id; jsonTest.setDialog(name, levelID, dialogID);}

    public JsonTest getJsonTest(){ return jsonTest;}

    public void initAnim() {
        stateTime = 0f;

        //change this with params later
        selector = 6;

        TextureRegion[][] tmp = new TextureRegion(atlas.findRegion("Spacece")).split(32, 32);
        TextureRegion[] walkLeft = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkDown = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkRight = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkUp = new TextureRegion[COLS * ROWS];


        int index = 0;
        for (int i = selector; i < selector + COLS; i++) {
            walkDown[index++] = tmp[4][i];
        }
        walk_Down = new Animation(.15f, walkDown);
        walk_Down.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = selector; i < selector + COLS; i++) {
            walkUp[index++] = tmp[7][i];
        }
        walk_Up = new Animation(.15f, walkUp);
        walk_Up.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = selector; i < selector + COLS; i++) {
            walkLeft[index++] = tmp[5][i];
        }
        walk_Left = new Animation(.15f, walkLeft);
        walk_Left.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        index = 0;
        for (int i = selector; i < selector + COLS; i++) {
            walkRight[index++] = tmp[6][i];
        }
        walk_Right = new Animation(.15f, walkRight);
        walk_Right.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        stand_Down = new Animation(1f, tmp[4][selector + 1]);
        stand_Up = new Animation(1f, tmp[7][selector + 1]);
        stand_Left = new Animation(1f, tmp[5][selector + 1]);
        stand_Right = new Animation(1f, tmp[6][selector + 1]);

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

    public void setRegion(TextureRegion region) {
        npcTexture = region;
        initAnim();
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