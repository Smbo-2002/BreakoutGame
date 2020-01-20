package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Ball {
    public static final float RADIUS = 15.0f;
    public static final float SPEED = 300;

    private final Sound bounceSound;
    private Vector2 direction = new Vector2(0.707f, 0.707f);
    private Vector2 position = new Vector2();
    private GameScreen gameScreen;
    private Circle circle;

    Ball(float x, float y, Sound bounceSound, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.bounceSound = bounceSound;
        position.set(x, y);
        circle = new Circle(position, RADIUS);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.valueOf("00539C"));
        shapeRenderer.circle(position.x, position.y, RADIUS);
    }

    public void update(float delta) {
        if (gameScreen.getGameBegan()) {

            Vector2 step = direction.cpy().scl(SPEED * delta);
            position.add(step);
            circle = new Circle(position, RADIUS);
        }
    }

    public void playBounceSound() {
        bounceSound.play();
    }

    public Vector2 getDirection() { return direction; }

    public void setDirection(float x, float y) { this.direction.set(x, y); }

    public Vector2 getPosition() { return position; }

    public void setPosition(Vector2 position) { this.position = position; }

    public float getRadius() { return RADIUS; }

    public Circle getCircle() { return circle; }
}
