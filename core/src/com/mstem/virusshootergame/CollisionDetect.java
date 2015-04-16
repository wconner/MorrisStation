package com.mstem.virusshootergame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catherinehuang on 4/13/15.
 */
public class CollisionDetect {

    private AnimatedSprite gun;
    private List<Target> target = new ArrayList<Target>();
    private final ShotManager shotManager;
    private int collide;

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
                collide++;
            }
        }
    }
    
    public int getNumberOfHits() {
        return collide;
    }
    
}//End of CollisionDetect
