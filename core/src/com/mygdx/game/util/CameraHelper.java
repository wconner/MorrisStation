package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.AbstractDynamicObject;

/**
 * Created by Ian on 12/22/2014.
 */
public class CameraHelper {

    /**
     * Two classes to choose a target in different ways
     */
    private AbstractDynamicObject targetAbstract;
    private Sprite target;

    private static final String TAG = CameraHelper.class.getName();

    /**
     * Limits of zooming
     */
    private static final float MAX_ZOOM_IN = 0.25f;
    private static final float MAX_ZOOM_OUT = 10.0f;

    /**
     * To position camera in d space
     */
    private Vector2 position;
    private float zoom;

    /**
     * Default constructor
     */
    public CameraHelper() {
        position = new Vector2();
        zoom = 1.0f;
    }

    /**
     * This is more for following a sprite
     */
    public void update(float deltaTime) {
        if (!hasTargetAbstract()) return;

        position.x = targetAbstract.getBody().getPosition().x;
        position.y = targetAbstract.getBody().getPosition().y;
    }

    /**
     * set (x,y) position manually
     */
    public void setPosition(float x, float y) {

        this.position.set(x, y);
    }

    /**
     * return a vector position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Zoom helpers
     */
    public void addZoom(float amount) {
        setZoom(zoom + amount);
    }

    public void setZoom(float zoom) {
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public float getZoom() {
        return zoom;
    }

    /**
     * Camera will select the sprite passed in
     */
    public void setTarget(Sprite target) {
        this.target = target;
    }

    public Sprite getTarget() {
        return target;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public boolean hasTarget(Sprite target) {
        return hasTarget() && this.target.equals(target);
    }

    /**
     * Apply camera updates to world
     * TODO this method ties the viewport camera and the world camera together
     */
    public void applyTo(OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }

    public void setTargetAbstract(AbstractDynamicObject target) {
        this.targetAbstract = target;
        Gdx.app.debug("CamHelper", "Target set\n" +
                this.targetAbstract.toString());
    }

    public boolean hasTargetAbstract() {
        return targetAbstract != null;
    }

    public AbstractDynamicObject getTargetAbstract() {
        return targetAbstract;
    }
}
