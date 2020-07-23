package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Brick extends Rectangle implements Shape {
    public static final float DEFAULT_WIDTH = 64;
    public static final float DEFAULT_HEIGHT = 18;
    public static final float DEFAULT_X_SPACE = 15;
    public static final float DEFAULT_Y_SPACE = 10;
    private int health;

    Brick(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    Brick(float x, float y, int health) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.health = health;
    }

    @Override
    public void draw() {

    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.valueOf("ffffff"));
        shapeRenderer.rect(x, y, width, height);
    }

    public int getHealth() {
        return health;
    }

    public void decrementHealth() {
        this.health--;
    }
}
