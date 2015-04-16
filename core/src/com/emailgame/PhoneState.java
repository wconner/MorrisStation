package com.emailgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.util.ArrayList;


/**
 * Created by Tom on 3/29/15.
 *
 */
public class PhoneState extends DefaultScreen{

    public static final FileLocation fileLocation = FileLocation.ABSOLUTE;

    private SpriteBatch sb;
    private BitmapFont font;
    private EmailParser ep;
    private Skin skin;
    private TextureAtlas atlas;
    private String answerString, userString;

    private ArrayList<Email> tutEmails;

    private int questionAmount, score;
    private Tutorial tutorial;

    private Table emailTable;

    private boolean tutorialBool, buttonPressed, trashBool, sentBool, spamBool, inboxBool;

    private EmailTextButtons[] emailItems;
    private EmailTextButtons[] emailOptions;

    private Inbox inbox;
    private Trash trash;
    private MarkedAsSpam spam;
    private Sent sent;

    private SpamFilter spamFilter;

    private Email tempEmail;

    private Label panelLabel;

    private ShapeRenderer shapeRenderer;

    public PhoneState(Stage stage, Game game){
        super(stage, game);

        sb = new SpriteBatch();
        font = new BitmapFont();
        ep = new EmailParser();
        inbox = new Inbox();
        trash = new Trash();
        spam = new MarkedAsSpam();
        sent = new Sent();
        spamFilter = new SpamFilter();
        tempEmail = null;

        inboxBool = true;
        buttonPressed = false;
        trashBool = false;
        sentBool = false;
        spamBool = false;

        tutorial = new Tutorial();

        //fonts
        font.setColor(Color.BLACK);

        //tools for setting filter
        answerString = "";
        userString = "fill";
        tutorialBool = false;
        questionAmount = 0;

        //Skin assets
        atlas = new TextureAtlas(fileLocation.getFile("android/assets/ui_skin/uiskin.atlas"));
        skin = new Skin(fileLocation.getFile("android/assets/ui_skin/uiskin.json"), atlas);


        //email table for displaying inbox and email details
        emailTable = new Table(skin);
        emailTable.setPosition(150, 430);
        emailTable.setOrigin(Align.topLeft, Align.topLeft);
        emailTable.align(Align.topLeft);

        panelLabel = new Label("Inbox", skin);
        panelLabel.setColor(Color.BLACK);
        panelLabel.setPosition(200, 450);

        shapeRenderer = new ShapeRenderer();


        //TODO: I do not like how I am hard coding these positions
        emailItems = new EmailTextButtons[]{new EmailTextButtons("Inbox (I)", 15, 440), new EmailTextButtons("Trash (O)", 15, 390), new EmailTextButtons("Sent (K)", 15, 340), new EmailTextButtons("Spam (J)", 15, 290), new EmailTextButtons("Tutorial (T)", 15, 240)};
        emailOptions = new EmailTextButtons[]{new EmailTextButtons("Delete (Z)", 200, 25), new EmailTextButtons("Reply (X)", 300, 25), new EmailTextButtons("Mark as Spam (C)", 400, 25), new EmailTextButtons("Forward (V)", 550, 25)};

    }

    /**
     * Checks the input from the user via the keys
     */
    public void handleInput(){

        //opens the inbox
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            inboxBool = true;
            emailTable.clearChildren();
            panelLabel.setText("Inbox");
        }

        //opens the next email
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            emailTable.clearChildren();
            if(!inbox.getEmails().isEmpty()){
                tempEmail = inbox.getNextEmail();
                answerString = tempEmail.getEmailType().trim();
                emailTable.add(tempEmail.getFullMessageView());
            }else{
                emailTable.add("Your Inbox is empty");
            }
        }

        //opens the spam folder
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
            spamBool = true;
            emailTable.clearChildren();
            panelLabel.setText("Spam Folder");
        }

        //opens the sent email folder
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
            sentBool = true;
            emailTable.clearChildren();
            panelLabel.setText("Sent Mail");
        }

        //opens the trash
        if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
            trashBool = true;
            emailTable.clearChildren();
            panelLabel.setText("Trash");
        }

        //delete emails
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            userString = "delete";
            if(!inbox.getEmails().isEmpty()){
                trash.getDeleted().add(0, tempEmail);
                trash.trimList();
                answerCheck(userString);
                emailHandled();
            }else{
                tempEmail = null;
            }
        }

        //mark emails as spam
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            userString = "mark as spam";
            if(!inbox.getEmails().isEmpty()){
                spam.getSpam().add(0, tempEmail);
                spam.trimList();
                answerCheck(userString);
                emailHandled();
            }else{
                tempEmail = null;
            }
        }

        //reply to emails
        if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
            userString = "reply";
            if(!inbox.getEmails().isEmpty()){
                sent.getSent().add(0, tempEmail);
                sent.trimList();
                answerCheck(userString);
                emailHandled();
            }else{
                tempEmail = null;
            }
        }

        //forwards emails
        if(Gdx.input.isKeyJustPressed(Input.Keys.V)){
            userString = "forward";
            if(!inbox.getEmails().isEmpty()){
                sent.getSent().add(0, tempEmail);
                sent.trimList();
                answerCheck(userString);
                emailHandled();
            }else{
                tempEmail = null;
            }
        }

        //clears the email table
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            emailTable.clearChildren();
            panelLabel.setText("");
        }

        //starts the tutorial
        if(Gdx.input.isKeyJustPressed(Input.Keys.T)){

        }

        /**
         * for testing purposes only
         */
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            inbox.getMoreMail();
            inbox.trimList();
            emailTable.clearChildren();
            inboxBool = true;
        }
    }


    //new methods
    @Override
    public void show(){

    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        //starts drawing
        sb.begin();

        emailTable.draw(sb, 10);
        panelLabel.draw(sb, 10);
        emailTable.setColor(0, 0, 255, 10);

        //opening the inbox
        if(inboxBool){
            if(inbox.getEmails().isEmpty()){
                emailTable.add("Your Inbox is empty");
                inboxBool = false;
            }else{
                emailTable.add(inbox.makeInbox());
                inboxBool = false;
            }
        }

        if(spamBool){
            if(spam.getSpam().isEmpty()){
                emailTable.add("You have no Spam Email");
                spamBool = false;
            }else{
                emailTable.add(spam.makeSpam());
                spamBool = false;
            }
        }

        if(sentBool){
            if(sent.getSent().isEmpty()){
                emailTable.add("You have no Sent Mail");
                sentBool = false;
            }else{
                emailTable.add(sent.makeSent());
                sentBool = false;
            }
        }

        if(trashBool){
            if(trash.getDeleted().isEmpty()){
                emailTable.add("Your Trash is empty");
                trashBool = false;
            }else{
                emailTable.add(trash.makeTrash());
                trashBool = false;
            }
        }

        //TODO:these numbers need to changed according to the screen size
        for(int i = 0; i < emailItems.length; i++){
            font.draw(sb, emailItems[i].getName(), emailItems[i].getXCoord(), emailItems[i].getYCoord());
        }

        for(int i = 0; i < emailOptions.length; i++){
            //TODO: This looks incredibly ugly they need to be positioned properly for(int i = 0; i < emailOptions.length; i++){
            font.draw(sb, emailOptions[i].getName(), emailOptions[i].getXCoord(), emailOptions[i].getYCoord());
        }


        //setting the filter level
        font.draw(sb, "Filter Level: " + spamFilter.returnFilterLevel(), 10, 20);

        //ends drawing
        sb.end();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        //black
        shapeRenderer.setColor(0, 0, 0, 4);
        //email panel
        shapeRenderer.rect(140, 125, 450, 325);
        //email items
        shapeRenderer.rect(10, 150, 80, 300);
        //email options
        shapeRenderer.rect(180, 5, 455, 25);
        //red
        shapeRenderer.setColor(Color.RED);
        //filter level
        shapeRenderer.rect(5, 5, 150, 25);
        shapeRenderer.end();
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){

    }

    public void emailHandled(){
        emailTable.clearChildren();

        if(inbox.getEmails().isEmpty()){
            emailTable.add("Your Inbox is empty");
        }else{
            inbox.getEmails().remove(0);
            if(!inbox.getEmails().isEmpty()){
                tempEmail = inbox.getNextEmail();
                answerString = tempEmail.getEmailType().trim();
                emailTable.add(tempEmail.getFullMessageView());
            }else{
                emailTable.add("Your Inbox is empty");
            }
        }
    }

    /**
     * Compares the string of the button pressed by the user
     * to the string that is for the email type
     *
     * @param user
     */
    public void answerCheck(String user){
        if(user.equals(answerString)){
            spamFilter.incrementCorrect();
        }
        spamFilter.incrementTotal();
        spamFilter.calcPercentage();
    }


}
