package com.mygdx.game.screens.miniGames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.MainClass;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.TouchUpListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Justin Shen on 2/22/2016.
 * Contains the mastermind game, which is a hacking minigame showcasing brute force hacking in particular
 */
public class MastermindGame extends com.mygdx.game.screens.DefaultScreen implements InputProcessor {
    private static final String TAG = MastermindGame.class.getName();
    private static int NUM_OF_DIGITS = 5;
    private Label label;
    private final InputListener playListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            password = "";
            for (int i = 0; i < pw.length(); i++) {
                if (!(inputs.get(i).getText().equals("")) && (!(inputs.get(i).getText().equals(" ")))) {
                    password += inputs.get(i).getText();
                    if (pw.charAt(i) == password.charAt(i)) {
                        inputs.get(i).setDisabled(true);
                        inputs.get(i).setColor(Color.CHARTREUSE);
                    } else {
                        inputs.get(i).setColor(Color.RED);
                    }

                    if (password.equals(pw)) {
                        label.setText("Nice job! You cracked the password! \nThe password is: " + password);
                        play.setText("Return to MorrisTown");
                        play.addListener(backListener);
                        status = true;
                    } else {
                        label.setText("The correct entries have been locked in, please fix the remaining entries");
                    }
                } else {
                    inputs.get(i).setColor(Color.GOLDENROD);
                    label.setText("Please fill in every entry");
                }
            }
        }
    };

    private final InputListener backListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (status) {
                ((NPC) gameScreen.getLevels().get(1).getActors().get(3)).setDialogID(3);
                ((NPC) gameScreen.getLevels().get(1).getActors().get(1)).setDialogID(3);
            }
            //gameScreen.getWorldController().getDialog().hide();
            game.setScreen(gameScreen);
            for (Actor a : stage.getActors()) {
                a.setVisible(false);
            }
            gameScreen.getWorldController().initInput();
            gameScreen.resume();
        }
    };

    private boolean status;
    private GameScreen gameScreen;
    private final MainClass game;
    private final Skin skin;
    private final Table table, buttonTable;
    private final String pw;
    private TextButton play;
    private String password;
    private ArrayList<TextField> inputs;

    public MastermindGame(Stage stage, MainClass game, GameScreen screen, String pass) {
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        this.game = game;
        gameScreen = screen;
        status = false;

        skin = new Skin(Gdx.files.internal("ui_skin/uiskin.json"));
        label = new Label("                                -(Brute Force Hacking)-" +
                "\n                =================================" +
                "\n Enter numbers until all the numbers have been locked in", skin);
        table = new Table(skin);
        buttonTable = new Table(skin);

        inputs = new ArrayList<>();

        play = new TextButton("Test Password!", skin);
        play.addListener(playListener);

        TextButton back = new TextButton("Return to Morris Station", skin);
        back.addListener(backListener);
        table.add(label);
        table.row();

        pw = pwGen(pass); /** Sets the number of digits and generates a random password based on the difficulty */

        for (int i = 0; i < NUM_OF_DIGITS; i++) { /**Generate all the input text fields and place them into the subtable */
            inputs.add(new TextField("", skin));
            inputs.get(i).setMaxLength(1);
            inputs.get(i).setAlignment(1);
            inputs.get(i).pack();
            buttonTable.add(inputs.get(i)).padRight(5f).padLeft(5f).width(50f);
        }
        table.add(buttonTable);
        table.row();
        table.add(play).size(320, 64).space(8);
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth(), Gdx.graphics.getHeight() - back.getHeight());
        stage.addActor(back);
    }

    private static String pwGen(String s) {
        String pw = "";
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Random rand = new Random();

        switch (s) {
            case "easy":
                NUM_OF_DIGITS = 3;
                for (int i = 0; i < NUM_OF_DIGITS; i++) {
                    pw += rand.nextInt(10);
                }
                break;
            case "med":
                NUM_OF_DIGITS = 3;
                for (int i = 0; i < NUM_OF_DIGITS; i++) {
                    pw += alphabet[rand.nextInt(26)];
                }
                break;
            case "hard":
                NUM_OF_DIGITS = 5;
                for (int i = 0; i < NUM_OF_DIGITS; i++) {
                    if (rand.nextBoolean()) {
                        pw += rand.nextInt(10);
                    } else {
                        pw += alphabet[rand.nextInt(26)];
                    }
                }
            default:
                pw += "";
        }
        System.out.println(pw);
        return pw;
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
        Gdx.gl.glClearColor(.1f, .1f, .1f, .3f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void hide() {
    }
}