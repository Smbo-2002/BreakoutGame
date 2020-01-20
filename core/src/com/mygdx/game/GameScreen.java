package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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

public class GameScreen extends ScreenAdapter  implements InputProcessor {

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

        paddle = new Paddle( WORLD_WIDTH/2 - Paddle.INITIAL_WIDTH / 2, 40, this);
        ball = new Ball(100, 100, bounceSound, this);

        Gdx.input.setInputProcessor(this);
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
        if (Intersector.overlaps(ball.getCircle(), paddle.getRectangle())) {
            if (paddle.getRectangle().y < ball.getCircle().y) {
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

    public Viewport getViewport() {
        return viewport;
    }

    public boolean getGameBegan() {
        return gameBegan;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        paddle.cursor.set(screenX, screenY);
        viewport.unproject(paddle.cursor);
        paddle.cursor.set(paddle.cursor.x - paddle.getWidth()/2, paddle.getPosition().y);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
