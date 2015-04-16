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
    //utilities
    public static final String TAG = MyGdxGame.class.getName();
    public static final int VIEWPORT_WIDTH = 1000;
    public static final int VIEWPORT_HEIGHT = 558;
    SpriteBatch sb;
    private OrthographicCamera camera;

    //variables for checking game conditions
    private float timeRemain = 90f;
    public int winScore = 300;
    public int playerScore = 0;
    private BitmapFont font;

    //general
    Texture background, gunTexture, gameoverTexture;
    private Sprite gunSprite;
    private AnimatedSprite gunAnimated;
    private ShotManager shotManager;
    //collision detection for each type of target
    private CollisionDetect collisionDetectGood;
    private CollisionDetect collisionDetectBad;
    private CollisionDetect collisionDetectVirus;
    //list of targets
    private ArrayList<Target> goodTargets = new ArrayList<Target>();
    private ArrayList<Target> badTargets = new ArrayList<Target>();
    private ArrayList<Target> virusTargets = new ArrayList<Target>();
    private Random random = new Random();
    private int timeStart = 80;

    //window and message
    private TargetMessage messenger;
    private BitmapFont message;
    private float messageShowtime = 10f;
    private String targetName = "";
    private CollisionDetect currentCollide;

    private float timeZero = 0f;

//    /**
//     * Constructor
//     */
//    public MyGdxGame() {
//        this.create();
//    }

    /**
     * create
     */
	@Override
	public void create () {

        //Set up the camera with a dimension;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        //for drawing texture and sprites on the screen
		sb = new SpriteBatch();

        //background texture
		background = new Texture(Gdx.files.internal("android/assets/data/background_mosaic.jpg"));
        gameoverTexture = new Texture(Gdx.files.internal("android/assets/data/GameOver.png"));

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

        //Collision Detection for all targets
        collisionDetectGood = new CollisionDetect(gunAnimated,goodTargets,shotManager);
        collisionDetectBad = new CollisionDetect(gunAnimated, badTargets, shotManager);
        collisionDetectVirus = new CollisionDetect(gunAnimated, virusTargets, shotManager);

        //messages
        message = new BitmapFont();
        message.setColor(1,1,1,1);
        messenger = new TargetMessage();

	}

    @Override
    public void dispose() {
        sb.dispose();
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
        sb.setProjectionMatrix(camera.combined);
		sb.begin();
        sb.draw(background, 0, 0);
        if(!checkDrawMessage()) {
            drawObjects();
        }
        else {
            drawMessage();
        }

        //font

        sb.end();
	}

    private void drawMessage() {
            if(checkDrawMessage()){
                String temp = messenger.randomPick(targetName,currentCollide);
//                System.out.println(temp);
                message.draw(sb, temp, VIEWPORT_WIDTH / 6, VIEWPORT_HEIGHT / 2);
            }
            else{
                targetName = "not yet implemented";
            }

    }

    private boolean checkDrawMessage() {
        for(Target virus: virusTargets) {
            if(virus.checkDestroy()){
                targetName = virus.getName();
                currentCollide = collisionDetectVirus;
                return true;
            }
        }
        for(Target good: goodTargets) {
            if(good.checkDestroy()){
                targetName = good.getName();
                currentCollide = collisionDetectGood;
                return true;
            }
        }
        for(Target bad: badTargets) {
            if(bad.checkDestroy()) {
                targetName = bad.getName();
                currentCollide = collisionDetectBad;
                return true;
            }
        }
        return false;
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
        shotManager.update();

        //check collision
        collisionDetectGood.hasCollide();
        collisionDetectBad.hasCollide();
        collisionDetectVirus.hasCollide();

        addPlayerScore();

        //count down timer
        countDown();
    }

    /**
     * draw the moving objects, goes in render
     */
    private void drawObjects() {
        //check if game is over
        if(!isGameOver()) {
            //can't seem to move to a method
            gunAnimated.draw(sb);
                for (Target good : goodTargets) {
                    good.draw(sb);
                }

                for (Target bad : badTargets) {
                    bad.draw(sb);
                }

                for (Target virus : virusTargets) {
                    virus.draw(sb);
                }

            shotManager.draw(sb);

            //check user input
            handleInput();
            //actions in response to inputs
            update();

            //check if the game is finished
            isGameOver();

            font.draw(sb, "Score: " + Integer.toString(playerScore), 10,20);
            font.draw(sb, "Bullets Remain: " + shotManager.shotRemain, VIEWPORT_WIDTH-130, 20);
            font.draw(sb, "Time Remain: " + Integer.toString((int)timeRemain) + "seconds", 120, 20);

        }
        else {
            sb.draw(gameoverTexture,VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2+80);
            font.draw(sb, "Total Score: " + playerScore, VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2+50);
            font.draw(sb,"Time Used: " + getTimeUsed(), VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2+30);
            font.draw(sb, "Remaining Bullet: " + shotManager.shotRemain, VIEWPORT_WIDTH/3, VIEWPORT_HEIGHT/2+10);
        }
    }

    /**
     * check for user input and reacts
     */
    private void handleInput() {
        //if z is pressed
        if (gunSprite.getX() > 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
                    gunAnimated.moveLeft(500);
                }
                else {
                    gunAnimated.moveLeft(200);
                }
            }
        }
        //if / is pressed
        if(gunSprite.getX() < background.getWidth() - gunSprite.getWidth()){
            if (Gdx.input.isKeyJustPressed(Input.Keys.SLASH)) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
                    gunAnimated.moveRight(500);
                }
                else {
                    gunAnimated.moveRight(200);
                }
            }
        }
        //if space is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shotManager.fire(gunAnimated.getX());
        }
    }

    /**
     * Check if the any of the game over condition are meet
     * @return
     */
    private boolean isGameOver() {
        if(shotManager.shotRemain == 0 || timeRemain <= 0f || playerScore >= winScore)
            return true;
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
        if(checkDrawMessage()){
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
            target = new Target(targetTexture, random.nextInt(50)+20, 25, tempName);
            goodTargets.add(target);
        }
        for(int i = 0; i < targetBadNames.length; i++) {
            Texture adwareTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetBadNames[i]));
            String tempName = targetBadNames[i].replace(".png", "");
            target = new Target(adwareTexture, random.nextInt(80)+40, -10, tempName);
            badTargets.add(target);
        }
        for(int i = 0; i < targetVirus.length; i++) {
            Texture targetTexture = new Texture(Gdx.files.internal("android/assets/data/" + targetVirus[i]));
            String tempName = targetVirus[i].replace(".png", "");
            target = new Target(targetTexture, random.nextInt(150)+80, 50, tempName);
            System.out.println(tempName);
            virusTargets.add(target);
        }
    }

    public void addPlayerScore() {
        playerScore = collisionDetectBad.getNumberOfHits() * -10 +
                collisionDetectGood.getNumberOfHits()*25 +
                collisionDetectVirus.getNumberOfHits()*50;
    }

    @Override
    public void resize(int width, int height) {

    }

}//End of MyGdxGame.java
