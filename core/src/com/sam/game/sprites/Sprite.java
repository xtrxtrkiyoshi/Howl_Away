package com.sam.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.sam.game.HowlAway;

public abstract class Sprite {
    protected Animation animation;
    protected Rectangle bounds;
    protected boolean alive;
    protected HowlAway game;

    public Sprite(HowlAway game) {
        this.game = game;
        bounds = new Rectangle();
        alive = false;
    }

    public void update(float delta) {

    }
    public abstract void dispose();

    public Animation getAnimation() {
        return animation;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void kill() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
