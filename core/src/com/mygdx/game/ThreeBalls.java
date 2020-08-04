package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class ThreeBalls extends PowerUp {
    private GameScreen gameScreen;
    public static final String ASSET_NAME = "three_balls";


    ThreeBalls(float x, float y, float width, float height, GameScreen gameScreen) {
        super(x, y, width, height);
        this.gameScreen = gameScreen;
    }

    @Override
    public void activate() {
        gameScreen.addBall(
                new Ball(
                        gameScreen.getPaddle().x + gameScreen.getPaddle().width/2,
                        gameScreen.getPaddle().y + gameScreen.getPaddle().height + 20,
                        Ball.DEF_RADIUS,
                        gameScreen
                )
        );
        gameScreen.addBall(
                new Ball(
                        gameScreen.getPaddle().x + gameScreen.getPaddle().width/2,
                        gameScreen.getPaddle().y + gameScreen.getPaddle().height + 20,
                        Ball.DEF_RADIUS,
                        gameScreen
                )
        );
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Batch batch) {
        TextureRegion textureRegion = gameScreen.getTextureAtlas().findRegion(ASSET_NAME);
        batch.begin();
        batch.draw(textureRegion, x, y, width, height);
        batch.end();
    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer, Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.valueOf("ffffff"));
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }
}
