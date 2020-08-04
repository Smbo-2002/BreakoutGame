package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.mygdx.game.BreakoutGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Breakout Game";
		config.width = 640;
		config.height = 640;
		config.x = 0;
		config.y = 0;
		TexturePacker.process("../assets", "../assets", "breakout_assets");
		new LwjglApplication(new BreakoutGame(), config);
	}
}
