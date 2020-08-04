package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Shape {

    // draw method draws actual graphics of the object
    void draw(ShapeRenderer shapeRenderer, Batch batch);

    // drawDebug method draws objects with simple shapes for debugging purposes
    void drawDebug(ShapeRenderer shape, Batch batch);
}
