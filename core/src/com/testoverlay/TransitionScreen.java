package com.testoverlay;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainClass;
import com.mygdx.game.screens.GameScreen;

/**
 * Created by Justin Shen on 4/14/2016.
 */
public class TransitionScreen extends DefaultScreen {
    public TransitionScreen(Stage stage, MainClass game, GameScreen screen, String type){
        super(stage, game);





        String def = "int";
        switch (type) {
            case "Password":
                game.setScreen(new PasswordGame(stage, game, screen));
                break;
            case "Mastermind":
                game.setScreen(new MastermindGame(stage,game,screen,def));
                break;
            default:
                game.setScreen(new IntroScreen(stage, game));

        }

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void hide() {

    }
}
