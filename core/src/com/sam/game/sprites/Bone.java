package com.sam.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.sam.game.Constants.*;


public class Bone extends Sprite{
    private static final int WIDTH = 64; // tentative
    private static final int HEIGHT = 64; // tentative
    private static final TextureAtlas normalFrames = new TextureAtlas(Gdx.files.internal(
            "bonespritesheet/bone.atlas"));
    private static final TextureAtlas goldenFrames = new TextureAtlas(Gdx.files.internal(
            "goldenbonespritesheet/goldenbone.atlas"));

    private boolean isGolden;

    public Bone() {
        super();
    }

    public void init(float xPos, float yPos, boolean isGolden) {
        this.isGolden = isGolden;
        if(isGolden) {
           animation = new Animation(0.15f, goldenFrames.getRegions());
        } else {animation = new Animation(0.15f, normalFrames.getRegions());
        }
        bounds.set(xPos, yPos, WIDTH, HEIGHT);
        alive = true;
    }

    @Override
    public void update(float delta) {
        bounds.x -= GROUND_SPEED * delta;
    }

    public static void dispose() {
        normalFrames.dispose();
        goldenFrames.dispose();
    }

    public boolean getGolden() {
        return isGolden;
    }
}
