package com.emailgame;

/**
 * Created by Ian on 12/22/2014.
 */
public class Constants {

    /*
    * The constants class represents a holding place for
    * various pieces of config data we hold on to for various classes
    * in particular it holds the scale of the tiled game world
    * as well as directory information for various textures
    * */

    //GAME_WORLD makes a square world
    public static float GAME_WORLD = 40f;


    public static final float UNIT_SCALE = 1 / 16f;

    //world dimensions
    public static final float WORLD_WIDTH= 100f;
    public static final float WORLD_HEIGHT = 100f;

    // Visible game world is 5 meters wide
    public static final float VIEWPORT_WIDTH = 25f;
    // Visible game world is 5 meters tall
    public static final float VIEWPORT_HEIGHT = 25f;

    // GUI Width
    public static final float VIEWPORT_GUI_WIDTH = 800.0f;
    // GUI Height
    public static final float VIEWPORT_GUI_HEIGHT = 480.0f;

    // Location of description file for texture atlas
    public static final String TEXTURE_ATLAS_OBJECTS =
            "android/assets/test.pack";

    // Location of image file for level 01
    public static final String LEVEL_01 = "android/assets/levels/level-01.png";

    public static final int LIVES_START = 3;

    //Tom's game
    public static final float ROW_WIDTH = 300f;
    public static final float PREVIEW_CELL_WIDTH = 100f;
    public static final float EMAIL_TEXT_SIZE = .8f;
}
