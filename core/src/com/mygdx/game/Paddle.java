package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Paddle {
    public static final float HEIGHT = 20.0f;
    public static final float INITIAL_WIDTH = 90.0f;
    private float width = INITIAL_WIDTH; // Width will be changed in the future

    private Rectangle rectangle; // // Game will use this to check for collision, with ball
    private Vector2 position;
    public Vector2 cursor; // Cursor position
    private GameScreen gameScreen;

    Paddle(float x, float y, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        position = new Vector2(x, y);
        cursor = new Vector2(x, y);
        rectangle = new Rectangle(position.x, position.y, width, HEIGHT);
    }


    // Draws paddle on the screen
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.valueOf("B1EA8C"));
        shapeRenderer.rect(position.x, position.y, width, HEIGHT);
    }

    public void update(float delta) {
        position.interpolate(cursor, 0.2f, Interpolation.smooth);
        rectangle.set(position.x, position.y, width, HEIGHT);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(float x) {
        this.position.set(x, position.y);
    }

    public float getWidth() {
        return this.width;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

}
