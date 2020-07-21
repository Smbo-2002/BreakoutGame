package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Brick extends Rectangle implements Shape {
    public static final float DEFAULT_WIDTH = 64;
    public static final float DEFAULT_HEIGHT = 18;
    public static final float DEFAULT_X_SPACE = 15;
    public static final float DEFAULT_Y_SPACE = 10;

    Brick(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    Brick(float x, float y) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
