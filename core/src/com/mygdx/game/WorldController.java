package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.AbstractDynamicObject;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.levels.BedroomLevel;
import com.mygdx.game.levels.Level;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.DialogButtons;
import com.mygdx.game.screens.gui.DialogWindow;
import com.mygdx.game.util.CameraHelper;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.JsonParser;
import com.mygdx.game.util.MapBodyManager;

/**
 * Created by Ian on 12/21/2014.
 * This class is designed to hold the logic of the game elements and provide access
 * to the other modules as well as control input
 */
@SuppressWarnings("NonJREEmulationClassesInClientCode")
public class WorldController implements InputProcessor {

    private static final String TAG = WorldController.class.getName();

    public Level level;
    public MapBodyManager bodyManager;
    public CameraHelper cameraHelper;
    public DialogWindow dialogWindow;
    private DialogButtons DB;
    private Stage stage;
    private JsonParser jsonParser;
    public Array<AbstractDynamicObject> actors;
    public Player player;
    private NPC target;
    private GameScreen gameScreen;
    private Fixture fixtureA, fixtureB;


    public WorldController(Stage stage, GameScreen gameScreen, Level level) {
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.level = level;
        init();
    }

    /**
     * Build class
     */
    private void init() {
        Gdx.input.setInputProcessor(this);

        bodyManager = new MapBodyManager(GameScreen.world, 16, null, Application.LOG_DEBUG);

        actors = level.getActors();
        cameraHelper = new CameraHelper();
        cameraHelper.setPosition(Constants.GAME_WORLD / 2, Constants.GAME_WORLD / 2);

        if (actors.size > 1)
            jsonParser = ((NPC) actors.get(1)).getJsonTest();

        /**Initiate everything*/
        initActors();
        initUI();

        bodyManager.createPhysics(level.getMap(), "Obstacles");
        createCollisionListener();
    }

    public void initInput() {
        Gdx.input.setInputProcessor(this);
    }

    public void initUI() {
        dialogWindow = new DialogWindow();
        DB = new DialogButtons(stage, this);
        if (!((BedroomLevel) gameScreen.getLevels().get(0)).isDoorActive()) { /** Set Dialog box for first time entering game */
            stage.addActor(dialogWindow.makeWindow());
            dialogWindow.setText("Something has gone wrong.  I should go talk to Bit Daemon the robot over there.");
        } else {
            stage.addActor(dialogWindow.makeWindow());
            dialogWindow.hide();
        }
    }

    /**
     * Create actors
     */
    private void initActors() {
        for (AbstractDynamicObject a : actors) {
            if (a.getID() == 0)                                             /** If it is player */
                a.getBody().setTransform(a.position.x + a.getWidth(), a.position.y + a.getHeight(), 0);
            else if (a.getID() < 100)                                       /** If its an NPC */
                a.getBody().setTransform(a.position.x + a.getWidth(), a.position.y + a.getHeight(), 0);
            else                                                            /** For sensors */
                a.getBody().setTransform(a.position.x, a.position.y, 0);
        }

        player = ((Player) actors.get(0));
        cameraHelper.setTargetAbstract(player);
    }

    /**
     * This method is called constantly and updates the model and view
     * <p>
     * Important note about handleDebugInput()
     * this class is called with a helper class to ensure all
     * input has been processed before rendering this class needs to be
     * called first to ensure user input is handled BEFORE logic is executed
     * ORDER IS IMPORTANT
     */
    public void update(float deltaTime) {

        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);

        for (int i = 1; i < actors.size; i++) {
            ((NPC) actors.get(i)).behavior(deltaTime);
            actors.get(i).update(deltaTime);
        }
        stage.act();
        stage.draw();

        GameScreen.world.step(1 / 45f, 2, 6);
    }

    private void createCollisionListener() {
        GameScreen.world.setContactListener(new ContactListener() {

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
    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

        if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP))
            player.moveCharacter(1);

        if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
            player.moveCharacter(2);

        if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN))
            player.moveCharacter(0);

        if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
            player.moveCharacter(3);

        if (Gdx.input.isKeyPressed(Keys.F)) {
            float angle = 1 * 90 * MathUtils.degRad;
            player.getBody().setTransform(player.getBody().getPosition(), angle);
            System.out.println("SPEED: " + player.body.getLinearVelocity().x + player.body.getLinearVelocity().y);
        }

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
    public void moveCamera(float x, float y) {
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
    public boolean keyUp(int keycode) {
        // Reset game world
        if (keycode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world reset");
            return true;
        } else if (keycode == Keys.P || keycode == Keys.ESCAPE)
            gameScreen.toggle();
        else if (keycode == Keys.V) {
            initInput();
            dialogWindow.hide();
            return true;
        }

        /** This is now the action key */
        else if (keycode == Keys.SPACE) {
            Contact contact;
            boolean eventFound = false;
            int i = 0;
            dialogWindow.show();
            dialogWindow.getWindow().getTitleLabel().setText("Player");
            dialogWindow.setText("What a pretty day in MorrisTown :)");     /** For hitting space with no contacts */

            while (!eventFound && i < GameScreen.world.getContactCount()) {      /** This is a while loop and not for so we stop looking for contacts */
                contact = GameScreen.world.getContactList().get(i);              /** after finding the first relevant contact (relevant makes eventFound = true) */
                fixtureA = contact.getFixtureA();
                fixtureB = contact.getFixtureB();

                if (fixtureB.getBody() == player.getBody())
                    swapFixtures();
                if (fixtureA.getBody() == player.getBody()) {

                    for (AbstractDynamicObject a : actors)              /** Checking to see if talking to an actor. */
                        if (fixtureB.getBody() == a.getBody()) {
                            eventFound = true;
                            target = (NPC) a;
                            dialogueStart();
                        }
                    if (!eventFound) {
                        if (fixtureB.isSensor()) {
                            eventFound = true;
                            commandWord((String) fixtureB.getBody().getUserData());
                        } else /** For hitting space with a non contact entity */
                            dialogWindow.setText("Nothing to do there :(");
                    }
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

    private void dialogueStart() {
        gameScreen.pause();

        target.initializeDialog();
        for (Actor a : stage.getActors())
            if (a.getName() != null)
                if (a.getName().equals("DB")) {
                    a.clear();
                    a.remove();
                }
        window = DB.makeWindow(jsonParser.getDialogOptions());
        stage.addActor(window);
        dialogWindow.getWindow().getTitleLabel().setText(target.getName());
        dialogWindow.setText(jsonParser.getDialog());
    }

    public void updateDialog(int optionSelected) {
        if (jsonParser.getUpdatedDialogID(optionSelected) != -1) {
            target.setDialogID(jsonParser.getUpdatedDialogID(optionSelected));
            dialogueStart();
        } else {
            DB.hide();
            dialogWindow.hide();
            window.remove();
            gameScreen.unPause();
            initInput();
        }
    }

    //@TODO explain what the fuck you're doing here, and maybe change it to something less cryptic.
    private void commandWord(String c) {
        if (c != null)
            switch (c) {
                case "BRD":
                    changeLevels(0);
                    break;
                case "door":
                    if (((BedroomLevel) gameScreen.getLevels().get(0)).isDoorActive())
                        changeLevels(1);
                    else
                        dialogWindow.setText("The security protocols have been reset and the door is locked.");
                    break;
                case "BLComputer":
                    gameScreen.screenSwap("Mastermind");
                    break;
                case "leftCabinet":
                    gameScreen.screenSwap("Password");
                    break;
                default:
                    return;
            }
    }

    private void changeLevels(int levelToChangeTo) {
        bodyManager.destroyPhysics();   /** Destroys all physics for structures */
        Array<Integer> n = new Array<Integer>();
        for (int i = 0; i < 16; i++)
            n.insert(i, 0);
        for (int i = 1; i < actors.size; i++)
            n.insert((actors.get(i)).getID(), ((NPC) actors.get(i)).getDialogID());
        level.returnDialogIDs(n);
        while (actors.size != 0) {      /** Destroys the physics for the actors */
            actors.pop().remove();
        }
        dialogWindow.getWindow().remove();            /** Destroys the dialog window (which may be hidden) */
        gameScreen.setLevel(levelToChangeTo);
    }

    /**
     * Simple method to switch FixtureA and FixtureB.
     */
    private void swapFixtures() {
        Fixture temp = fixtureA;
        fixtureA = fixtureB;
        fixtureB = temp;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public DialogWindow getDialog() {
        return dialogWindow;
    }

    public Stage getStage() {
        return stage;
    }

    public Level getLevel() {
        return level;
    }

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
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
