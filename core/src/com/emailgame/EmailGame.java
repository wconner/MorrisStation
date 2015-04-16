package com.emailgame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmailGame extends Game{
	SpriteBatch batch;
	BitmapFont bmp;
	//private GameStateManager gsm;

	@Override
	public void create () {
		batch = new SpriteBatch();
		bmp = new BitmapFont();
		//gsm = new GameStateManager();

	}

	@Override
	public void render () {

		batch.begin();
		Gdx.gl.glClearColor(164f, 155f, 155f, 10f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyPressed(Input.Keys.P)){
			//gsm.setState(gsm.PHONE);
		}

		//gsm.update(Gdx.graphics.getDeltaTime());
		//gsm.draw();
		bmp.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		batch.end();
	}
}
