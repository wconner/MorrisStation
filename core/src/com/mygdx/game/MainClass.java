package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.mygdx.game.screens.MainMenu;

public class MainClass extends Game {
	@Override
	public void create() {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		//Assets.load();
		setScreen(new MainMenu(this));

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