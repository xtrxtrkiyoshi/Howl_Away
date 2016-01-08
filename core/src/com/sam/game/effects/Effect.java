package com.sam.game.effects;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;


public abstract class Effect {
    protected float elapsedTime;
    protected Rectangle bounds;
    protected Animation animation;
    protected boolean alive;

    public Effect() {
        elapsedTime = 0f;
        bounds = new Rectangle();
        alive = false;
    }

    public abstract void update(float delta);

    public Animation getAnimation() {
        return animation;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }
}
