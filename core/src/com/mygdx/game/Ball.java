package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Circle implements Shape {
    public static float DEF_RADIUS = 10f;

    // ball will move 300 units per second
    private float velocity = 500;

    // direction of the ball
    private Vector2 directionVector;
    private float directionVectorAngle;

    private GameScreen gameScreen;

    // juice of the ball
    private final Sound bounceSound;

    public Ball(float x, float y, float radius, GameScreen gameScreen) {
        super(x, y, radius);
        directionVector = new Vector2( 0f, 1);
        directionVectorAngle = directionVector.angle();
        this.gameScreen = gameScreen;
        this.bounceSound = gameScreen.bounceSound;
    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer, Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.valueOf("00539C"));
        shapeRenderer.circle(x, y, radius);
        shapeRenderer.end();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Batch batch) {
        TextureRegion textureRegion = gameScreen.getTextureAtlas().findRegion("ball");
        batch.begin();
        batch.draw(
                textureRegion,
                x - radius,
                y - radius,
                radius,
                radius,
                radius * 2,
                radius * 2,
                1,
                1,
                directionVectorAngle,
                true
        );
        batch.end();
    }

    public void move (float delta) {
        setPosition(new Vector2(x, y).mulAdd(directionVector, velocity * delta));
    }

    public void followPaddle(Paddle paddle) {
        setPosition(paddle.x + paddle.width/2, paddle.y + paddle.height + 20);
    }

    public void playBounceSound() {
            bounceSound.play(0.5f, 1.1f, 0);
    }

    // Set direction as a vector
    public void setDirection(Vector2 direction) {
        this.directionVector = direction;
        this.directionVectorAngle = direction.angle();
    }

    // Set direction by angle in degrees
    public void setDirection(float angle) {
        this.directionVectorAngle = angle;
        this.directionVector.setAngle(angle);
    }

    // To make game more interesting, ball will not have horizontal or vertical movements when bouncing
    public float clampAngle(float angle) {
        if(angle > 150f) {
            return 150f;
        }
        if(angle < 30f) {
            return 30f;
        }
        if(angle <= 90f && angle > 75f) {
            return 75f;
        }
        if(angle >= 90f && angle < 105f) {
            return 105f;
        }
        return angle;
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }
>>>>>>> game-rebirth
}
