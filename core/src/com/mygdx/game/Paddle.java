package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Paddle extends Rectangle implements  Shape{
    public final static float DEF_WIDTH = 96;
    public final static float DEF_HEIGHT = 14;

    // how many units/second can paddle move
    float velocity = 800f;
    private GameScreen gameScreen;

    public Paddle(float x, float y, float width, float height, GameScreen gameScreen) {
        super(x, y, width, height);
        this.gameScreen = gameScreen;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Batch batch) {
        TextureRegion textureRegion = gameScreen.getTextureAtlas().findRegion("paddle");
        batch.begin();
        batch.draw(textureRegion, x, y, width, height);
        batch.end();
    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer, Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.valueOf("B1EA8C"));
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    // method for paddle to follow cursor
    public void follow(float delta, Vector3 cursor) {
        // fix of issue when paddle wobbles, because of floats
        if (Math.abs(x + width / 2f - cursor.x) < velocity * delta)
            return;

        if(x + width / 2f > cursor.x)
            moveLeft(delta);
        else if(x + width / 2f < cursor.x)
            moveRight(delta);
    }

    // methods for paddle to follow arrow keys
    public void moveLeft(float delta) {
        setX(x - velocity*delta);
    }
    public void moveRight(float delta) {
        setX(x + velocity*delta);
    }

}
