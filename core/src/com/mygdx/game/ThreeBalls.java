package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class ThreeBalls extends PowerUp {
    private GameScreen gameScreen;

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
                        gameScreen.bounceSound
                )
        );
        gameScreen.addBall(
                new Ball(
                        gameScreen.getPaddle().x + gameScreen.getPaddle().width/2,
                        gameScreen.getPaddle().y + gameScreen.getPaddle().height + 20,
                        Ball.DEF_RADIUS,
                        gameScreen.bounceSound
                )
        );
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
