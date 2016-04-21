package com.testoverlay;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainClass;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.gui.TouchUpListener;

/**
 * Created by Justin Shen on 4/14/2016.
 */
public class TransitionScreen extends com.mygdx.game.screens.DefaultScreen implements InputProcessor {

    private String type;
    private GameScreen gameScreen;
    private String dif;

    private final Skin skin;
    private final Table table;
    private Label infoLabel;
    private TextButton play;


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
                default:
                    game.setScreen(new IntroScreen(stage, game));
            }
        }
    };


    private final InputListener easyListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            dif = "easy";

        }
    };
    private final InputListener medListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            dif = "med";

        }
    };
    private final InputListener hardListener = new TouchUpListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            dif = "hard";

        }
    };
    public TransitionScreen(Stage stage, MainClass game, GameScreen screen, String type){
        super(stage, game);
        Gdx.input.setInputProcessor(this);
        gameScreen = screen;
        this.type = type;
        skin = new Skin(Gdx.files.internal("android/assets/ui_skin/uiskin.json"));
        table = new Table(skin);
        infoLabel = new Label(type,skin);
        dif = "easy";

        initUI(type);
        }


    private void initUI(String type){
        if(type == "Mastermind") {
            infoLabel.setText("In this game you must continuously guess different numbers \nuntill they are all locked in");
            Table diffTable = new Table(skin);
            Table buttonTable = new Table(skin);
            TextButton easy = new TextButton("Easy", skin);
            TextButton med = new TextButton("Medium", skin);
            TextButton hard = new TextButton("Hard", skin);
            play = new TextButton("Play", skin);
            play.addListener(playListener);

            easy.addListener(easyListener);
            med.addListener(medListener);
            hard.addListener(hardListener);

            table.add(diffTable);
            table.pad(0f, 15f, 0f, 15f);
            diffTable.pad(15f, 15f, 15f, 15f);
            diffTable.add(new Label("Crack the Password!", skin));
            diffTable.row();
            diffTable.add(infoLabel);
            diffTable.row();
            diffTable.row();
            diffTable.center();
            diffTable.add(play);


            table.add(buttonTable);
            buttonTable.pad(10f, 10f, 10f, 10f);
            buttonTable.row();
            buttonTable.add(easy);
            buttonTable.row();
            buttonTable.add(med);
            buttonTable.row();
            buttonTable.add(hard);

            table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            stage.addActor(table);
        }


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
}
