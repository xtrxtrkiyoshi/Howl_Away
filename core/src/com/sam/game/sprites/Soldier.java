package com.sam.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.sam.game.Constants.*;

public class Soldier extends Sprite{
    private static final int WIDTH = 10; // tentative
    private static final int HEIGHT = 10; // tentative

    //private static final TextureAtlas frames = new TextureAtlas(Gdx.files.internal(""));


    public Soldier() {
        super();
    }

    public void init(float xPos, float yPos) {
       // animation = new Animation(0.15f, frames.getRegions());
        bounds.set(xPos, yPos, WIDTH, HEIGHT);
    }

    @Override
    public void update(float delta) {
        bounds.x -= GROUND_SPEED * delta;
    }

    public static void dispose() {
        //frames.dispose();
    }
}
