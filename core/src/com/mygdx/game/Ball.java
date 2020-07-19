package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Circle implements Shape {
    // ball will move 300 units per second
    private float velocity = 300;

    // direction of the ball
    private Vector2 directionVector;
    private float directionVectorAngle;

    // juice of the ball
    private final Sound bounceSound;

    public Ball(float x, float y, float radius, Sound bounceSound) {
        super(x, y, radius);
        directionVector = new Vector2( 1, 1);
        directionVectorAngle = directionVector.angle();
        this.bounceSound = bounceSound;
    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.valueOf("00539C"));
        shapeRenderer.circle(x, y, radius);
    }

    @Override
    public void draw (){

    }

    public void move (float delta) {
        setPosition(new Vector2(x, y).mulAdd(directionVector, velocity * delta));
    }

    public void playBounceSound() {
            bounceSound.play();
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }
}
