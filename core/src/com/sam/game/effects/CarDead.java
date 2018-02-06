package com.sam.game.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

public class CarDead extends Effect{

    private TextureAtlas frames;

    public CarDead(HowlAway game) {
        super(game);
        frames = game.assets.get("carDead.atlas", TextureAtlas.class);
    }

    public void init(float xPos, float yPos) {
        animation = new Animation(0.2f, frames.getRegions());
        alive = true;
        bounds.set(xPos, yPos, 512, 512);
    }


    @Override
    public void update(float delta) {
        elapsedTime += delta;
        bounds.x -= (Play.ground_speed+175) * delta;
    }

    public void dispose() {
        frames.dispose();
    }
}

