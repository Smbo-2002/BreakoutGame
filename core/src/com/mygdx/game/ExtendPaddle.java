package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

public class ExtendPaddle extends PowerUp {
    private GameScreen gameScreen;

    ExtendPaddle(float x, float y, float width, float height, GameScreen gameScreen) {
        super(x, y, width, height);
        this.gameScreen = gameScreen;
    }

    @Override
    public void activate() {
        if (!gameScreen.isPaddleExtended())
            gameScreen.getPaddle().setWidth(gameScreen.getPaddle().getWidth()*2);
        else {
            gameScreen.setScore(gameScreen.getScore() + POWER_UP_BONUS);
            gameScreen.getExtendedPaddleTimer().clear();
        }
        gameScreen.setPaddleIsExtended(true);
        gameScreen.getExtendedPaddleTimer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                gameScreen.setPaddleIsExtended(false);
                gameScreen.getPaddle().setWidth(gameScreen.getPaddle().getWidth()/2);
            }
        }, 7);
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
