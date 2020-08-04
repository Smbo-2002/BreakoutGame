package com.mygdx.game;

import java.util.concurrent.ThreadLocalRandom;

public class RandomPowerUpGenerator{
    private static Class[] types = {ThreeBalls.class, StickyPaddle.class, ExtendPaddle.class};

    public static PowerUp next(float x, float y, float width, float height, GameScreen gameScreen) {
        try{
            int index = ThreadLocalRandom.current().nextInt(0, types.length);
            return (PowerUp) types[index].getDeclaredConstructor(
                    float.class,
                    float.class,
                    float.class,
                    float.class,
                    GameScreen.class
            ).newInstance(x, y, width, height, gameScreen);
        } catch(Exception e) { throw new RuntimeException(e); }
    }
}
