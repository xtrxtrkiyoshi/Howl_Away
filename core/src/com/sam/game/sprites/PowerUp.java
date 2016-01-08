package com.sam.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.sam.game.Constants.*;

public class PowerUp extends Sprite {
    private static final int WIDTH = 10; // tentative
    private static final int HEIGHT = 10; // tentative

    /*private static final TextureAtlas fullMoonFrames = new TextureAtlas(
            Gdx.files.internal(""));
    private static final TextureAtlas hermesFrames = new TextureAtlas(
            Gdx.files.internal(""));
    private static final TextureAtlas goldenBonesFrames = new TextureAtlas(
            Gdx.files.internal(""));
    private static final TextureAtlas multiplierFrames = new TextureAtlas(
            Gdx.files.internal(""));
    private static final TextureAtlas headStartFrames = new TextureAtlas(
            Gdx.files.internal(""));
    private static final TextureAtlas wolfPackFrames = new TextureAtlas(
            Gdx.files.internal(""));
    private static final TextureAtlas magnetFrames = new TextureAtlas(
            Gdx.files.internal(""));
    private static final TextureAtlas bubbleFrames = new TextureAtlas(
            Gdx.files.internal(""));*/


    public static final int FULL_MOON = 0;
    public static final int HERMES = 1;
    public static final int GOLDEN_BONES = 2;
    public static final int MULTIPLIER = 3;
    public static final int HEAD_START = 4;
    public static final int WOLF_PACK = 5;
    public static final int MAGNET = 6;
    public static final int BUBBLE = 7;

    private int type;

    public PowerUp() {
        super();
        type = -1;
    }

    public void init(float xPos, float yPos, int type) {
        this.type = type;

        /*if(type == FULL_MOON) {
            animation = new Animation(0.15f, fullMoonFrames.getRegions());
        }
        if(type == HERMES) {
            animation = new Animation(0.15f, hermesFrames.getRegions());
        }
        if(type == GOLDEN_BONES) {
            animation = new Animation(0.15f, goldenBonesFrames.getRegions());
        }
        if(type == MULTIPLIER) {
            animation = new Animation(0.15f, multiplierFrames.getRegions());
        }
        if(type == HEAD_START) {
            animation = new Animation(0.15f, headStartFrames.getRegions());
        }
        if(type == WOLF_PACK) {
            animation = new Animation(0.15f, wolfPackFrames.getRegions());
        }
        if(type == MAGNET) {
            animation = new Animation(0.15f, magnetFrames.getRegions());
        }
        if(type == BUBBLE) {
            animation = new Animation(0.15f, bubbleFrames.getRegions());
        }*/

        bounds.set(xPos, yPos, WIDTH, HEIGHT);
    }

    @Override
    public void update(float delta) {
        bounds.x -= GROUND_SPEED * delta;
    }

    public int getType() {
        return type;
    }

    public static void dispose() {
        /*fullMoonFrames.dispose();
        hermesFrames.dispose();
        goldenBonesFrames.dispose();
        multiplierFrames.dispose();
        headStartFrames.dispose();
        wolfPackFrames.dispose();
        magnetFrames.dispose();
        bubbleFrames.dispose();*/
    }


}
