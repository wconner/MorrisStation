package com.emailgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screens.DefaultScreen;

public class MainClass extends Game {
    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Assets.load();
        setScreen(new PhoneState(new Stage(), this));

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