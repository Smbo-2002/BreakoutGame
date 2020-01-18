package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private final BreakoutGame breakoutGame;

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Texture bg;
    private SpriteBatch batch;

    GameScreen(BreakoutGame breakoutGame) { this.breakoutGame = breakoutGame; }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);
        batch = new SpriteBatch();
        bg = breakoutGame.getAssetManager().get("background.jpg");

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        clearScreen();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.end();
    }

    private class Ball {
        Vector2 position = new Vector2();
        Vector2 velocity = new Vector2();
        float radius = 20.0f;

        Ball(float x, float y) {
            position.set(x, y);
        }
    }

    private class Brick {
        float x;
        float y;
        float width = 58.0f;
        float height = 30.0f;

        Brick(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
