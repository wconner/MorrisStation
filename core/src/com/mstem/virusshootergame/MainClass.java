package com.mstem.virusshootergame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mstem.virusshootergame.Screen.ShooterScreen;

//import com.mygdx.game.screens.TestScreen;

public class MainClass extends Game {


    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);


        //Assets.load();
        setScreen(new ShooterScreen(new Stage(),this));

    }

    public void render() {
        super.render();
    }

    public void dispose() {

        super.dispose();
        Gdx.app.exit();
    }

    public void resize(int width, int height) {
    }

    public void pause() {
    }

    public void resume() {
    }
}