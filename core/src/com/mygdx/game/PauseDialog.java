package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class PauseDialog extends Rectangle implements Shape {
    public static final float DEF_WIDTH = 460;
    public static final float DEF_HEIGHT= 280;
    private float timePassed = 0;

    private GameScreen gameScreen;

    public PauseDialog(float x, float y, float width, float height, GameScreen gameScreen) {
        super(x, y, width, height);
        this.gameScreen = gameScreen;
    }

    @Override
    public void draw() {

    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer, Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(36, 77, 145, 0.9f);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
//        shapeRenderer.circle(x + 50, y + 100, 20);
//        shapeRenderer.circle(x + 150, y + 100, 20);
//        shapeRenderer.rect(x + 50 + 10, y + 90, 100, 40);
        float circle_radius = 20f;
        float small_circle_radius = 15f;
        float rect_width = 100f;
        float rect_height = circle_radius*2f;
        float full_width = circle_radius * 2f + rect_width;
        shapeRenderer.circle(x + ((width - full_width) /2f) + circle_radius, y + height/2f, circle_radius);
        shapeRenderer.rect(x + ((width - full_width) /2f) + circle_radius, y + height/2f - circle_radius, rect_width, rect_height);
        shapeRenderer.circle(x + ((width - full_width) /2f) + rect_width + circle_radius, y + height/2f, circle_radius);
        shapeRenderer.setColor(Color.valueOf("b3306d"));
        shapeRenderer.circle(
                gameScreen.isMouseControl() ? x + ((width - full_width) /2f) + circle_radius : x + ((width - full_width) /2f) + circle_radius + 100,
                y + height/2f,
                small_circle_radius);
        shapeRenderer.end();
        batch.begin();

        GlyphLayout layout = new GlyphLayout();
        gameScreen.getFont_32().setColor(Color.RED);
//        gameScreen.getFont_32().setColor(179, 48, 109, 0.5f);
        layout.setText(gameScreen.getFont_32(), "CONTROLS");
        gameScreen.getFont_32().draw(batch, layout, x + (width - layout.width) / 2f, y + height -  40);

        layout = new GlyphLayout();
        gameScreen.getFont_18().setColor(255, 0, 255, 1f);
        layout.setText(gameScreen.getFont_18(), "press C to resume");
        gameScreen.getFont_18().draw(batch, layout, x + (width - layout.width) / 2f, y + 40);
        batch.end();
    }

    public void changeControl(float delta) {
        if(Gdx.input.isTouched()) {
            Vector3 cursor = new Vector3();
            cursor.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameScreen.getViewport().unproject(cursor);
            System.out.println(cursor.x + " " + cursor.y);
            if ((cursor.x > this.x && cursor.x < this.x + width) && (cursor.y > y && cursor.y < y + height) && timePassed> 0.08) {
                gameScreen.setMouseControl(!gameScreen.isMouseControl());
                timePassed = 0;
            } else {
                timePassed += delta;
            }
        }
    }

}
