package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 640;
    private static final float WORLD_HEIGHT = 640;
    private final BreakoutGame breakoutGame;
    private boolean gameBegan = false;

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera camera;
//    private Texture bg;
    private SpriteBatch batch;

    private Ball ball;
    private Paddle paddle;

    GameScreen(BreakoutGame breakoutGame) { this.breakoutGame = breakoutGame; }

    @Override
    public void show() {
        camera = new OrthographicCamera();

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
//        bg = breakoutGame.getAssetManager().get("background.jpg");
        Sound bounceSound = breakoutGame.getAssetManager().get("bounce.mp3");
        paddle = new Paddle(WORLD_WIDTH/2 - 96/2, 50, 96, 13);
        ball = new Ball(100, paddle.y + paddle.height + 20, 10, bounceSound);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        drawDebug();
        update(delta);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        batch.draw(bg, 0, 0);
//        batch.end();
    }

    private void drawDebug() {
        batch.setProjectionMatrix(camera.combined);

        // Draw the background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.valueOf("EEA47D"));
        shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        // Draw other objects
        ball.drawDebug(shapeRenderer);
        paddle.drawDebug(shapeRenderer);
        shapeRenderer.end();
    }

    private void update(float delta) {
        paddle.follow(delta, getCursorPosition());
        if (!gameBegan) {
            checkStart();
            ball.followPaddle(paddle);
        }
        else if (gameBegan) {
            ball.move(delta);
            stopBallLeavingTheScreen();
            stopPaddleLeavingTheScreen();
            checkPaddleCollision();
        }
    }

    private void checkStart() {
        boolean isTouched = Gdx.input.isTouched();
        boolean isAPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (isTouched || isAPressed) gameBegan = true;
    }

    private void stopBallLeavingTheScreen() {
        if((ball.getDirectionVector().x < 0f && ball.x - ball.radius < 0) ||
                (ball.getDirectionVector().x > 0f && ball.x + ball.radius > WORLD_WIDTH)) {
            ball.setDirection(ball.getDirectionVector().scl(-1,1));
            ball.playBounceSound();
        }

        if(ball.getDirectionVector().y > 0f && ball.y + ball.radius > WORLD_HEIGHT + 0.1f)
        {
            ball.setDirection(ball.getDirectionVector().scl(1,-1));
            ball.playBounceSound();
        }
    }

    private void stopPaddleLeavingTheScreen() {
        paddle.setX(MathUtils.clamp(paddle.x, 0, WORLD_WIDTH - paddle.getWidth()));
    }

    private void checkPaddleCollision() {
        Vector2 distance = new Vector2(ball.x, ball.y).sub(paddle.x, paddle.y);
        Vector2 scaleFactor = new Vector2(1/ paddle.width, 1/(paddle.height/paddle.width));

        distance.scl(scaleFactor);

        if (Intersector.overlaps(ball, paddle)) {
            if (Math.abs(distance.x) >= Math.abs(distance.y)) {
                // scaled delta x was larger than delta y. This is a horizontal hit.
                ball.setDirection(ball.getDirectionVector().scl(-1, 1));
                ball.setX(
                        ((ball.x - (paddle.x + paddle.width/2)) >= 0) ?
                        ball.x + ((paddle.x + paddle.width) - (ball.x - ball.radius)) :
                        ball.x - ((ball.x + ball.radius) - paddle.x)
                );
            } else {
                // This is a vertical hit.
                // max horizontal distance between ball center and paddle center, when colliding
                float newAngle = 180 - ((ball.x - paddle.x) * 180 ) / paddle.width;
                ball.setDirection(ball.clampAngle(newAngle));
            }
        }

    }

    public Vector3 getCursorPosition () {
        Vector3 cursor = new Vector3();
        cursor.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(cursor);
        return cursor;
    }

    private class Brick {
        private final float width = 58.0f;
        private final float height = 30.0f;

        private float x;
        private float y;

        Brick(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

}
