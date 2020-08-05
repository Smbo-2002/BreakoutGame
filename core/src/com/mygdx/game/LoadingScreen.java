package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen extends ScreenAdapter {
    // Defining loading screen properties
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private static final float PROGRESS_BAR_WIDTH = 100;
    private static final float PROGRESS_BAR_HEIGHT = 25;
    private float progress = 0;

    // Defining properties for graphical part
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera camera;
    private final BreakoutGame breakoutGame;

    public LoadingScreen(BreakoutGame breakoutGame) {
        this.breakoutGame = breakoutGame;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();

        // Creating camera and setting it on the center of the world
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);

        shapeRenderer = new ShapeRenderer();

        // Loading the assets
        breakoutGame.getAssetManager().load("breakout_assets.atlas", TextureAtlas.class);
        BitmapFontLoader.BitmapFontParameter bitmapFontParameter = new BitmapFontLoader.BitmapFontParameter();
        bitmapFontParameter.atlasName = "breakout_assets.atlas";
        breakoutGame.getAssetManager().load("font18.fnt", BitmapFont.class, bitmapFontParameter);
        breakoutGame.getAssetManager().load("font32.fnt", BitmapFont.class, bitmapFontParameter);
        breakoutGame.getAssetManager().load("font64.fnt", BitmapFont.class, bitmapFontParameter);
        breakoutGame.getAssetManager().load("bounce.mp3", Sound.class);
        breakoutGame.getAssetManager().load("crack.mp3", Sound.class);
        breakoutGame.getAssetManager().load("Whimsical-Popsicle.mp3", Music.class);

    }

    @Override
    public void render(float delta) {
        update();
        clearScreen();
        draw();
    }

    @Override
    public void dispose() { shapeRenderer.dispose(); }

    private void update() {
        // Check if the resources are loaded proceed to game screen
        if (breakoutGame.getAssetManager().update()) {
            breakoutGame.setScreen(new GameScreen(breakoutGame));
        } else {
            progress = breakoutGame.getAssetManager().getProgress();
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        // drawing the progress
        shapeRenderer.rect((
                WORLD_WIDTH  - PROGRESS_BAR_WIDTH) / 2,
                WORLD_HEIGHT / 2 - PROGRESS_BAR_HEIGHT / 2,
                progress * PROGRESS_BAR_WIDTH,
                PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();
    }
}
