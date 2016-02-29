package com.mygdx.game.model;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.*;
import com.mygdx.game.entities.AbstractDynamicObject;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.levels.Level;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.DialogButtons;
import com.mygdx.game.screens.gui.DialogWindow;
import com.mygdx.game.screens.gui.Display;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.JsonTest;
import com.mygdx.game.util.MapBodyManager;

/**
 * Created by Ian on 12/21/2014.
 *
 * This class is designed to hold the logic of the game elements and provide access
 * to the other modules as well as control input
 *
 */
@SuppressWarnings("NonJREEmulationClassesInClientCode")
public class WorldController implements InputProcessor {

    private static final String TAG = WorldController.class.getName();

    public Level level;
    public MapBodyManager bodyManager;

    /**
    * Creates the camera helper, a utility class for camera manipulation
    */
    public CameraHelper cameraHelper;

    /**
    * The class for displaying the UI
    * Is placed in controller so input has access
    */
    public Display display;
    public DialogWindow dialogWindow;
    private DialogButtons DB;
    private Stage stage;
    private JsonTest jsonTest;

    /**
    * Actor initialization
    * Dude is only active sprite 3/4/2015
    */
    public Array<AbstractDynamicObject> actors;
    public Player player;
    private NPC target;
    /** For collisions */
    private Fixture fixtureA;
    private Fixture fixtureB;

    private boolean visible;
    private GameScreen gameScreen;

    /**
    * Default constructor
    */
    public WorldController (Stage stage, GameScreen gameScreen, Level level) {
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.level = level;
        init(stage);
    }

    /**
    * Build class
    */
    private void init(Stage stage) {
        Gdx.input.setInputProcessor(this);

        bodyManager = new MapBodyManager(GameInstance.getInstance().world,16, null, Application.LOG_DEBUG);

        actors = level.getActors();
        cameraHelper = new CameraHelper();
        cameraHelper.setPosition(Constants.GAME_WORLD / 2, Constants.GAME_WORLD / 2);

        if (actors.size > 1)
            jsonTest = ((NPC) actors.get(1)).getJsonTest();

        /**Initiate everything*/
        initActors();
        initUI(stage);

        bodyManager.createPhysics(level.getMap(), "Obstacles");
        createCollisionListener();
    }

    public void initInput() {  Gdx.input.setInputProcessor(this); }

    public void initUI(Stage stage){
        visible = false;
        display = new Display(stage);
        //dialog = new Display();
        dialogWindow = new DialogWindow();
        DB = new DialogButtons(stage, this);
        stage.addActor(display.makeWindow());
        stage.addActor(dialogWindow.makeWindow());
    }

    /**
    * Create actors
    */
    private void initActors(){
        for (AbstractDynamicObject a : actors){
            if (a.getID() == 0)                                             /** If it is player */
                a.getBody().setTransform(a.position.x + a.getWidth(), a.position.y + a.getHeight(), 0);
            else if (a.getID() < 100)                                       /** If its an NPC */
                a.getBody().setTransform(a.position.x + a.getWidth(), a.position.y + a.getHeight(), 0);
            else                                                            /** For sensors */
                a.getBody().setTransform(a.position.x, a.position.y, 0);
        }

        player = ((Player) actors.get(0));
    }

    /**
    * This method is called constantly and updates the model and view
    *
    * Important note about handleDebugInput()
    * this class is called with a helper class to ensure all
    * input has been processed before rendering this class needs to be
    * called first to ensure user input is handled BEFORE logic is executed
    * ORDER IS IMPORTANT
    */
    public void update (float deltaTime) {

        handleDebugInput(deltaTime);

        cameraHelper.update(deltaTime);
        
        display.setText(cameraHelper.getPosition().toString());

        //array added here to facilitate more actors
        for(AbstractDynamicObject dudes : actors){
            dudes.behavior(dudes.getID(), deltaTime);
            dudes.update(deltaTime);
        }
        stage.act();
        stage.draw();

        GameInstance.getInstance().world.step(1 / 45f, 2, 6);
    }

    private void createCollisionListener() {
        GameInstance.instance.world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                fixtureA = contact.getFixtureA();
                fixtureB = contact.getFixtureB();
                Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void endContact(Contact contact) {
                fixtureA = contact.getFixtureA();
                fixtureB = contact.getFixtureB();
                Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

    /**
    * Enables all kinds of awesome things that we can control when
    * used in conjunction with the CameraHelper class
    */
    private void handleDebugInput (float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

        if(Gdx.input.isKeyPressed(Keys.W)) {
            player.moveCharacter(1);
            //player.setAnimState(2);
        }
        if(Gdx.input.isKeyPressed(Keys.A)) {
            player.moveCharacter(2);
            //player.setAnimState(0);
        }
        if(Gdx.input.isKeyPressed(Keys.S)) {
            player.moveCharacter(0);
            //player.setAnimState(1);
        }
        if(Gdx.input.isKeyPressed(Keys.D)) {
            player.moveCharacter(3);
            //player.setAnimState(3);
        }

        if(Gdx.input.isKeyPressed(Keys.F)){
            float angle = 1 * 90 * MathUtils.degRad;
            player.getBody().setTransform(player.getBody().getPosition(), angle);
            System.out.println("SPEED: " + player.body.getLinearVelocity().x + player.body.getLinearVelocity().y);
        }

     /*
    * Test method for changing screens

        if(Gdx.input.isKeyPressed(Keys.N)) {
            initPhone(new Stage());
        }
*/

        // Camera Controls (move)

        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camMoveSpeed *=
                camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCamera(-camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCamera(camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCamera(0, camMoveSpeed);

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCamera(0,
                -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);

        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *=
                camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(
                -camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
    }

    /**
    * Uses CameraHelper to control the camera position
    */
    public void moveCamera (float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;

        cameraHelper.setPosition(x, y);
    }

    /**
    * InputProcessor Inputs
    */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        // Reset game world
        if (keycode == Keys.R) {
            init(stage);
            Gdx.app.debug(TAG, "Game world reset");
            return true;
        }

        //change text
        else if (keycode == Keys.B){
            dialogWindow.setText(player.randomText());
            return true;

        }

        else if (keycode == Keys.V){
            dialogWindow.hide();
            return true;

        }

        //@TODO Make this a hash?
        /** This is now the action key */
        else if (keycode == Keys.SPACE) {
            Contact contact;
            boolean eventFound = false;
            int i = 0;

            dialogWindow.setText("What a pretty day in MorrisTown :)");     /** For hitting space with no contacts */

            while (!eventFound && i < GameInstance.instance.world.getContactCount()) {      /** This is a while loop and not for so we stop looking for contacts */
                contact = GameInstance.instance.world.getContactList().get(i);              /** after finding the first relevant contact (relevant makes eventFound = true) */
                fixtureA = contact.getFixtureA();
                fixtureB = contact.getFixtureB();

                if (fixtureB.getBody() == player.getBody())
                    swapFixtures();
                if (fixtureA.getBody() == player.getBody()) {
                    if (!dialogWindow.isHidden())                /** Displaying the dialog window. */
                        dialogWindow.hide();

                    for (AbstractDynamicObject a : actors)              /** Checking to see if talking to an actor. */
                        if (fixtureB.getBody() == a.getBody()) {
                            eventFound = true;
                            target = (NPC) a;
                            dialogueStart();
                        }
                        if (!eventFound) {
                            if (fixtureB.isSensor()){
                                dialogWindow.setText("I'm at a sensor.");
                                eventFound = true;
                                commandWord((String) fixtureB.getBody().getUserData());
                            }
                            else /** For hitting space with a non contact entity */
                                dialogWindow.setText("Nothing to do there :(");
                        }

//                    if (fixtureB.getBody() == actors.get(1).getBody()) {           /** Talking to NPC */
//                        dialogWindow.setText("I'm talking to npc!");
//                        eventFound = true;
//                        gameScreen.pauseSwap();
//                        stage.addActor(DB.makeWindow(DC.getArray(101)));
//                    } else if (fixtureB.getBody() == actors.get(2).getBody()) {      /** Talking to NPC2 */
//                        dialogWindow.setText("I'm talking to npc2!!");
//                        eventFound = true;
//                    } else if (fixtureB.getBody() == actors.get(3).getBody()) {  /** At blueHouseSensor */
//                        dialogWindow.setText("I'm entering the blue house ! !");
//                        eventFound = true;
//                        changeLevels("blueHouse");
//                }
                }
                i++;
            }
            dialogWindow.update(stage);
            DB.update(stage);
        }


        // Toggle camera follow
        else if (keycode == Keys.ENTER) {
            //cameraHelper.setTargetAbstract(cameraHelper.hasTarget() ? null : dude);
            cameraHelper.setTargetAbstract(player);
            Gdx.app.debug(TAG, "Camera follow enabled: " +
                    cameraHelper.hasTargetAbstract());
            return true;

        }
        return false;
    }

    private Window window;

    private void dialogueStart(){
        gameScreen.pause();

        target.setDialog();
        for (Actor a : stage.getActors())
            if (a.getName() != null)
                if (a.getName().equals("DB")) {
                    a.clear();
                    a.remove();
                }
        window = DB.makeWindow(jsonTest.getDialogOptions());
        stage.addActor(window);
        dialogWindow.setText(jsonTest.getDialog());
    }

    public void updateDialog(int optionSelected){
        if (jsonTest.getUpdatedDialogID(optionSelected) != -1) {
            target.setDialogID(jsonTest.getUpdatedDialogID(optionSelected));
            dialogueStart();
        }
        else {
            DB.hide();
            dialogWindow.hide();
            window.remove();
            gameScreen.pauseSwap();
            initInput();
        }
    }

    //@TODO explain what the fuck you're doing here, and maybe change it to something less cryptic.
    private void commandWord(String c){
        switch(c){
            case "BRD":
                changeLevels(1);
                break;
            case "door":
                changeLevels(2);
                break;
            case "BLComputer":
                gameScreen.pwGameSwap();
                break;
        }
    }

    private void changeLevels(int levelToChangeTo){
        bodyManager.destroyPhysics();   /** Destroys all physics for structures */
        while (actors.size != 0) {      /** Destroys the physics for the actors */
            actors.pop().remove();
        }
        gameScreen.setLevel(levelToChangeTo);
    }
    /**
     * Simple method to switch FixtureA and FixtureB.
     */
    private void swapFixtures(){
        Fixture temp = fixtureA;
        fixtureA = fixtureB;
        fixtureB = temp;
    }

    public DialogWindow getDialog() {
        return dialogWindow;
    }
    public Display getDisplay() {
        return display;
    }
    public Stage getStage(){
        return stage;
    }
    public Level getLevel(){ return level;}

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if(stage.hit(screenX, screenY, true) != null)
            visible = true;
        else
            visible = false;
        return true;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}