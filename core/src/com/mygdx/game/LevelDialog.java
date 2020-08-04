package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

public class LevelDialog implements Shape {
    private boolean visible = false;
    private boolean inFinalLevel = false;
    private GameScreen gameScreen;

    public LevelDialog(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Batch batch) {

    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer, Batch batch) {
        if (!visible)
            return;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);

        shapeRenderer.end();

        batch.begin();
        gameScreen.getFont_64().setColor(255, 255, 255, 1f);

        GlyphLayout layout = new GlyphLayout();

        if (gameScreen.isGameWon())
            layout.setText(gameScreen.getFont_64(), "You Won! " );
        else
            layout.setText(gameScreen.getFont_64(), "Level " + (gameScreen.getLevel() + 1));

        gameScreen.getFont_64().draw(
                batch,
                layout,
                GameScreen.WORLD_WIDTH / 2 - layout.width / 2,
                GameScreen.WORLD_HEIGHT / 2 + layout.height / 2);

        batch.end();
    }

    public void setVisible () {
        visible =  true;

        if (gameScreen.getLevel() == (GameScreen.GAME_PATTERNS.size() - 1) && !inFinalLevel)
            inFinalLevel = true;
        else if (gameScreen.getLevel() == (GameScreen.GAME_PATTERNS.size() - 1) && inFinalLevel)
            gameScreen.setGameWon(true);

        Timer.instance().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                visible = false;
                if (gameScreen.isGameWon()) {
                    inFinalLevel = false;
                    gameScreen.reset(true);
                }
            }
        },1);
    }

    public boolean isVisible() {
        return visible;
    }
}
