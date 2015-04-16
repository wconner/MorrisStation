package com.mstem.virusshootergame.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by catherinehuang on 4/13/15.
 */
public abstract class DefaultScreen implements Screen {
    protected Game game;
    protected Stage stage;

    public DefaultScreen(Stage stage,Game game) {
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
//		dispose();
    }

    @Override
    public void resume() {

//		Resources.getInstance().reInit();
    }

    @Override
    public void dispose() {
        stage.dispose();
        game.dispose();
//		Resources.getInstance().dispose();
    }

    public void render(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
