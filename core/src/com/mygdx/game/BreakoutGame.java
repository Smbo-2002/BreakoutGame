package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class BreakoutGame extends Game {
	private final AssetManager assetManager = new AssetManager();

	@Override
	public void create() {
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void dispose() {
		getScreen().dispose();
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
