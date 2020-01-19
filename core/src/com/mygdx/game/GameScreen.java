package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private final BreakoutGame breakoutGame;
    private boolean gameBegan = false;

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Texture bg;
    private SpriteBatch batch;
    private Paddle paddle;
    private Ball ball;

    GameScreen(BreakoutGame breakoutGame) { this.breakoutGame = breakoutGame; }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        bg = breakoutGame.getAssetManager().get("background.jpg");
        Sound bounceSound = breakoutGame.getAssetManager().get("bounce.mp3");
        paddle = new Paddle();
        ball = new Ball(100, 100, bounceSound);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        update(delta);
        clearScreen();
        drawDebug();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.end();
    }

    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        ball.drawDebug(shapeRenderer);
        paddle.drawDebug(shapeRenderer);
        shapeRenderer.end();
    }

    private void update(float delta) {
        if (!gameBegan) checkStart();
        paddle.update(delta);
        ball.update(delta);
        stopBallLeavingTheScreen();
        stopPaddleLeavingTheScreen();
        checkPaddleCollision();
    }

    private void checkStart() {
        boolean isTouched = Gdx.input.isTouched();
        boolean isAPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (isTouched || isAPressed) gameBegan = true;
    }

    private void stopBallLeavingTheScreen() {
        if (ball.getPosition().y - ball.getRadius() < 0) {
            System.out.println(ball.getPosition().y - ball.getRadius() + " y");
            if (ball.getDirection().y < 0) ball.getDirection().scl(1, -1);
            ball.playBounceSound();
        }

        if (ball.getPosition().x - ball.getRadius() < 0) {
            System.out.println(ball.getPosition().x - ball.getRadius() + " x");
            if (ball.getDirection().x < 0) ball.getDirection().scl(-1, 1);
            ball.playBounceSound();
        }

        if (ball.getPosition().x + ball.getRadius() > WORLD_WIDTH) {
            System.out.println(ball.getPosition().x + ball.getRadius() + " x");
            if (ball.getDirection().x > 0) ball.getDirection().scl(-1, 1);
            ball.playBounceSound();
        }

        if (ball.getPosition().y + ball.getRadius() > WORLD_HEIGHT) {
            System.out.println(ball.getPosition().y + ball.getRadius() + " y");
            if (ball.getDirection().y > 0) ball.getDirection().scl(1, -1);
            ball.playBounceSound();
        }
    }

    private void stopPaddleLeavingTheScreen() {
        paddle.setPosition(MathUtils.clamp(paddle.getPosition().x, 0, WORLD_WIDTH - paddle.getWidth()));
    }

    private void checkPaddleCollision() {
        if (Intersector.overlaps(ball.circle, paddle.rectangle)) {
            if (paddle.rectangle.y < ball.circle.y) {
                float paddleChunk = paddle.getWidth()/3;
                if (ball.getPosition().x < (paddleChunk  + paddle.getPosition().x)) {
                    ball.setDirection(-1.3f, -1);
                }
                else if (ball.getPosition().x > (paddleChunk  + paddle.getPosition().x) &&
                        ball.getPosition().x < (2*paddleChunk + paddle.getPosition().x)) {
                    ball.setDirection(ball.getDirection().x > 0 ? 1 : -1, -1);
                }
                else if (ball.getPosition().x > (2*paddleChunk  + paddle.getPosition().x) &&
                         ball.getPosition().x < (3*paddleChunk + paddle.getPosition().x)) {
                    ball.setDirection(1.3f, -1);
                }
                ball.getDirection().scl(1, -1);
                ball.playBounceSound();
            }
        }
    }

    private class Ball {
        private final float radius = 15.0f;
        private final float speed = 300;

        private final Sound bounceSound;
        private Vector2 direction = new Vector2(0.707f, 0.707f);
        private Vector2 position = new Vector2();
        Circle circle;

        Ball(float x, float y, Sound bounceSound) {
            this.bounceSound = bounceSound;
            position.set(x, y);
            circle = new Circle(position, radius);
        }

        public Vector2 getDirection() { return direction; }

        public void setDirection(float x, float y) { this.direction.set(x, y); }

        public Vector2 getPosition() { return position; }

        public void setPosition(Vector2 position) { this.position = position; }

        public float getRadius() { return radius; }

        public void drawDebug(ShapeRenderer shapeRenderer) {
            shapeRenderer.setColor(Color.valueOf("00539C"));
            shapeRenderer.circle(position.x, position.y, radius);
        }

        public void update(float delta) {
            if (gameBegan) {
                Vector2 step = new Vector2(direction.x * speed * delta, direction.y * speed * delta);
                position.add(step);
                circle = new Circle(position, radius);
            }
        }

        public void playBounceSound() {
            bounceSound.play();
        }
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

    private class Paddle {
        private static final float height = 20.0f;
        private float width = 90;

        private Vector2 position;
        Rectangle rectangle;

        Paddle() {
            position = new Vector2(0, 40);
            rectangle = new Rectangle(position.x, position.y, width, height);
        }

        public void drawDebug(ShapeRenderer shapeRenderer) {
            shapeRenderer.setColor(Color.valueOf("B1EA8C"));
            shapeRenderer.rect(position.x, position.y, width, height);
        }

        public void update(float delta) {
            Vector2 cursor = new Vector2();
            viewport.unproject(cursor.set(Gdx.input.getX() - width/2, 0));
            cursor.set(cursor.x, position.y);
            position.interpolate(cursor, delta*3, Interpolation.fastSlow);
            rectangle = new Rectangle(position.x, position.y, width, height);
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
    }
}
