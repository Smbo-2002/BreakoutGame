package com.mygdx.game;


import com.badlogic.gdx.math.Rectangle;

public abstract class PowerUp extends Rectangle implements Shape {
        public static final int POWER_UP_BONUS = 10;
        public  static final float DROP_VELOCITY = 100f;
        public static final float DROP_PROBABILITY = 0.05f;
        public static final float DEF_WIDTH = 20;
        public static final float DEF_HEIGHT = 20;

        public PowerUp(float x, float y, float width, float height) {
                super(x, y, width, height);
        }

        public void update(float delta) {
              y -= delta * DROP_VELOCITY;
        }
        abstract void activate();
}
