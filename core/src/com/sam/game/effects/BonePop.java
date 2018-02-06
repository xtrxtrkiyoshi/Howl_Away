package com.sam.game.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

import static com.sam.game.Constants.*;

public class BonePop extends Effect {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public final TextureAtlas frames;
    public final TextureAtlas goldenFrames;
    public BonePop(HowlAway game) {
        super(game);
        frames = game.assets.get("bonepop.atlas", TextureAtlas.class);
        goldenFrames = game.assets.get("goldenbonepop.atlas", TextureAtlas.class);
    }

    @Override
    public void update(float delta) {

        elapsedTime += delta;
        bounds.x -= Play.ground_speed * delta;
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

    public void dispose() {
        frames.dispose();
        goldenFrames.dispose();
    }
}
