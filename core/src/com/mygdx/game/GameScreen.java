package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    public static final ArrayList<int[][]> GAME_PATTERNS = new ArrayList<>();

    private static final float WORLD_WIDTH = 640;
    private static final float WORLD_HEIGHT = 640;
    private final BreakoutGame breakoutGame;
    private boolean gameBegan = false;
    private int score;
    private boolean paddleIsExtended = false;
    private boolean paddleIsSticky = false;

    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private OrthographicCamera camera;
//    private Texture bg;
    private SpriteBatch batch;
    public Sound bounceSound;
    private Timer extendedPaddleTimer = Timer.instance();

    private Paddle paddle;
    private BitmapFont font;
    private ArrayList<Ball> balls = new ArrayList<>();
    private List<Brick> bricks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();

    GameScreen(BreakoutGame breakoutGame) { this.breakoutGame = breakoutGame; }

    @Override
    public void show() {
        GAME_PATTERNS.add(
                new int[][] {
                        {1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 0, 0, 1, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 0, 1, 1, 0, 1, 1},
                        {1, 0, 1, 1, 1, 1, 0, 1},
                        {1, 0, 1, 1, 1, 1, 0, 1},
                        {1, 0, 1, 1, 1, 1, 0, 1},
                        {1, 0, 1, 0, 0, 1, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1},
                }
        );

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
//        bg = breakoutGame.getAssetManager().get("background.jpg");
        bounceSound = breakoutGame.getAssetManager().get("bounce.mp3");
        paddle = new Paddle(WORLD_WIDTH/2 - Paddle.DEF_WIDTH/2, 50, Paddle.DEF_WIDTH, Paddle.DEF_HEIGHT);
        balls.add(new Ball(100, paddle.y + paddle.height + 20, Ball.DEF_RADIUS, bounceSound));

        font = breakoutGame.getAssetManager().get("font.fnt");

        addBricks();
    }

    private void addBricks() {

        int horizontal_blocks = GAME_PATTERNS.get(0)[0].length;
        int vertical_blocks = GAME_PATTERNS.get(0).length;

        float all_blocks_width = horizontal_blocks * Brick.DEFAULT_WIDTH;
        float all_blocks_in_between_space = (horizontal_blocks - 1) * Brick.DEFAULT_X_SPACE;

        float leftPadding = (WORLD_HEIGHT - all_blocks_width - all_blocks_in_between_space) / 2;
        float topPadding = 100;

        for (int i = 0; i < horizontal_blocks; i++) {
            for (int j = 0; j < vertical_blocks; j++) {
                if (GAME_PATTERNS.get(0)[j][i] == 0)
                    continue;
                float x = leftPadding + Brick.DEFAULT_X_SPACE * i + Brick.DEFAULT_WIDTH * i;
                float y = WORLD_HEIGHT - (topPadding + Brick.DEFAULT_HEIGHT * j + Brick.DEFAULT_Y_SPACE*j);
                bricks.add(
                        new Brick(
                                x,
                                y,
                                GAME_PATTERNS.get(0)[j][i],
                                (float) Math.random() < PowerUp.DROP_PROBABILITY ?
                                        RandomPowerUpGenerator.next(
                                                x,
                                                y,
                                                PowerUp.DEF_WIDTH,
                                                PowerUp.DEF_HEIGHT,
                                                this
                                        ) : null
                        )
                );
            }
        }
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
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
//        batch.draw(bg, 0, 0);
        batch.end();
    }

    private void drawDebug() {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        // Draw the background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.valueOf("EEA47D"));
        shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        // Draw other objects
        for (Ball ball : balls)
            ball.drawDebug(shapeRenderer);
        paddle.drawDebug(shapeRenderer);
        for (Brick b: bricks)
            b.drawDebug(shapeRenderer);
        for (PowerUp p: powerUps)
            p.drawDebug(shapeRenderer);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "Score: " + score, 20, WORLD_HEIGHT-20);
        batch.end();
    }

    private void update(float delta) {
        paddle.follow(delta, getCursorPosition());
        stopPaddleLeavingTheScreen();
        if (paddleIsSticky) {
            for (Ball ball: balls)
                ball.followPaddle(paddle);
                checkStickiness();
            return;
        }
        if (!gameBegan) {
            checkStart();
            if (!balls.isEmpty())
                balls.get(0).followPaddle(paddle);
        }
        else if (gameBegan) {
            for (Ball ball : balls)
                ball.move(delta);
            stopBallLeavingTheScreen();
            for(PowerUp p: powerUps)
                p.update(delta);
            checkPaddleCollision();
            checkBrickCollision();
        }
    }

    private void checkBrickCollision() {
        for (Ball ball : balls) {
            Iterator<Brick> iterator = bricks.iterator();
            while (iterator.hasNext()) {
                Brick b = iterator.next();
                if (Intersector.overlaps(
                        new Rectangle(
                                ball.x - ball.radius, ball.y - ball.radius, ball.radius * 2, ball.radius * 2),
                        b
                )) {
                    // Check for powerUps
                    if (b.hasPowerUp())
                        powerUps.add(b.getPowerUp());

                    // Check for side collision
                    Vector2 distance = new Vector2(ball.x - ball.radius, ball.y - ball.radius).sub(b.x, b.y);
                    Vector2 scaleFactor = new Vector2(1 / b.width, 1 / b.height);

                    distance.scl(scaleFactor);

                    if (Math.abs(distance.x) >= Math.abs(distance.y)) {
                        // scaled delta x was larger than delta y. This is a horizontal hit.
                        if (Math.signum(-ball.getDirectionVector().x) == Math.signum(distance.x)) {
                            ball.setDirection(ball.getDirectionVector().scl(-1, 1));
                            scoreIncrement();
                            if (b.getHealth() == 1)
                                iterator.remove();
                            else
                                b.decrementHealth();
                        }
                    } else {
                        // scaled delta y was larger than delta x. This is a vertical hit.
                        if (Math.signum(-ball.getDirectionVector().y) == Math.signum(distance.y)) {
                            ball.setDirection(ball.getDirectionVector().scl(1, -1));
                            scoreIncrement();
                            if (b.getHealth() == 1)
                                iterator.remove();
                            else
                                b.decrementHealth();
                        }
                    }
                }
            }
        }
    }

    private void scoreIncrement() {
        setScore(score + 1);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    private void checkStart() {
        boolean isTouched = Gdx.input.isTouched();
        boolean isAPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (isTouched || isAPressed) gameBegan = true;
    }

    private void checkStickiness() {
        boolean isTouched = Gdx.input.isTouched();
        boolean isAPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (isTouched || isAPressed) paddleIsSticky = false;
    }

    private void stopBallLeavingTheScreen() {
        Iterator<Ball> ballIterator = balls.iterator();
        while (ballIterator.hasNext()){
            Ball ball = ballIterator.next();
            if ((ball.getDirectionVector().x < 0f && ball.x - ball.radius < 0) ||
                    (ball.getDirectionVector().x > 0f && ball.x + ball.radius > WORLD_WIDTH)) {
                ball.setDirection(ball.getDirectionVector().scl(-1, 1));
                ball.playBounceSound();
            }

            if (ball.getDirectionVector().y > 0f && ball.y + ball.radius > WORLD_HEIGHT + 0.1f) {
                ball.setDirection(ball.getDirectionVector().scl(1, -1));
                ball.playBounceSound();
            }

            if (ball.getDirectionVector().y < 0 && ball.y + ball.radius < 0 + 0.1f) {
                if (balls.size() == 1)
                    reset();
                else
                    ballIterator.remove();
            }
        }
    }

    private void reset() {
        score = 0;
        gameBegan = false;
        bricks.clear();
        addBricks();
        balls.clear();
        balls.add(new Ball(100, paddle.y + paddle.height + 20, Ball.DEF_RADIUS, bounceSound));
        powerUps.clear();
        paddleIsExtended = false;
        extendedPaddleTimer.clear();
        paddle.width = Paddle.DEF_WIDTH;
    }

    private void stopPaddleLeavingTheScreen() {
        paddle.setX(MathUtils.clamp(paddle.x, 0, WORLD_WIDTH - paddle.width));
    }

    private void checkPaddleCollision() {
        // Check paddle collision with balls
        for (Ball ball: balls) {
            Vector2 distance = new Vector2(ball.x, ball.y).sub(paddle.x, paddle.y);
            Vector2 scaleFactor = new Vector2(1 / paddle.width, 1 / paddle.height);

            distance.scl(scaleFactor);

            if (Intersector.overlaps(
                    new Rectangle(
                            ball.x - ball.radius, ball.y - ball.radius, ball.radius * 2, ball.radius * 2),
                    paddle
            )) {
                if (Math.abs(distance.x) >= Math.abs(distance.y)) {
                    // scaled delta x was larger than delta y. This is a horizontal hit.
                    ball.setDirection(ball.getDirectionVector().scl(-1, 1));
                    ball.setX(
                            ((ball.x - (paddle.x + paddle.width / 2)) >= 0) ?
                                    ball.x + ((paddle.x + paddle.width) - (ball.x - ball.radius)) :
                                    ball.x - ((ball.x + ball.radius) - paddle.x)
                    );
                } else {
                    // This is a vertical hit.
                    // max horizontal distance between ball center and paddle center, when colliding
                    float newAngle = 180 - ((ball.x - paddle.x) * 180) / paddle.width;
                    ball.setDirection(ball.clampAngle(newAngle));
                }
            }
        }

        // Check paddle collision with powerUps

        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp p = powerUpIterator.next();
            if (Intersector.overlaps(p, paddle)) {
                p.activate();
                powerUpIterator.remove();
            }
        }
    }

    public void addBall(Ball b) {
        balls.add(b);
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Vector3 getCursorPosition () {
        Vector3 cursor = new Vector3();
        cursor.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(cursor);
        return cursor;
    }

    public boolean isPaddleExtended() {
        return paddleIsExtended;
    }

    public void setPaddleIsExtended(boolean paddleIsExtended) {
        this.paddleIsExtended = paddleIsExtended;
    }

    public Timer getExtendedPaddleTimer() {
        return extendedPaddleTimer;
    }

    public boolean isPaddleSticky() {
        return paddleIsSticky;
    }

    public void setPaddleIsSticky(boolean paddleIsSticky) {
        this.paddleIsSticky = paddleIsSticky;
    }

    public List<PowerUp> getPowerUps () {
        return powerUps;
    }

    public List<Ball> getBalls () {
        return balls;
    }
}
