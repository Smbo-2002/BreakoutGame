package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class Brick extends Rectangle implements Shape {
    public static final float DEFAULT_WIDTH = 74;
    public static final float DEFAULT_HEIGHT = 28;
    public static final float DEFAULT_X_SPACE = 5;
    public static final float DEFAULT_Y_SPACE = 5;
    private int base_health = -1;
    private int health = base_health;
    private PowerUp powerUp;
    private GameScreen gameScreen;
    private Sound crackSound;
    private boolean powerUpPicked = true;

    Brick(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    Brick(float x, float y, int health) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.base_health = health;
        this.health = health;
    }

    Brick(float x, float y, int health, PowerUp powerUp, GameScreen gameScreen) {
        this(x, y, health);
        if (powerUp != null)
            powerUpPicked = false;
        this.powerUp = powerUp;
        this.gameScreen = gameScreen;
        this.crackSound = gameScreen.crackSound;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Batch batch) {
        TextureRegion textureRegion = gameScreen.getTextureAtlas().findRegion("brick" + base_health + health);
        batch.begin();
        batch.draw(textureRegion, x, y, width, height);
        batch.end();
    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer, Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.valueOf("ffffff"));
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    public void playCrackSound() {
        crackSound.play(1f, 1f, 0);
    }

    public int getHealth() {
        return health;
    }

    public void decrementHealth() {
        this.health--;
    }

    public boolean hasPowerUp() {
        return powerUp != null;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public int getBase_health() {
        return base_health;
    }

    public boolean isPowerUpPicked() {
        return powerUpPicked;
    }

    public void setPowerUpPicked(boolean powerUpPicked) {
        this.powerUpPicked = powerUpPicked;
>>>>>>> game-rebirth
    }
}
