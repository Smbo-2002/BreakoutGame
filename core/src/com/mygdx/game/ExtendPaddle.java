package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

public class ExtendPaddle extends PowerUp {
    private GameScreen gameScreen;
    public static final String ASSET_NAME = "extended_paddle";


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
        }
        gameScreen.setPaddleIsExtended(true);
        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                gameScreen.setPaddleIsExtended(false);
                gameScreen.getPaddle().setWidth(gameScreen.getPaddle().DEF_WIDTH);
            }
        }, 7);
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
