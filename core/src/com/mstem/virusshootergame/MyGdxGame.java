package com.mstem.virusshootergame;

//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;
/**
 * MainClass, creates, render
 */
public class MyGdxGame extends Game {
	    public static final String TAG = MyGdxGame.class.getName();
    public static final int VIEWPORT_WIDTH = 1000;
    public static final int VIEWPORT_HEIGHT = 558;
    SpriteBatch batch;
    private OrthographicCamera camera;
    
    //variables for checking game conditions
    private float timeRemain = 90f;
    public int winScore = 300;
    public int playerScore = 0;
    private BitmapFont font;
    
    //general
    Texture background, gunTexture;
    private Sprite gunSprite;
    private AnimatedSprite gunAnimated;
    private ShotManager shotManager;
    private Target target;
    
    //collision detection for each type of target
    private CollisionDetect collisionDetectGood;
    private CollisionDetect collisionDetectBad;
    private CollisionDetect collisionDetectVirus;
    //lists of targets
    private ArrayList<Target> goodTargets = new ArrayList<Target>();
    private ArrayList<Target> badTargets = new ArrayList<Target>();
    private ArrayList<Target> virusTargets = new ArrayList<Target>();
    private Random random = new Random();
    private int timeStart = 80;

    /**
     * create
     */
	@Override
	public void create () {

        //Set up the camera with a dimension;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        //for drawing texture and sprites on the screen
		batch = new SpriteBatch();

        //background texture
		background = new Texture(Gdx.files.internal("android/assets/background_mosaic.jpg"));
        //gameoverTexture = new Texture(Gdx.files.internal("android/assets/data/GameOver.png"));

        //font
        font = new BitmapFont();
        font.setColor(1,0,0,1);

        //gun sprite and initial position
        gunTexture = new Texture(Gdx.files.internal("android/assets/data/gun1.png"));
        gunSprite = new Sprite(gunTexture);
        gunAnimated = new AnimatedSprite(gunSprite);
        gunAnimated.setPosition(background.getWidth()/2-(gunSprite.getWidth()/2),30);

        //bullet texture and initialization for action
        Texture shotTexture = new Texture(Gdx.files.internal("android/assets/data/bullet.png"));
        shotManager = new ShotManager(shotTexture);

        //Target setup
        setupTargets();

        //Collision Detection
        collisionDetectGood = new CollisionDetect(gunAnimated,goodTargets,shotManager);
        collisionDetectBad = new CollisionDetect(gunAnimated, badTargets, shotManager);
        collisionDetectVirus = new CollisionDetect(gunAnimated, virusTargets, shotManager);

	}

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * render the screen
     */
	@Override
	public void render () {
//        //initialize window
        //clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //drawing objects to the screen
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        batch.draw(background, 0, 0);

        drawObjects();

        //font

        batch.end();
	}

    /**
     * update
     */
    public void update() {
        gunAnimated.move();
        for(Target good: goodTargets) {
            good.update();
        }
        for(Target bad: badTargets) {
            bad.update();
        }
        for(Target virus: virusTargets) {
            virus.update();
        }
//        target.update();
        shotManager.update();

	//check collision
        collisionDetectGood.hasCollide();
        collisionDetectBad.hasCollide();
        collisionDetectVirus.hasCollide();

        //addPlayerScore();

        //count down timer
        countDown();
    }
    
    private void drawObjects() {
        //check if game is over
        if(!isGameOver()) {
            //can't seem to move to a method
            gunAnimated.draw(batch);
                for (Target good : goodTargets) {
                    good.draw(batch);
                }

                for (Target bad : badTargets) {
                    bad.draw(batch);
                }

                for (Target virus : virusTargets) {
                    virus.draw(batch);
                }

            shotManager.draw(batch);

            //check user input
            handleInput();
            //actions in response to inputs
            update();

            //check if the game is finished
            isGameOver();

            font.draw(batch, "Score: " + Integer.toString(playerScore), 10,20);
            //font.draw(batch, "Bullets Remain: " + shotManager.shotRemain, VIEWPORT_WIDTH-130, 20);
            font.draw(batch, "Time Remain: " + Integer.toString((int)timeRemain) + "seconds", 120, 20);

        }
        else {
            //batch.draw(gameoverTexture,VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2+80);
            font.draw(batch, "Total Score: " + playerScore, VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2+50);
            font.draw(batch,"Time Used: " + getTimeUsed(), VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2+30);
            //font.draw(batch, "Remaining Bullet: " + shotManager.shotRemain, VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2+10);
            font.draw(batch,"Press R to Start a New Game Press Q: Return to Main Menu", VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2 -10);
        }
    }

    /**
     * check for user input and reacts
     */
    private void handleInput() {
        //if left or a is pressed
        if (gunSprite.getX() > 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                    Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    gunAnimated.moveLeft(500);
                }
                else {
                    gunAnimated.moveLeft(200);
                }
            }
        }
        //if right or d is pressed
        if(gunSprite.getX() < background.getWidth() - gunSprite.getWidth()){
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                    Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    gunAnimated.moveRight(500);
                }
                else {
                    gunAnimated.moveRight(200);
                }
            }
        }
        //if up or space or w is pressed
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) ||
                Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.W)) {
            shotManager.fire(gunAnimated.getX());
        }
    }
    
    /**
     * Check if the any of the game over condition are meet
     * @return
     */
    private boolean isGameOver() {
        /*if(shotManager.shotRemain == 0 || timeRemain <= 0f || playerScore >= winScore)
            return true;*/
        return false;
    }

    /**
     * count down timer
     * @return
     */
    private float countDown() {
        if(isGameOver()) {
            return timeRemain += 0;
        }

        return timeRemain -= Gdx.graphics.getDeltaTime();
    }

    /**
     * Get the time used to finish the game
     * @return
     */
    private int getTimeUsed() {
        if(timeRemain <= 0){
            return timeStart;
        }
        return timeStart - (int)timeRemain;
    }
    /**
     * sets up the targets
     */
    public void setupTargets() {
        Target target;
        //for reading files for each target
        String[] targetGoodNames = {"update.png", "changepassword.png", "intallanti.png","runscan.png"} ;
        String[] targetBadNames = {"attachment.png", "ad.png", "unknownsender.png", "freeitem.png"
                ,"facebookapp.png"};
        String[] targetVirus = {"adware.png", "spyware.png" };
        for(int i = 0; i < targetGoodNames.length; i++) {
            Texture targetTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetGoodNames[i]));
            String tempName = targetGoodNames[i].replace(".png", "");
            //target = new Target(targetTexture, random.nextInt(50)+20, 25, tempName);
            //goodTargets.add(target);
        }
        for(int i = 0; i < targetBadNames.length; i++) {
            Texture adwareTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetBadNames[i]));
            String tempName = targetBadNames[i].replace(".png", "");
            //target = new Target(adwareTexture, random.nextInt(80)+40, -10, tempName);
            //badTargets.add(target);
        }
        for(int i = 0; i < targetVirus.length; i++) {
            Texture targetTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetVirus[i]));
            String tempName = targetVirus[i].replace(".png", "");
            //target = new Target(targetTexture, random.nextInt(150)+80, 50, tempName);
            //virusTargets.add(target);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

}//End of MyGdxGame.java
