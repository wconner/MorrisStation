package com.emailgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Tom on 4/13/15.
 */
public class EmailScreen extends DefaultScreen implements InputProcessor{


    public EmailScreen(Stage stage, Game game){
        //transfer game
        super(stage, game);
    }




    @Override
    public boolean keyDown(int keycode){
        return false;
    }

    @Override
    public boolean keyUp(int keycode){
        return false;
    }

    @Override
    public boolean keyTyped(char character){
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY){
        return false;
    }

    @Override
    public boolean scrolled(int amount){
        return false;
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
    //this is now update

    }

    @Override
    public void hide(){

    }
}
