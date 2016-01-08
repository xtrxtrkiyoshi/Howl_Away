package com.sam.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import static com.sam.game.Constants.*;

public class Wolf extends Sprite {
    private static final int WIDTH = 192; // tentative
    private static final int HEIGHT = 128; // tentative
    private static final TextureAtlas frames = new TextureAtlas(Gdx.files.internal(
            "skin1spritesheet/skin1.atlas"));
    //private static final TextureAtlas bubbleFrames = new TextureAtlas(Gdx.files.internal(
           // "bubblespritesheet/bubbleWolf.atlas"));
    private Vector2 velocity;

    private Animation mainAnimation;
    private Animation bubbleAnimation;

    public Wolf() {
        super();
        velocity = new Vector2();
    }

    public void init(float xPos, float yPos) {
        mainAnimation = new Animation(0.075f, frames.getRegions());
        //bubbleAnimation = new Animation(0.2f, bubbleFrames.getRegions());

        animation = mainAnimation;
        velocity.set(0, 0);
        bounds.set(xPos, yPos, WIDTH, HEIGHT);
        alive = true;
    }

    public void jump() {
        if (bounds.y == 0) {
            velocity.y = 55;
            // play sound or something
        }

        /*if(animation.equals(mainAnimation))
            animation = bubbleAnimation;
        else
            animation = mainAnimation;*/
    }

    @Override
    public void update(float delta) {
        // if above ground, apply linear gravity (no acceleration)
        velocity.scl(delta);
        velocity.y += GRAVITY * delta;
        velocity.scl(1/delta);
        bounds.y += velocity.y;

        if(bounds.y < 0) {
            bounds.y = 0;
            velocity.y = 0;
        }





    }

    public static void dispose() {
        frames.dispose();
    }
}
