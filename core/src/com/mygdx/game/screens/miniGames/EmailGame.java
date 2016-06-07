package com.mygdx.game.screens.miniGames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.MainClass;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.levels.BedroomLevel;
import com.mygdx.game.screens.DefaultScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.TransitionScreen;
import com.mygdx.game.screens.gui.TouchUpListener;
import com.mygdx.game.util.JsonParser;

import java.util.ArrayList;
import java.util.Random;

public class EmailGame extends DefaultScreen implements InputProcessor {
    private boolean isGameOver;
    private String helpPlayer;
    private int helpCounter;
    private String doneClickMessage;

    private final InputListener checkListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (!isGameOver) {
                int goodNum;
                if (stageLevel <= 9) {
                    goodNum = 1;
                } else {
                    goodNum = 1;
                }
                if (notEnoughChecked(goodNum) == -1) {
                    if (checkEmails("good")) {
                        if (stageLevel <= 9) {
                           doneClickMessage = systemMessageReader.getItemField("systemMessages", "wellDone1");
                        }
                        isGameOver = true;
                        ((BedroomLevel) gameScreen.getLevels().get(0)).setDoorActive(true);
                        ((NPC) gameScreen.getLevels().get(0).getActors().get(1)).setDialogID(3);
                    } else {
                        doneClickMessage = systemMessageReader.getItemField("systemMessages", "badEmails");
                    }
                } else if(notEnoughChecked(goodNum) == 0){
                    doneClickMessage = systemMessageReader.getItemField("systemMessages", "noneChecked");
                } else {
                    doneClickMessage = systemMessageReader.getItemField("systemMessages", "notEnough");
                }
                helpWindow.setText(doneClickMessage);
                //gameScreen.messageScreenSwap("EmailGameMessage",getSelf());
//                for (EmailTable emailTable : emailTableList) {
//                    if (emailTable.getCheckBox().isChecked()) {
//                        emailTable.getCheckBox().toggle();
//                    }
//                }
            }
        }
    };

    private final InputListener saveListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(activeWindow == null){
                doneClickMessage = systemMessageReader.getItemField("systemMessages", "noEmailSelected");
                //gameScreen.messageScreenSwap("EmailGameMessage",getSelf());
                helpWindow.setText(doneClickMessage);
            } else if(!activeWindow.getCheckBox().isChecked()){
                activeWindow.getCheckBox().toggle();
            }
        }
    };

    private final InputListener deleteListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(activeWindow == null){
                doneClickMessage = systemMessageReader.getItemField("systemMessages", "noEmailSelected");
                //gameScreen.messageScreenSwap("EmailGameMessage",getSelf());
                helpWindow.setText(doneClickMessage);
            } else if(activeWindow.getCheckBox().isChecked()){
                activeWindow.getCheckBox().toggle();
            }
        }
    };

    private final InputListener instructListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (!isGameOver) {
                helpWindow.setText(systemMessageReader.getItemField("systemMessages", "instructions1"));
            }
        }
    };

    private final InputListener helpListen = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (!isGameOver) {
                setHelpWindow();
            }
        }
    };

    private void setHelpWindow() {
        if (helpPlayer.equals("")) {
            Random rand = new Random();
            int hintNum = rand.nextInt(3);
            if (hintNum == 0) {
                helpWindow.setText(systemMessageReader.getItemField("hints", "scams"));
            } else if (hintNum == 1) {
                helpWindow.setText(systemMessageReader.getItemField("hints", "bad"));
            } else if (hintNum == 2) {
                helpWindow.setText(systemMessageReader.getItemField("hints", "spam"));
            }
        } else if (helpCounter <= 2) {
            helpWindow.setText(systemMessageReader.getItemField("hints", helpPlayer));
        } else if (helpCounter > 2) {
            Random rand = new Random();
            int hintNum = rand.nextInt(3);
            if (hintNum == 0) {
                helpWindow.setText(systemMessageReader.getItemField("hints", "hint1"));
            } else if (hintNum == 1) {
                helpWindow.setText(systemMessageReader.getItemField("hints", "hint2"));
            } else if (hintNum == 2) {
                helpWindow.setText(systemMessageReader.getItemField("hints", "hint3"));
            }
        }
    }

    private final InputListener backListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            returnToGame();
        }
    };

    private void returnToGame() {
        game.setScreen(gameScreen);
        for (Actor a : stage.getActors()) {
            a.setVisible(false);
        }
        gameScreen.getWorldController().initInput();
        gameScreen.resume();
    }
    public void returnToGameWrap(){
        returnToGame();
    }

    private GameScreen gameScreen;
    private final MainClass game;
    private final Skin skin;
    private final Table table;
    private TextArea emWindow, helpWindow;
    private ScrollPane helpScroll;
    private ArrayList<EmailTable> emailTableList;
    private JsonParser emailReader, systemMessageReader;
    private int stageLevel;
    private int NUMEMAILS = 5;
    private SpriteBatch background;
    private Texture backgroundImage;
    private EmailTable activeWindow;

    public EmailGame(Stage stage, MainClass game, GameScreen screen) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        gameScreen = screen;

        emailReader = new JsonParser("data/emails.json");
        systemMessageReader = new JsonParser("data/emailInstructions.json");

        background = new SpriteBatch();
        backgroundImage = new Texture(Gdx.files.internal("backgrounds/phonebackground.png"));

        skin = new Skin(Gdx.files.internal("ui_skin/uiskin.json"));

        helpPlayer = "";
        helpCounter = 0;
        Table innerTab = new Table(skin);
        Table checkingButtonsTable = new Table(skin);
        table = new Table(skin);

        TextButton checkEmail = new TextButton("Done Checking Emails", skin);
        TextButton save = new TextButton("Save this Email", skin);
        save.addListener(saveListen);
        TextButton delete = new TextButton("Delete this Email", skin);
        delete.addListener(deleteListen);
        checkEmail.addListener(checkListen);
        TextButton instructionsButton = new TextButton("Instructions", skin);
        instructionsButton.addListener(instructListen);
        TextButton helpButton = new TextButton("Hints", skin);
        helpButton.addListener(helpListen);
        emWindow = new TextArea("", skin);
        emWindow.setDisabled(true);
        helpWindow = new TextArea("", skin);
        helpWindow.setDisabled(true);

        TextButton back = new TextButton("Return to Morris Station", skin);

        back.addListener(backListener);
        emailTableList = new ArrayList<>();
        if (stageLevel <= 9) {
            for (int i = 0; i < 3; i++) {

                String emailName = emailReader.getItemField(i, "sender");
                TextButton emailButton = new TextButton(emailName,skin);


                CheckBox check = new CheckBox("", skin);
                EmailTable et = new EmailTable(emailButton, check);

                et.setType(emailReader.getItemField(i, "type"));


                emailTableList.add(et);
                et.getEmailButton().addListener(new ButtListener(i));

                et.setSender(emailReader.getItemField(i, "sender"));
                et.setSubject(emailReader.getItemField(i, "subject"));
                et.seteMessage(emailReader.getItemField(i, "body"));

                innerTab.row();
                innerTab.add(et).size(420, 30).space(8);
            }
        } else if (stageLevel > 9) {
            for (int i = 0; i < 9; i++) {

                String emailName = emailReader.getItemField(i, "sender");
                TextButton emailButton = new TextButton(emailName, skin);


                CheckBox check = new CheckBox("", skin);
                EmailTable et = new EmailTable(emailButton, check);

                et.setType(emailReader.getItemField(i, "type"));


                emailTableList.add(et);
                et.getEmailButton().addListener(new ButtListener(i));

                et.setSender(emailReader.getItemField(i, "sender"));
                et.setSubject(emailReader.getItemField(i, "subject"));
                et.seteMessage(emailReader.getItemField(i, "body"));

                innerTab.row();
                innerTab.add(et).size(420, 30).space(8);
            }
        }
        helpWindow.setText(systemMessageReader.getItemField("systemMessages", "instructions1"));
        String windText = "From: \nSubject: \n\nMessage:\n";
        emWindow.setText(windText);
        Table helpTable = new Table(skin);
        Table helpTableButtons = new Table(skin);
        checkingButtonsTable.row();
        innerTab.row();
        innerTab.add(checkEmail).size(225, 60).space(8);
        //helpTableButtons.add(checkEmail).size(175, 40).space(8);
        //helpTableButtons.row();
        helpTableButtons.add(instructionsButton).size(120, 40).space(8);
        helpTableButtons.row();
        helpTableButtons.add(helpButton).size(120, 40).space(8);
        Table emailContainer = new Table(skin);
        emailContainer.add(checkingButtonsTable).space(8);
        emailContainer.add(innerTab);
        emailContainer.add(emWindow).size(425, 500).space(8);
        Table indEmailButtons = new Table(skin);
        indEmailButtons.add(save);
        indEmailButtons.row();
        indEmailButtons.add(delete).space(8);
        table.add(emailContainer);
        table.add(indEmailButtons);
        table.row();
        helpTable.add(helpTableButtons);
        helpTable.add(helpWindow).size(750, 175).space(8);
        table.add(helpTable).space(8);

        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth(), Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);
    }

    public boolean checkEmails(String type) {
        for (EmailTable emailTable : emailTableList) {
            if (emailTable.getCheckBox().isChecked()) {
                if (!emailTable.getType().equals(type)) {
                    helpPlayer = emailTable.getType();
                    return false;
                }
            }
        }
        return true;
    }

    private int notEnoughChecked(int a) {
        int count = 0;
        for (EmailTable emailTable : emailTableList) {
            if (emailTable.getCheckBox().isChecked()) {
                count++;
            }
        }
        if (count < a && count!= 0) {
            return a-count;
        }
        else if(count == 0){
            return 0;
        }
        else{
            return -1;
        }
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
        background.begin();
        background.draw(backgroundImage, 0, 0, 1100, 800);
        background.end();
        stage.draw();
    }

    @Override
    public void hide() {
    }

    class ButtListener extends InputListener {
        int optionNum;

        public ButtListener(int optionNum) {
            this.optionNum = optionNum;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (!isGameOver) {
                EmailTable tab = emailTableList.get(optionNum);
                String windText = "From: " + tab.getSender() + "\nSubject: " + tab.getSubject() + "\n\nMessage:\n" + tab.geteMessage();
                emWindow.setText(windText);
                activeWindow = tab;
            }
            return super.touchDown(event, x, y, pointer, button);
        }
    }

    public boolean getIsGameOver(){
        return isGameOver;
    }

    public String getDoneClickMessage(){
        return doneClickMessage;
    }

    public EmailGame getSelf(){
        return this;
    }

}