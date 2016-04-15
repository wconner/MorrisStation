package com.testoverlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.MainClass;
import com.mygdx.game.levels.BedroomLevel;
import com.mygdx.game.levels.Level;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.TouchUpListener;
import com.mygdx.game.util.EmailTable;
import com.mygdx.game.util.JsonTest;

import java.util.ArrayList;

public class EmailGame extends DefaultScreen implements InputProcessor {
   // private TextField inputBox;
   // private Label label;
    private boolean isGameOver;

    private final InputListener checkListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(!isGameOver) {
                int goodNum;
                if(stageLevel<9){
                    goodNum = 2;
                }
                else{
                    goodNum = 4;
                }
                if(!notEnoughChecked(goodNum)) {
                    if (checkEmails("good")) {
                        helpWindow.setText(systemMessageReader.getItemField("systemMessages", "wellDone"));
                        isGameOver = true;
                        ((BedroomLevel) gameScreen.getLevels().get(0)).setDoorActive(true);
                    } else if (checkEmails("spam")) {
                        helpWindow.setText(systemMessageReader.getItemField("systemMessages", "spamEmails"));
                    } else if (checkEmails("bad")) {
                        helpWindow.setText(systemMessageReader.getItemField("systemMessages", "badEmails"));
                    }
                }else{
                    helpWindow.setText(systemMessageReader.getItemField("systemMessages", "notEnough"));
                }

                for (EmailTable emailTable : emailTableList) {
                    if (emailTable.getCheckBox().isChecked()) {
                        emailTable.getCheckBox().toggle();
                    }
                }
            }
        }
    };

    private final InputListener instructListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(!isGameOver) {
                helpWindow.setText(systemMessageReader.getItemField("systemMessages", "instructions1"));
            }
        }
    };

    private final InputListener helpListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(!isGameOver) {
                helpWindow.setText(systemMessageReader.getItemField("hints", "scams"));
            }
        }
    };

    private final InputListener backListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            gameScreen.getWorldController().getDialog().hide();
            game.setScreen(gameScreen);
            for(Actor a: stage.getActors()) {
                a.setVisible(false);
            }
            gameScreen.getWorldController().initInput();
            gameScreen.resume();
        }
    };


    private GameScreen gameScreen;
    private final MainClass game;
    private final Skin skin;
    private final Table table;
    private TextArea emWindow;
    private TextArea helpWindow;
    private ScrollPane helpScroll;
    private ArrayList<EmailTable> emailTableList;
    private JsonTest emailReader,systemMessageReader;
    private int stageLevel;

    //private String pwLog;

    public EmailGame(Stage stage, MainClass game,GameScreen screen) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        gameScreen = screen;


        emailReader = new JsonTest("android/assets/data/emails.json");
        systemMessageReader = new JsonTest("android/assets/data/emailInstructions.json");

        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        //inputBox = new TextField("",skin);
        //label = new Label("", skin);

        Table innerTab = new Table(skin);
        Table checkingButtonsTable = new Table(skin);
        table = new Table(skin);
        /*
        Table subTA = new Table(skin);
        Table subTB = new Table(skin);
        Table subTC = new Table(skin);
        TextButton goodEmail = new TextButton("gmail", skin);
        TextButton badEmail = new TextButton("bmail", skin);
        TextButton checkEmail = new TextButton("Check Selected Emails", skin);
        CheckBox gCheck = new CheckBox("", skin);
        CheckBox bCheck = new CheckBox("", skin);
        goodEmail.addListener(eListener);

        TextButton back = new TextButton("Return to MorrisTown", skin);
        back.addListener(backListener);
        table.row();
        table.add(label);
        table.row();
        table.add(instructionsButton);
        table.row();
        subTA.add(gCheck);
        subTA.add(goodEmail).size(320, 30).space(8);
        table.add(subTA);
        table.row();
        subTB.add(bCheck);
        subTB.add(badEmail).size(320, 30).space(8);
        table.add(subTB);
        table.row();
        table.add(checkEmail).size(320, 30).space(8);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth(), Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);
        */

        TextButton checkEmail = new TextButton("Download Selected \nEmail Contents", skin);
        checkEmail.addListener(checkListen);
        TextButton instructionsButton = new TextButton("Instructions", skin);
        instructionsButton.addListener(helpListen);
        TextButton helpButton = new TextButton("Need Help?", skin);
        helpButton.addListener(instructListen);
        emWindow = new TextArea("",skin);
        helpWindow = new TextArea("",skin);

        TextButton back = new TextButton("Return to MorrisTown", skin);

        back.addListener(backListener);
//        innerTab.row();
//        innerTab.add(label);
        emailTableList = new ArrayList<>();
        if(stageLevel<=9) {
            for (int i = 0; i < 5; i++) {

                String emailName = emailReader.getItemField(i,"sender") ;
                TextButton emailButton = new TextButton(emailName, skin);


                CheckBox check = new CheckBox("", skin);
                EmailTable et = new EmailTable(emailButton, check);

                et.setType(emailReader.getItemField(i,"type"));


                emailTableList.add(et);
                et.getEmailButton().addListener(new ButtListener(i));

                et.setSender(emailReader.getItemField(i,"sender"));
                et.setSubject(emailReader.getItemField(i,"subject"));
                et.seteMessage(emailReader.getItemField(i,"body"));

                innerTab.row();
                innerTab.add(et).size(420, 30).space(8);
            }
        }
        helpWindow.setText(systemMessageReader.getItemField("systemMessages","instructions1"));
        String windText = "From: \nSubject: \n\nMessage:\n";
        emWindow.setText(windText);
       /*
        helpWindow.setPrefRows(8);
        helpScroll = new ScrollPane(helpWindow,skin);
        helpScroll.setForceScroll(false, true);
        helpScroll.setFlickScroll(false);
        helpScroll.setOverscroll(false, true);*/
        checkingButtonsTable.row();
        checkingButtonsTable.add(checkEmail).size(175, 40).space(8);
        checkingButtonsTable.row();
        checkingButtonsTable.add(instructionsButton).size(175, 40).space(8);
        checkingButtonsTable.row();
        checkingButtonsTable.add(helpButton).size(175, 40).space(8);
        Table emailContainer = new Table(skin);
        emailContainer.add(checkingButtonsTable).space(8);
        emailContainer.add(innerTab);
        emailContainer.add(emWindow).size(450,500).space(8);
        table.add(emailContainer);
        table.row();
        table.add(helpWindow).size(800, 175).space(8);

        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth(), Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);
    }

    public boolean checkEmails(String type) {
//        ArrayList<EmailTable> tickedEmails = new ArrayList();
//        ArrayList<EmailTable> untickedEmails = emailTableList ;
//        boolean selectedCheck, unselectedCheck;
        for(EmailTable emailTable : emailTableList){
            if(emailTable.getCheckBox().isChecked()){
                if(!emailTable.getType().equals(type)){
                    return false;
                }
            }
        }



        return true;

    }

    private boolean notEnoughChecked( int a)
    {
        int count = 0;
        for(EmailTable emailTable : emailTableList){
            if(emailTable.getCheckBox().isChecked()){
                count++;
            }
        }
        if(count<a){
            return true;
        }
        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        return stage.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return stage.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return stage.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stage.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return stage.mouseMoved(screenX, screenY);

    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        //delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(.5f, .7f, .5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*time += delta;

        if (time < 1f)
            return;*/

        stage.draw();


    }

    @Override
    public void hide() {

    }

    class ButtListener extends InputListener{
        int optionNum;

        public ButtListener(int optionNum){ this.optionNum = optionNum;}

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
           if(!isGameOver) {
               EmailTable tab = emailTableList.get(optionNum);
               String windText = "From: "+tab.getSender()+"\nSubject: "+tab.getSubject()+"\n\nMessage:\n"+tab.geteMessage();
               emWindow.setText(windText);
           }
            return super.touchDown(event, x, y, pointer, button);

        }
    }
}


