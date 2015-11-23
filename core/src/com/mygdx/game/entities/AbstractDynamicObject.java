package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameInstance;

/**
 * Created by Ian on 1/21/2015.
 */

public abstract class AbstractDynamicObject{

    private static final String TAG = AbstractDynamicObject.class.getName();

    public final int id;
    boolean ableToMove = true;

    /*
    * For physics
    * */
    public Body body;
    Vector2 currentVector;
    float inputForce = 35;

    /*
    * Fields for a basic 2d object on a flat plane
    * */
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    /*
    * Fields for simulating a physical model on an object
    * info on page 168
    * */
    public Vector2 velocity;
    public Vector2 terminalVelocity;
    public Vector2 friction;
    public Vector2 acceleration;
    public Rectangle bounds;

    public String facing;


    /*
    * Default constructor
    * */
    public AbstractDynamicObject(int id) {

        this.id = id;
        facing = "d";

        BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        /*bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);*/

        body = GameInstance.getInstance().world.createBody(bodyDef);
        body.setLinearDamping(10f);


        CircleShape shape = new CircleShape();
        shape.setRadius(.5f);

        //shape.setAs(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()
        //        /2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;

        body.createFixture(fixtureDef);
        shape.dispose();

        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;

        //from actor creation shit
        velocity = new Vector2();
        terminalVelocity = new Vector2(1, 1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();

        currentVector = body.getLinearVelocity();

    }

    public AbstractDynamicObject(int id, boolean isNPC) {

        this.id = id;
        facing = "d";



        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        /*bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);*/

        body = GameInstance.getInstance().world.createBody(bodyDef);
        body.setLinearDamping(10f);


        CircleShape shape = new CircleShape();
        shape.setRadius(.5f);

        //shape.setAs(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()
        //        /2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;

        body.createFixture(fixtureDef);
        shape.dispose();

        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;

        //from actor creation shit
        velocity = new Vector2();
        terminalVelocity = new Vector2(1, 1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();

        currentVector = body.getLinearVelocity();

    }

    //TODO Make this more organized, make it so there is not 3 different constructors
    public AbstractDynamicObject(int id, String type) {

        this.id = id;
        facing = "d";

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        if(isNPC)
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        /*bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);*/

        body = GameInstance.getInstance().world.createBody(bodyDef);
        body.setLinearDamping(10f);


        CircleShape shape = new CircleShape();
        shape.setRadius(.5f);

        //shape.setAs(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()
        //        /2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        if (type.equals("sensor"))
            fixtureDef.isSensor = true;

        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;

        body.createFixture(fixtureDef);
        shape.dispose();

        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
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


    public void setLinearV(float vx,float vy){
        getBody().setLinearVelocity(vx,vy);
    }

    public Body getBody() {
        return body;
    }


    public void setAbleToMove(boolean option){
        ableToMove = option;
    }
    /*
    * Moves an object based on physical bounds of the game world
    * */
    public void update(float deltaTime) {

        currentVector = body.getLinearVelocity();

        position = this.body.getPosition();

        //updateMotionX(deltaTime);
        //updateMotionY(deltaTime);
        // Move to new position
        //position.x += velocity.x * deltaTime;
        //position.y += velocity.y * deltaTime;
    }

    public void moveCharacter(int direction){

        Vector2 currentPosition = this.body.getPosition();

        if(!ableToMove) return;

        if(direction == 0){
            float angle = 3 * 90 * MathUtils.degRad;
            this.body.applyForceToCenter(0, -inputForce, true);
            this.body.setTransform(this.body.getPosition(), angle);
        } else if(direction == 1){
            float angle = 1 * 90 * MathUtils.degRad;
            this.body.applyForceToCenter(0, inputForce, true);
            this.body.setTransform(this.body.getPosition(), angle);
        } else if(direction == 2){
            float angle = 2 * 90 * MathUtils.degRad;
            this.body.applyForceToCenter(-inputForce, 0, true);
            this.body.setTransform(this.body.getPosition(), angle);
        } else if(direction == 3){
            float angle = 0 * 90 * MathUtils.degRad;
            this.body.applyForceToCenter(inputForce, 0, true);
            this.body.setTransform(this.body.getPosition(), angle);
        }
        position = currentPosition;

    }
    private int state = 0; //data field for behavior
    public void behavior(int id) {

        if(id == 1) { //npc #1 - lisa
            if(state == 0) {
                setLinearV(0,1);
            }
            if(this.position.y > 21) {
                setLinearV(0, -1);
                state = 1;
            }
            else if(this.position.y < 14) {
                setLinearV(0, 1);
                state = 1;
            }
        }
        if(id == 2) { //npc #2 - other dude
            if(state == 0) {
                setLinearV(1, 0);
            }
            if (this.position.x > 25) {
                setLinearV(-1, 0);
                state = 1;
            }
            else if(this.position.x < 18) {
                setLinearV(1, 0);
                state = 1;
            }
        }

    }


    public abstract void render(SpriteBatch batch);


    public Vector2 getPosition(){
        return position;
    }
    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }


    public void rotateBody(float angle){
        this.body.setTransform(body.getPosition(), angle);
    }


    public Vector2 getVelocity(){
        return currentVector;
    }

    public String toString(){
        return "ID: " + id + "\nPosition: " + position.toString();
    }
}