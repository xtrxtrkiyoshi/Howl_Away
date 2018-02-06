package com.sam.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

import static com.sam.game.Constants.*;

public class Civilian extends Sprite {
    private int width; // tentative
    private int height; // tentative
    private TextureAtlas littleGirl;
    private boolean falling;
    private float elapsedTime;
    private int speed;

    public Civilian(HowlAway game) {
        super(game);
        falling = false;
        littleGirl = game.assets.get("littlegirl.atlas");
    }

    public void init(float xPos, float yPos) {
        animation = new Animation(0.15f, littleGirl.getRegions());
        width = 83;
        height = 127;
        bounds.set(xPos, yPos, width, height);
        alive = true;
        falling = false;
        elapsedTime = 0f;
        speed = MathUtils.random(180, 220);
    }


    public void update(float delta) {
        elapsedTime += delta;
        if(falling) {
            bounds.y -= 300f * delta;
        }
        bounds.x -= (Play.ground_speed-speed) * delta;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public void dispose() {
        littleGirl.dispose();
    }
    public float getElapsedTime() {
        return elapsedTime;
    }
    public boolean isFalling() {
        return falling;
    }
}
