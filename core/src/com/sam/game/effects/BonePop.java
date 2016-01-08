package com.sam.game.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.sam.game.Constants.*;

public class BonePop extends Effect {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private static final TextureAtlas frames = new TextureAtlas(Gdx.files.internal(
            "bonepopspritesheet/bonepop.atlas"));
    private static final TextureAtlas goldenFrames = new TextureAtlas(Gdx.files.internal(
            "goldenbonepopspritesheet/goldenbonepop.atlas"));
    public BonePop() {
        super();
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        bounds.x -= GROUND_SPEED * delta;
    }

    public void init(float xPos, float yPos, boolean isGolden) {
        if(isGolden) {
            animation = new Animation(0.15f, goldenFrames.getRegions());
        }
        else {
            animation = new Animation(0.15f, frames.getRegions());
        }
        bounds.set(xPos, yPos, WIDTH, HEIGHT);
        alive = true;
    }

    public static void dispose() {
        frames.dispose();
        goldenFrames.dispose();
    }
}
