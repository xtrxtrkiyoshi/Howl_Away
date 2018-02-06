package com.sam.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

import static com.sam.game.Constants.*;

public class PowerUp{
    protected static final int WIDTH = 80;
    protected static final int HEIGHT = 80;
    protected Rectangle bounds;
    protected boolean alive;

    public final Texture fullMoon;
    public final Texture goldenBones;
    public final Texture hermes;
    public final Texture magnet;
    public final Texture multiplier;
    public final Texture shield;
    public final Texture wolfpack;

    private int type;

    private HowlAway game;

    public PowerUp(HowlAway game) {
        this.game = game;
        fullMoon = game.assets.get("powerup_fullmoon.png", Texture.class);
        goldenBones = game.assets.get("powerup_goldenbones.png", Texture.class);
        hermes = game.assets.get("powerup_hermes.png", Texture.class);
        magnet = game.assets.get("powerup_magnet.png", Texture.class);
        multiplier = game.assets.get("powerup_multiplier.png", Texture.class);
        shield = game.assets.get("powerup_shield.png", Texture.class);
        wolfpack = game.assets.get("powerup_wolfpack.png", Texture.class);

        bounds = new Rectangle();
        alive = false;
        type = NONE;
    }

    public void init(float xPos, float yPos, int type) {
        this.type = type;
        bounds.set(xPos, yPos, WIDTH, HEIGHT);
        alive = true;
    }

    public void update(float delta) {
        bounds.x -= Play.ground_speed * delta;
    }

    public int getType() {
        return type;
    }

    public Texture getTexture() {
        switch(type) {
            case FULL_MOON:
                return fullMoon;
            case GOLDEN_BONES:
                return goldenBones;
            case HERMES:
                return hermes;
            case MAGNET:
                return magnet;
            case MULTIPLIER:
                return multiplier;
            case SHIELD:
                return shield;
            case WOLF_PACK:
                return wolfpack;
            default:
                return null;
        }
    }


    public void dispose() {
        fullMoon.dispose();
        goldenBones.dispose();
        hermes.dispose();
        magnet.dispose();
        multiplier.dispose();
        shield.dispose();
        wolfpack.dispose();
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
