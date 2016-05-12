
package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screens.IntroScreen;


public class MainClass extends Game {
	private Game game;

	public MainClass() {
		game = this;
	}

	public MainClass(Game game) {
		this.game = game;
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new IntroScreen(new Stage(), this));
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