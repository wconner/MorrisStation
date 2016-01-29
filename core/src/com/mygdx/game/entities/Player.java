package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
     * Dude is the little guy sprite being built
     * This is the player character class and has a long way to go
     */

    /**
     * To hold a texture
     */
    private TextureRegion dudeTexture;

    /**
     * Constants for animation
     */
    private static final int COLS = 3;
    private static final int ROWS = 1;
    Animation walk_Down;
    Animation walk_Right;
    Animation walk_Left;
    Animation walk_Up;
    Animation stand_Up;
    Animation stand_Down;
    Animation stand_Left;
    Animation stand_Right;
    float stateTime;
    TextureRegion currentFrame;

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
    public String randomText() {
        index = (index + 1) % dialog.length;
        return dialog[index];
    }

    /**
     * Initialize animations for dude
     */
    public void initAnim(TextureRegion region) {

        Texture dude = new Texture(Gdx.files.internal("android/assets/sprites/cTest.png"));

        //try to figure out a way to not have to use a separate object for each animation
        TextureRegion[] walkLeft = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkDown = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkRight = new TextureRegion[COLS * ROWS];
        TextureRegion[] walkUp = new TextureRegion[COLS * ROWS];


        TextureRegion[][] tmp = TextureRegion.split(dude, 32, 32);


        int index = 0;
        for (int i = 21; i < 21 + COLS; i++) {
            walkDown[index++] = tmp[4][i];
        }
        walk_Down = new Animation(.2f, walkDown);
        walk_Down.setPlayMode(Animation.PlayMode.LOOP);

        index = 0;
        for (int i = 21; i < 21 + COLS; i++) {
            walkUp[index++] = tmp[7][i];
        }
        walk_Up = new Animation(.2f, walkUp);
        walk_Up.setPlayMode(Animation.PlayMode.LOOP);

        index = 0;
        for (int i = 21; i < 21 + COLS; i++) {
            walkLeft[index++] = tmp[5][i];
        }
        walk_Left = new Animation(.2f, walkLeft);
        walk_Left.setPlayMode(Animation.PlayMode.LOOP);

        index = 0;
        for (int i = 21; i < 21 + COLS; i++) {
            walkRight[index++] = tmp[6][i];
        }
        walk_Right = new Animation(.2f, walkRight);
        walk_Right.setPlayMode(Animation.PlayMode.LOOP);



        stand_Down = new Animation(1f, tmp[4][22]);
        stand_Up = new Animation(1f, tmp[7][22]);
        stand_Left = new Animation(1f, tmp[5][22]);
        stand_Right = new Animation(1f, tmp[6][22]);


        stateTime = 0f;
    }


    public Animation[] getAnimations() {
        Animation[] animations = new Animation[8];
        animations[0] = walk_Down;
        animations[1] = walk_Up;
        animations[2] = walk_Left;
        animations[3] = walk_Right;

        animations[4] = stand_Down;
        animations[5] = stand_Up;
        animations[6] = stand_Left;
        animations[7] = stand_Right;
        return animations;
    }

    public void setRegion(TextureRegion region) {

        dudeTexture = region;
        initAnim(region);
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
        if (((this.body.getLinearVelocity().x + this.body.getLinearVelocity().y) < .1) && (this.body.getLinearVelocity().x + this.body.getLinearVelocity().y) > -.1) {
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