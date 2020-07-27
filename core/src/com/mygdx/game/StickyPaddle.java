package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class StickyPaddle extends PowerUp {
    private GameScreen gameScreen;

    StickyPaddle(float x, float y, float width, float height, GameScreen gameScreen) {
        super(x, y, width, height);
        this.gameScreen = gameScreen;
    }

    @Override
    public void activate() {
        gameScreen.setPaddleIsSticky(true);
//        gameScreen.getPowerUps().clear();
        for (Ball b : gameScreen.getBalls()) {
            b.setPosition(100, gameScreen.getPaddle().y + gameScreen.getPaddle().getHeight() + 20);
            b.setDirection(new Vector2(0, 1));
        }

    }

    @Override
    public void draw() {

    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.valueOf("ffffff"));
        shapeRenderer.rect(x, y, width, height);
    }
}
