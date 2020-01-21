package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;


// Ball class
public class Ball {
    public static final float RADIUS = 15.0f; // Radius of the ball
    public static final float SPEED = 500; // In one second ball can move 300 units

    private final Sound bounceSound;
    private Vector2 direction = new Vector2(0.707f, 0.707f);
    private Vector2 position = new Vector2();
    private GameScreen gameScreen;
    private Circle circle; // Game will use this object to check for collisions

    Ball(float x, float y, Sound bounceSound, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.bounceSound = bounceSound;
        position.set(x, y);
        circle = new Circle(position, RADIUS);
    }


    // Draws ball on the screen
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.valueOf("00539C"));
        shapeRenderer.circle(position.x, position.y, RADIUS);
    }

    public void update(float delta) {
        // Moves ball with paddle until the game start, then ball moves by its velocity
        if (gameScreen.getGameBegan()) {
            Vector2 step = direction.cpy().scl(SPEED * delta);
            position.add(step);
            circle = new Circle(position, RADIUS);
        } else {
            this.position.set(gameScreen.paddle.getPosition().x + gameScreen.paddle.getWidth() /2,
                    gameScreen.paddle.getPosition().y + gameScreen.paddle.HEIGHT + RADIUS + 10);
        }
    }


    public void playBounceSound() {
        bounceSound.play();
    }

    public Vector2 getDirection() { return direction; }

    public void setDirection(float x, float y) { this.direction.set(x, y); }

    public Vector2 getPosition() { return position; }

    public void setPosition(Vector2 position) { this.position.set(position); }

    public void setPosition(float x, float y) { this.position.set(x, y); }

    public float getRadius() { return RADIUS; }

    public Circle getCircle() { return circle; }
}
