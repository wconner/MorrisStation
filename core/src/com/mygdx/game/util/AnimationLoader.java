package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;

/**
 * Created by Jshen94 on 11/5/2015.
 */
public class AnimationLoader implements ApplicationListener{
    private Animation animation;
    private Texture sheet;
    private TextureRegion[] frames;
    private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;

    float stateTime;

    public static void main(String[] args) {
        AnimationLoader test = new AnimationLoader();
        test.create();
        test.render();
    }
    @Override
    public void create() {

        // this line doesnt work, need to figure out how to grab the file from assets folder
        sheet = new Texture(Gdx.files.internal("android/assets/biff.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet,sheet.getWidth()/42, sheet.getHeight()/12);
        frames = new TextureRegion[3];
        int index = 0;
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 3; j++)
                frames[index++] = tmp[i][j];
        }
        animation = new Animation(0.025f, frames);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(16384| 246);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 50, 50);
        spriteBatch.end();


    }

    @Override
    public void resize(int x, int y){

    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }


}
