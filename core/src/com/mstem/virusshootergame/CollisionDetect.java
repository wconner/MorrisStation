package com.mstem.virusshootergame;

import com.mstem.virusshootergame.AnimatedSprite;
import com.mstem.virusshootergame.ShotManager;
import com.mstem.virusshootergame.Target;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by catherinehuang on 4/13/15.
 */
public class CollisionDetect {

    private AnimatedSprite gun;
    private List<Target> target = new ArrayList<Target>();
    private final ShotManager shotManager;

    /**
     * Constructor
     * @param gun
     * @param target
     * @param shotManager
     */
    public CollisionDetect(AnimatedSprite gun, ArrayList<Target> target, ShotManager shotManager) {
        this.gun = gun;
        this.target = target;
        this.shotManager = shotManager;
    }

    /**
     * Check if the object has collide with each other
     */
    public void hasCollide() {
        for(Target t: target) {
            if (shotManager.bulletHits(t.getBoundingBox())) {
                t.hit();
            }
        }
    }
}//End of CollisionDetect
