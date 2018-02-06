package com.sam.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

import static com.sam.game.Constants.*;

public class Soldier extends Sprite {
    private int width; // tentative
    private int height; // tentative
    private TextureAtlas catcher;
    private boolean falling;
    private float elapsedTime;

    public Soldier(HowlAway game) {
        super(game);
        falling = false;
        catcher = game.assets.get("catcher.atlas");
    }

    public void init(float xPos, float yPos) {
        animation = new Animation(0.05f, catcher.getRegions());
        width = 60;
        height = 180;
        bounds.set(xPos+160, yPos, width, height);
        alive = true;
        falling = false;
        elapsedTime = 0f;
    }


    public void update(float delta, boolean isNear) {
        if(isNear) {
            elapsedTime += delta;
        }
        if(falling) {
            bounds.y -= 300f * delta;
        }
        bounds.x -= Play.ground_speed * delta;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public void dispose() {
        catcher.dispose();
    }
    public float getElapsedTime() {
        return elapsedTime;
    }
}