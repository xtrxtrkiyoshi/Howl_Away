package com.sam.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;

public abstract class Sprite {
    protected Animation animation;
    protected Rectangle bounds;
    protected boolean alive;

    public Sprite() {
        bounds = new Rectangle();
        alive = false;
    }

    public abstract void update(float delta);

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
