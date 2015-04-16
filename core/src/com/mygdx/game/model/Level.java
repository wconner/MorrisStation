package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.AbstractDynamicObject;
import com.mygdx.game.Background;
import com.mygdx.game.old.Clouds;
import com.mygdx.game.old.Ground;


/**
 * Created by Ian on 2/10/2015.
 */
public class Level {

    private static int numOfTiles = 1;

    public static final String TAG = Level.class.getName();

    public enum BLOCK_TYPE {
        EMPTY(1, 1, 1), // black
        GROUND(1, 255, 1), // green
        PLAYER_SPAWNPOINT(255, 255, 255); // white
        private int color;
        private BLOCK_TYPE (int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor (int color) {
            return this.color == color;
        }

        public int getColor () {
            return color;
        }
    }

    // objects
    public Array<Ground> platforms;
    // decoration
    public Clouds clouds;
    public Background background;

    public Level (String filename) {
        init(filename);
    }

    private void init (String filename) {
        // objects
        platforms = new Array<Ground>();

        // load image file that represents the level data
        Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));

        // scan pixels from top-left to bottom-right
        int lastPixel = -1;
        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {

                AbstractDynamicObject obj = null;
                float offsetHeight = 0;

                // height grows from bottom to top
                float baseHeight = pixmap.getHeight() - pixelY;

                // get color of current pixel as 32-bit RGBA value
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                // find matching color value to identify block type at (x,y)
                // point and create the corresponding game object if there is
                // a match

                // empty space
                if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
                    //Gdx.app.debug("MyTag", "my debug message");
                }

                // ground
                else if (BLOCK_TYPE.GROUND.sameColor(currentPixel)) {
                    if (lastPixel != currentPixel) {
                        obj = new Ground(numOfTiles++);
                        float heightIncreaseFactor = 0.25f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX,
                                baseHeight * obj.dimension.y
                                        * heightIncreaseFactor
                                        + offsetHeight);
                        platforms.add((Ground) obj);
                    } else {
                        platforms.get(platforms.size - 1).increaseLength(1);
                    }
                }

                // player spawn point
                else if
                        (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
                }

                // unknown object/pixel color
                else {
                    int r = 0xff & (currentPixel >>> 24); //red color channel
                    int g = 0xff & (currentPixel >>> 16); //green color channel
                    int b = 0xff & (currentPixel >>> 8); //blue color channel
                    int a = 0xff & currentPixel; //alpha channel
                    Gdx.app.error(TAG, "Unknown object at x<" + pixelX
                            + "> y<" + pixelY
                            + ">: r<" + r
                            + "> g<" + g
                            + "> b<" + b
                            + "> a<" + a + ">");
                }
                lastPixel = currentPixel;
            }

            // decoration
            clouds = new Clouds(numOfTiles,pixmap.getWidth());
            clouds.position.set(0, 2);
            background = new Background(numOfTiles,pixmap.getWidth());
            background.position.set(-1, -1);

            // free memory
            //pixmap.dispose();
            Gdx.app.debug(TAG, "level '" + filename + "' loaded");

        }
    }

    public void render (SpriteBatch batch) {
        // Draw Mountains
        //background.render(batch);

        // Draw Rocks
        //for (Ground rock : platforms)
            //rock.render(batch);

        // Draw Clouds
        //clouds.render(batch);
    }


}
