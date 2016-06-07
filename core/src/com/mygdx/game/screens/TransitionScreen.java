package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainClass;
import com.mygdx.game.screens.gui.TouchUpListener;
import com.mygdx.game.screens.miniGames.EmailGame;
import com.mygdx.game.screens.miniGames.MastermindGame;
import com.mygdx.game.screens.miniGames.PasswordGame;
import com.mygdx.game.util.JsonParser;

import java.util.ArrayList;

/**
 * Created by Justin Shen on 4/14/2016.
 */
public class TransitionScreen extends com.mygdx.game.screens.DefaultScreen implements InputProcessor {

    private String type;
    private GameScreen gameScreen;
    private String dif;

    private final Skin skin;
    private final Table table;
    private Label infoLabel, diffLabel;
    private TextButton play;
    private EmailGame activeEGame;

    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            table.setVisible(false);
            switch (type) {
                case "Password":
                    game.setScreen(new PasswordGame(stage, game, gameScreen));
                    break;
                case "Mastermind":
                    game.setScreen(new MastermindGame(stage, game, gameScreen, dif));
                    break;
                case "EmailGame":
                    game.setScreen(new EmailGame(stage, game, gameScreen));
                    break;
                default:
                    game.setScreen(new IntroScreen(stage, game));
            }
        }
    };

    private final InputListener eGameMessageListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(activeEGame.getIsGameOver()){
                activeEGame.returnToGameWrap();
            }
            else
            {
                game.setScreen(activeEGame);
            }
        }
    };

    private final InputListener easyListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            dif = "easy";
            diffLabel.setText("Difficulty: Easy \nYou will only have to enter numbers");
        }
    };
    private final InputListener medListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            dif = "med";
            diffLabel.setText("Difficulty: Medium \nYou will only have to enter lowercase letters");

        }
    };
    private final InputListener hardListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            dif = "hard";
            diffLabel.setText("Difficulty: Hard \nYou will have to enter both numbers and lowercase letters");

        }
    };

    public TransitionScreen(Stage stage, MainClass game, GameScreen screen, String type) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        gameScreen = screen;
        this.type = type;
        skin = new Skin(Gdx.files.internal("ui_skin/uiskin.json"));
        table = new Table(skin);
        infoLabel = new Label(type, skin);
        diffLabel = new Label("", skin);
        dif = "easy";

        initUI(type);
    }

    private void initUI(String type) {
        play = new TextButton("Play", skin);
        play.addListener(playListener);
        if (type.equals("Mastermind")) {
            infoLabel.setText("In this game you must continuously guess different numbers \nuntil they are all locked in");
            infoLabel.setFontScale(1.1f);
            diffLabel.setText("Difficulty: Easy \nYou will only have to enter numbers");
            Table diffTable = new Table(skin);
            Table buttonTable = new Table(skin);
            TextButton easy = new TextButton("Easy", skin);
            TextButton med = new TextButton("Medium", skin);
            TextButton hard = new TextButton("Hard", skin);

            easy.addListener(easyListener);
            med.addListener(medListener);
            hard.addListener(hardListener);

            table.add(diffTable);
            diffTable.add(new Label("Crack the Password!", skin)).padBottom(10f);
            diffTable.row();
            diffTable.add(infoLabel).padBottom(20f);
            diffTable.row();
            diffTable.center();
            diffTable.add(play).size(360, 50).space(8f).padTop(30f);

            table.add(buttonTable);
            buttonTable.add(diffLabel);
            buttonTable.row();
            buttonTable.add(easy).size(70, 40).space(8f).padTop(10f).padLeft(15f);
            buttonTable.row();
            buttonTable.add(med).size(70, 40).space(8f).padLeft(15f);
            buttonTable.row();
            buttonTable.add(hard).size(70, 40).space(8f).padLeft(15f);

        } else if (type.equals("Password")) {
            infoLabel = new Label("In this game you will have to create a password that is secure\nSecure passwords are hard to crack and " +
                    "are a crucial part of keeping your information safe", skin);
            infoLabel.setFontScale(1.1f);
            table.add(infoLabel).padBottom(20f);
            table.row();
            table.add(play).size(360, 50).space(8f).padTop(30f);

        }
        else if (type.equals("EmailGame")) {
            JsonParser instructReader = new JsonParser("data/emailInstructions.json");
            infoLabel = new Label(instructReader.getItemField("systemMessages", "instructions2"), skin);
            infoLabel.setFontScale(1.1f);
            table.add(infoLabel).padBottom(20f);
            table.row();
            table.add(play).size(360, 50).space(8f).padTop(30f);

        }
        else if (type.equals("EmailGameMessage")) {
            ArrayList<String> labels = determineEGameMessage();
            String labelMessage = labels.get(0);
            infoLabel = new Label(labelMessage, skin);
            infoLabel.setFontScale(1.1f);
            table.add(infoLabel).padBottom(20f);
            table.row();
            String stuff = labels.get(1);
            TextButton cont = new TextButton(stuff,skin);
            cont.addListener(eGameMessageListener);
            table.add(cont).size(360, 50).space(8f).padTop(30f);

        }
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
    }

    private ArrayList<String> determineEGameMessage(){
        ArrayList<String> labels = new ArrayList<String>();
        if(activeEGame == null){
            labels.add("");
            labels.add("");
            return labels;
        }
        labels.add(activeEGame.getDoneClickMessage());

        if(activeEGame.getIsGameOver()){
            labels.add("Return to MorrisTown");
        }
        else
        {
            labels.add("Return to Inbox");
        }
        return labels;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, .3f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void hide() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
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

    public void setActiveEGame(EmailGame g){
        activeEGame = g;
        initUI(type);
    }
}