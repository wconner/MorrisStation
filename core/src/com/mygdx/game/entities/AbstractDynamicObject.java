package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

import com.mygdx.game.GameInstance;

/**
 * Created by Ian on 1/21/2015.
 */

public abstract class AbstractDynamicObject {

    private static final String TAG = AbstractDynamicObject.class.getName();
    protected TextureAtlas atlas;
    public final int id;
    boolean ableToMove = true;

    /**
     * For physics
     */
    public Body body;
    Vector2 currentVector;
    float inputForce = 35;

    /**
     * Fields for a basic 2d object on a flat plane
     */
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    /**
     * Fields for simulating a physical model on an object
     * info on page 168
     */
    public Vector2 velocity;
    public Vector2 terminalVelocity;
    public Vector2 friction;
    public Vector2 acceleration;
    public Rectangle bounds;
    public CircleShape shape;
    public FixtureDef fixtureDef;
    public BodyDef bodyDef;
    public Fixture fixture;

    public String facing;

    /**
     * Animation variables
     */
    protected int animState;

    /**
     * Default constructor
     */
    public AbstractDynamicObject(int id, String objType) {

        this.id = id;
        facing = "d";
        bodyDef = new BodyDef();

        atlas = new TextureAtlas("android/assets/sprites/cTest.pack");

        if (objType.equals("player")) {         /** For player ADO */
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        /*bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);*/
        } else if (objType.equals("NPC")) {        /** For NPC ADO */
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        } else if (objType.equals("sensor")) {     /** For Sensor ADO */
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }

        body = GameInstance.getInstance().world.createBody(bodyDef);
        body.setLinearDamping(10f);

        shape = new CircleShape();
        shape.setRadius(.65f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;

        if (objType.equals("sensor"))       /** Only for Sensor ADO */
            fixtureDef.isSensor = true;

        fixture = body.createFixture(fixtureDef);
        shape.dispose();

        position = new Vector2();
        dimension = new Vector2(1.3f, 1.3f);
        origin = new Vector2();
        scale = new Vector2(1f, 1f);
        rotation = 0;

        //from actor creation shit
        velocity = new Vector2();
        terminalVelocity = new Vector2(1, 1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();

        currentVector = body.getLinearVelocity();
    }

    public void facing(int facing) {
        switch (facing) {
            case 1:
        }
    }

    public int getID() {
        return id;
    }


    public void setLinearV(float vx, float vy) {
        getBody().setLinearVelocity(vx, vy);
    }

    public Body getBody() {
        return body;
    }


    public void setAbleToMove(boolean option) {
        ableToMove = option;
    }

    /**
     * Moves an object based on physical bounds of the game world
     */
    public void update(float deltaTime) {

        currentVector = body.getLinearVelocity();

        position = this.body.getPosition();
    }

    public void moveCharacter(int direction) {

        Vector2 currentPosition = this.body.getPosition();

        if (!ableToMove) return;

        if (direction == 0) { //down
            float angle = 3 * 90 * MathUtils.degRad;
            this.body.applyForceToCenter(0, -inputForce, true);
            this.body.setTransform(this.body.getPosition(), angle);
            animState = 0;

        } else if (direction == 1) { //up
            float angle = 1 * 90 * MathUtils.degRad;
            this.body.applyForceToCenter(0, inputForce, true);
            this.body.setTransform(this.body.getPosition(), angle);
            animState = 1;

        } else if (direction == 2) { //left
            float angle = 2 * 90 * MathUtils.degRad;
            this.body.applyForceToCenter(-inputForce, 0, true);
            this.body.setTransform(this.body.getPosition(), angle);
            animState = 2;

        } else if (direction == 3) { //right
            float angle = 0 * 90 * MathUtils.degRad;
            this.body.applyForceToCenter(inputForce, 0, true);
            this.body.setTransform(this.body.getPosition(), angle);
            animState = 3;

        }
        position = currentPosition;

    }

    public int getAnimState() {
        return animState;
    }

    public void setAnimState(int state) {
        animState = state;
    }

    private int state = 1; //data field for behavior
    private int prevState = 1;
    private float sleep = 0;

    public void behavior(int id, float deltaTime) {
        sleep -= deltaTime;

        if (sleep <= 0) {
            switch (id) {
                case 1:     /** npc #1 - lisa. Has north and south motion. */
                    if (state == 1) {            /** Initial Start */
                        setLinearV(0, 1);
                    }
                    if (this.position.y <= 14) {
                        setLinearV(0, 1);
                        state = 1;
                    } else if (this.position.y >= 21) {
                        setLinearV(0, -1);
                        state = 2;
                    }
                    break;

                case 2:  /** npc #2 - other dude. Has east and west motion. */
                    if (state == 1) {
                        setLinearV(1, 0);
                    }
                    if (this.position.x < 18) {
                        setLinearV(1, 0);
                        state = 1;
                    } else if (this.position.x > 25) {
                        setLinearV(-1, 0);
                        state = 2;
                    }
                    break;
            }
            if (state != prevState) {       /** Sleep when state changed */
                sleep = 10;
                setLinearV(0, 0);
            }
            prevState = state;
        }
    }


    public abstract void render(SpriteBatch batch);


    public Vector2 getPosition(){
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    /**
     * Removes this object from the game.
     */
    public void remove() {
        body.destroyFixture(fixture);
    }

    public void rotateBody(float angle) {
        this.body.setTransform(body.getPosition(), angle);
    }


    public Vector2 getVelocity() {
        return currentVector;
    }

    public String toString() {
        return "ID: " + id;
    }

    /**
     * These methods should be overridden by the subordinate classes
     */
    public void setRegion(TextureRegion region) {
        return;
    }

    public float getWidth() {
        return 0;
    }

    public float getHeight() {
        return 0;
    }
}