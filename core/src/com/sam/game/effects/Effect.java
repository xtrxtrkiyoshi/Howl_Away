package com.sam.game.effects;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.sam.game.HowlAway;


public abstract class Effect {
    protected float elapsedTime;
    protected Rectangle bounds;
    protected Animation animation;
    protected boolean alive;
    protected HowlAway game;
    public Effect(HowlAway game) {
        this.game = game;
        elapsedTime = 0f;
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

    public float getElapsedTime() {
        return elapsedTime;
    }
}
