package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Paddle extends Rectangle implements  Shape{
    // how many units/second can paddle move
    float velocity = 768f;

    public Paddle(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void draw() {

    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.valueOf("B1EA8C"));
        shapeRenderer.rect(x, y, width, height);
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
