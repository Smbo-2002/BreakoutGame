package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.omg.PortableInterceptor.DISCARDING;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    public static final ArrayList<int[][]> GAME_PATTERNS = new ArrayList<>(
            Arrays.asList(
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
            },
                    new int[][] {
                            {1, 1, 1, 1, 1, 1, 1, 1},
                            {1, 1, 0, 1, 0, 1, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1},
                            {1, 0, 1, 1, 1, 1, 0, 1},
                            {1, 0, 1, 1, 1, 1, 0, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1},
                            {1, 0, 1, 0, 0, 1, 0, 1},
                            {1, 0, 0, 0, 0, 0, 0, 1},
                    },
                    new int[][] {
                            {1, 1, 1, 1, 1, 1, 1, 1},
                            {1, 1, 0, 0, 0, 0, 0, 1},
                            {1, 0, 1, 0, 0, 0, 0, 1},
                            {1, 0, 1, 1, 0, 0, 1, 1},
                            {1, 1, 0, 1, 1, 0, 1, 1},
                            {1, 0, 1, 1, 1, 1, 0, 1},
                            {1, 0, 1, 1, 1, 1, 1, 1},
                            {1, 0, 1, 1, 1, 1, 0, 1},
                            {1, 1, 1, 1, 1, 1, 2, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1},
                    })
//            Arrays.asList(
//                    new int[][] {
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 1, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                    },
//                    new int[][] {
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 1, 1, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                    },
//                    new int[][] {
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 1, 1, 1, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 0, 0, 0, 0, 0, 0},
//                    }
//            )
    );

    public static final float WORLD_WIDTH = 640;
    public static final float WORLD_HEIGHT = 640;
    public static final int BASE_HEALTH = 3;

    private final BreakoutGame breakoutGame;

    // Player progress
    private int score;
    private int level = 0;
    private int health = BASE_HEALTH;

    // Game state
    private boolean gameBegan = false;
    private boolean paddleIsExtended = false;
    private boolean paddleIsSticky = false;
    private boolean gamePaused = false;
    private boolean mouseControl = true;
    private boolean gameWon = false;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;

    // Assets
    // private Texture bg;
    public Sound bounceSound;
    private BitmapFont font_32;
    private BitmapFont font_18;
    private BitmapFont font_64;

    // Game objects
    private Paddle paddle;
    private ArrayList<Ball> balls = new ArrayList<>();
    private List<Brick> bricks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();

    private PauseDialog pauseDialog;
    private LevelDialog levelDialog;

    GameScreen(BreakoutGame breakoutGame) { this.breakoutGame = breakoutGame; }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

//        bg = breakoutGame.getAssetManager().get("background.jpg");
        bounceSound = breakoutGame.getAssetManager().get("bounce.mp3");
        font_32 = breakoutGame.getAssetManager().get("font_32.fnt");
        font_18 = breakoutGame.getAssetManager().get("font_18.fnt");
        font_64 = breakoutGame.getAssetManager().get("font_64.fnt");

        paddle = new Paddle(WORLD_WIDTH/2 - Paddle.DEF_WIDTH/2, 50, Paddle.DEF_WIDTH, Paddle.DEF_HEIGHT);
        balls.add(new Ball(100, paddle.y + paddle.height + 20, Ball.DEF_RADIUS, bounceSound));
        addBricks();

        pauseDialog = new PauseDialog(
                (WORLD_WIDTH - PauseDialog.DEF_WIDTH) /  2f,
                180f,
                PauseDialog.DEF_WIDTH,
                PauseDialog.DEF_HEIGHT,
                this);

        levelDialog = new LevelDialog(this);
        levelDialog.setVisible();
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
        shapeRenderer.end();

        // Draw other objects
        for (Ball ball : balls)
            ball.drawDebug(shapeRenderer, batch);
        paddle.drawDebug(shapeRenderer, batch);
        for (Brick b: bricks)
            b.drawDebug(shapeRenderer, batch);
        for (PowerUp p: powerUps)
            p.drawDebug(shapeRenderer, batch);

        if (gamePaused) {
            pauseDialog.drawDebug(shapeRenderer, batch);
        }

        levelDialog.drawDebug(shapeRenderer, batch);


        batch.begin();
        font_32.setColor(255, 255, 255, 1);
        font_32.draw(batch, "Score: " + score, 20, WORLD_HEIGHT-20);

        GlyphLayout layout = new GlyphLayout();
        font_32.setColor(255, 255, 255, 1);
        layout.setText(font_32, BASE_HEALTH + "/" + health);
        font_32.draw(batch, layout, WORLD_WIDTH/2 - layout.width/2, WORLD_HEIGHT-20);

        font_18.setColor(255, 255, 255, 0.7f);
        layout = new GlyphLayout();
        layout.setText(font_18, "press p to pause...");
        font_18.draw(batch, layout, WORLD_WIDTH - layout.width - 20, WORLD_HEIGHT-20);

        batch.end();
    }



    private void update(float delta) {
        checkUnpause();
        checkPaused();
        if (gamePaused) {
            pauseDialog.changeControl(delta);
            return;
        }
        if (gameWon)
            return;
        if (isLevelCompleted()){
                if (level < ( GAME_PATTERNS.size() - 1 )) {
                    level++;
                    reset(false);
                }
                levelDialog.setVisible();
            return;
        }
        if (mouseControl)
            paddle.follow(delta, getCursorPosition());
        else {
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                paddle.moveLeft(delta);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                paddle.moveRight(delta);
            }
        }
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

    private void addBricks() {

        int horizontal_blocks = GAME_PATTERNS.get(level)[0].length;
        int vertical_blocks = GAME_PATTERNS.get(level).length;

        float all_blocks_width = horizontal_blocks * Brick.DEFAULT_WIDTH;
        float all_blocks_in_between_space = (horizontal_blocks - 1) * Brick.DEFAULT_X_SPACE;

        float leftPadding = (WORLD_HEIGHT - all_blocks_width - all_blocks_in_between_space) / 2;
        float topPadding = 100;

        for (int i = 0; i < horizontal_blocks; i++) {
            for (int j = 0; j < vertical_blocks; j++) {
                if (GAME_PATTERNS.get(level)[j][i] == 0)
                    continue;
                float x = leftPadding + Brick.DEFAULT_X_SPACE * i + Brick.DEFAULT_WIDTH * i;
                float y = WORLD_HEIGHT - (topPadding + Brick.DEFAULT_HEIGHT * j + Brick.DEFAULT_Y_SPACE*j);
                bricks.add(
                        new Brick(
                                x,
                                y,
                                GAME_PATTERNS.get(level)[j][i],
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

    private void checkBrickCollision() {
        for (Ball ball : balls) {
            Iterator<Brick> iterator = bricks.iterator();
            boolean hit = false;
            while (iterator.hasNext() && !hit) {
                Brick b = iterator.next();
                Collision collision = checkCollision(ball, b);
                if (collision.isOverlapping()) {
                    hit = true;
                    // Check for powerUps
                    if (b.hasPowerUp())
                        powerUps.add(b.getPowerUp());

                    Direction dir = collision.getDirection();
                    Vector2 difference_vector = collision.getDifference();
                    if (dir == Direction.LEFT || dir == Direction.RIGHT) {
                        // horizontal hit
                        ball.setDirection(ball.getDirectionVector().scl(-1, 1));

                        float penetration = ball.radius - Math.abs(difference_vector.x);
                        if (dir == Direction.LEFT)
                            ball.x += penetration;
                        else
                            ball.x -= penetration;
                    } else {
                        // vertical hit
                        ball.setDirection(ball.getDirectionVector().scl(1, -1));

                        float penetration = ball.radius - Math.abs(difference_vector.y);
                        if (dir == Direction.UP)
                            ball.y -= penetration;
                        else
                            ball.y += penetration;
                    }

                    scoreIncrement();
                    if (b.getHealth() == 1)
                        iterator.remove();
                    else
                        b.decrementHealth();
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

    private void checkPaused() {
        boolean isAPressed = Gdx.input.isKeyPressed(Input.Keys.P);
        if (isAPressed) gamePaused = true;
    }

    private void checkUnpause() {
        boolean isAPressed = Gdx.input.isKeyPressed(Input.Keys.C);
        if (isAPressed) gamePaused = false;
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
                if (balls.size() == 1 && health == 0)
                    reset(true);
                else if (balls.size() == 1 && health > 0) {
                    balls.clear();
                    balls.add(new Ball(paddle.x + paddle.width/2, paddle.y + paddle.height + 20, Ball.DEF_RADIUS, bounceSound));
                    gameBegan = false;
                    health--;
                }
                else
                    ballIterator.remove();
            }
        }
    }

    public void reset(boolean fullReset) {
        if (fullReset) {
            score = 0;
            level = 0;
            Timer.instance().clear();
            gameWon = false;
            levelDialog.setVisible();
            health = BASE_HEALTH;
        }
        gameBegan = false;
        bricks.clear();
        addBricks();
        balls.clear();
        balls.add(new Ball(100, paddle.y + paddle.height + 20, Ball.DEF_RADIUS, bounceSound));
        powerUps.clear();
        paddleIsExtended = false;
        paddle.width = Paddle.DEF_WIDTH;
    }

    private void stopPaddleLeavingTheScreen() {
        paddle.setX(MathUtils.clamp(paddle.x, 0, WORLD_WIDTH - paddle.width));
    }

    private void checkPaddleCollision() {
        // Check paddle collision with balls
        for (Ball ball: balls) {
            Collision collision = checkCollision(ball, paddle);
            if (collision.isOverlapping() && collision.getDirection() == Direction.UP) {
                    // This is a vertical hit.
                    // max horizontal distance between ball center and paddle center, when colliding
                    float newAngle = 180 - ((ball.x - paddle.x) * 180) / paddle.width;
                    ball.setDirection(ball.clampAngle(newAngle));
                    ball.getDirectionVector().y = Math.abs(ball.getDirectionVector().y);
//                    float penetration = ball.radius - Math.abs(collision.getDifference().y);
//                    ball.y += penetration;
                ball.y = ball.radius + paddle.y + paddle.height;
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

    public BitmapFont getFont_18 () {
        return font_18;
    }

    public BitmapFont getFont_32 () {
        return font_32;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public boolean isMouseControl() {
        return mouseControl;
    }

    public void setMouseControl(boolean mouseControl) {
        this.mouseControl = mouseControl;
    }

    private boolean isLevelCompleted() {
        return bricks.size() == 0;
    }

    public BitmapFont getFont_64() {
        return font_64;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    };

    public Direction vectorDirection(Vector2 target) {
        Vector2[] compass = new Vector2[] {
            new Vector2(0.0f, 1.0f), // up
            new Vector2(1.0f, 0.0f), // right
            new Vector2(0.0f, -1.0f), // down
            new Vector2(-1.0f, 0.0f) // left
        };
        float max = 0.0f;
        int best_match = 0;

        for (int i = 0; i < compass.length - 1; i++) {
            Vector2 normalizedTarget = target.nor();
            float dot_product = Vector2.dot(normalizedTarget.x, normalizedTarget.y, compass[i].x, compass[i].y);

            if (dot_product > max) {
                max = dot_product;
                best_match = i;
            }
        }

        return Direction.values()[best_match];
    }

    Collision checkCollision(Ball ball, Rectangle rectangle)
    {
        // get center point circle first
        Vector2 ballCenter = new Vector2(ball.x, ball.y);
        // calculate AABB info (center, half-extents)
        Vector2 halfWidthHeightOfRect = new Vector2(rectangle.width/2f, rectangle.height/2f);
        Vector2 rectCenter = new Vector2(
                rectangle.x + halfWidthHeightOfRect.x,
                rectangle.y + halfWidthHeightOfRect.y
        );
        // get difference vector between both centers
        Vector2 difference = new Vector2(
                ballCenter.x - rectCenter.x,
                ballCenter.y - rectCenter.y
        );
        Vector2 clamped = new Vector2(
                MathUtils.clamp(difference.x, -halfWidthHeightOfRect.x, halfWidthHeightOfRect.x),
                MathUtils.clamp(difference.y, -halfWidthHeightOfRect.y, halfWidthHeightOfRect.y)
        );
        Vector2 closestPoint  = new Vector2(
                rectCenter.x + clamped.x,
                rectCenter.y + clamped.y
        );
        // retrieve vector between center circle and closest point AABB and check if length <= radius
        difference = new Vector2(
                closestPoint.x - ballCenter.x,
                closestPoint.y - ball.y
        );
//        return difference.len() < ball.radius;
        if (difference.len() <= ball.radius)
            return new Collision(true, vectorDirection(difference), difference);
        else
            return new Collision(false, Direction.UP, new Vector2(0f, 0f));
    }


    public static class Triple<A, B, C> {
        protected A a;
        protected B b;
        protected C c;
        Triple (A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static class Collision extends Triple<Boolean, Direction, Vector2> {
        Collision(boolean collided, Direction dir, Vector2 difference) {
            super(collided, dir, difference);
        }

        public boolean isOverlapping () {
            return a;
        }

        public Direction getDirection () {
            return b;
        }

        public Vector2 getDifference () {
            return c;
        }
    }

}
