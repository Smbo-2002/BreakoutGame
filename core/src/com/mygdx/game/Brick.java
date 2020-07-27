package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;

import java.util.Objects;

public class Brick extends Rectangle implements Shape {
    public static final float DEFAULT_WIDTH = 64;
    public static final float DEFAULT_HEIGHT = 18;
    public static final float DEFAULT_X_SPACE = 15;
    public static final float DEFAULT_Y_SPACE = 10;
    private int health;
    private PowerUp powerUp;

    Brick(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    Brick(float x, float y, int health) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.health = health;
    }

    Brick(float x, float y, int health, PowerUp powerUp) {
        this(x, y, health);
        this.powerUp = powerUp;
    }

    @Override
    public void draw() {

    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer, Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.valueOf("ffffff"));
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    public int getHealth() {
        return health;
    }

    public void decrementHealth() {
        this.health--;
    }

    public boolean hasPowerUp() {
        return !Objects.isNull(powerUp);
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
}
