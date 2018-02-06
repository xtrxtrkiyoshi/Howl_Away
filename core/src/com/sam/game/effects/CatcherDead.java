package com.sam.game.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

public class CatcherDead extends Effect{

    private TextureAtlas frames;

    public CatcherDead(HowlAway game) {
        super(game);
        frames = game.assets.get("catcherdead.atlas", TextureAtlas.class);
    }

    public void init(float xPos, float yPos) {
        animation = new Animation(0.1f, frames.getRegions());
        alive = true;
        bounds.set(xPos, yPos, 400, 301);
    }


    @Override
    public void update(float delta) {
        elapsedTime += delta;
        bounds.x -= (Play.ground_speed) * delta;
    }

    public void dispose() {
        frames.dispose();
    }
}