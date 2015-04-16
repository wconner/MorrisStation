package com.mygdx.game;

/**
 * Created by Ian on 4/13/2015.
 */


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.MapBodyManager;

public class GameInstance {

    public boolean debugMode = false;

    //public Array<Ship> fighters = new Array<Ship>();

    public int difficultyConfig = 0;
    public int factoryHealthConfig = 0;
    public int antiAliasConfig = 0;


    public World world = new World(new Vector2(0,0),false);
    //public MapBodyManager mapBodyManager = new MapBodyManager(world, Constants.UNIT_SCALE, null, Application.LOG_DEBUG);


    public static GameInstance instance;

    public static GameInstance getInstance() {
        if (instance == null) {
            instance = new GameInstance();
        }
        return instance;
    }

    public void resetGame() {
        //fighters.clear();

        //Preferences prefs = Gdx.app.getPreferences("paxbritannica");
        //GameInstance.getInstance().difficultyConfig  = prefs.getInteger("difficulty",0);
        //GameInstance.getInstance().factoryHealthConfig  = prefs.getInteger("factoryHealth",0);
        //GameInstance.getInstance().antiAliasConfig  = prefs.getInteger("antiAliasConfig",1);
    }

    /*public void bulletHit(Ship ship, Bullet bullet) {
        bullet.facing.nor();
        float offset=0;
        if(ship instanceof FactoryProduction) offset = 50;
        if(ship instanceof Frigate) offset = 20;
        if(ship instanceof Bomber) offset = 10;
        if(ship instanceof Fighter) offset = 10;
        Vector2 pos = new Vector2().set(bullet.collisionCenter.x + (offset * bullet.facing.x), bullet.collisionCenter.y + (offset * bullet.facing.y));

        // ugh. . .
        Vector2 bullet_vel = new Vector2().set(bullet.velocity);

        Vector2 bullet_dir;
        if (bullet_vel.dot(bullet_vel) == 0) {
            bullet_dir = new Vector2();
        } else {
            bullet_dir = bullet_vel.nor();
        }
        Vector2 vel = new Vector2(bullet_dir.x * 1.5f, bullet_dir.y * 1.5f);

        if (bullet instanceof Laser) {
            laser_hit(pos, vel);
        } else if (bullet instanceof Bomb) {
            explosionParticles.addMediumExplosion(bullet.position);
        } else if (bullet instanceof Missile) {
            explosionParticles.addTinyExplosion(bullet.position);
        }
    }
*/
/*
    public void laser_hit(Vector2 pos, Vector2 vel) {
        sparkParticles.addLaserExplosion(pos, vel);
    }
*/

/*    public void explode(Ship ship) {
        explode(ship, ship.collisionCenter);
    }*/

/*
    public void explode(Ship ship, Vector2 pos) {

        if (ship instanceof FactoryProduction) {
            explosionParticles.addBigExplosion(pos);
        } else if (ship instanceof Bomber) {
            explosionParticles.addMediumExplosion(pos);
        } else if (ship instanceof Frigate) {
            explosionParticles.addMediumExplosion(pos);
        }else {
            explosionParticles.addSmallExplosion(pos);
        }
    }
*/

}