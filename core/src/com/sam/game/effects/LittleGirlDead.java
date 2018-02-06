package com.sam.game.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

/**
 * Created by sam on 1/23/16.
 */
public class LittleGirlDead extends Effect{

    private TextureAtlas frames;

    public LittleGirlDead(HowlAway game) {
        super(game);
        frames = game.assets.get("littlegirldead.atlas", TextureAtlas.class);
    }

    public void init(float xPos, float yPos) {
        animation = new Animation(0.1f, frames.getRegions());
        alive = true;
        bounds.set(xPos-30, yPos, 209, 209);

    }


    @Override
    public void update(float delta) {
        elapsedTime += delta;
        bounds.x -= Play.ground_speed * delta;
    }

    public void dispose() {
        frames.dispose();
    }
}
