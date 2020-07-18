package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;

public class BreakoutGame extends Game {
	private final AssetManager assetManager = new AssetManager();
	private ScreenAdapter currentScreen;

	@Override
	public void create() {
		setScene(new LoadingScreen(this));
	}

	public void setScene (ScreenAdapter screen) {
		currentScreen = screen;
		setScreen(currentScreen);
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
