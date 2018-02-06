package com.sam.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;
import com.sam.game.screens.Shop;

import org.w3c.dom.html.HTMLImageElement;

import static com.sam.game.Constants.*;

public class Wolf {
    private static final int WIDTH = 192; // tentative
    private static final int HEIGHT = 110; // tentative

    private HowlAway game;
    public final TextureAtlas normalFrames, goldenBonesFrames, hermesFrames,
        magnetFrames, shieldFrames, fullMoonFrames;
    private Animation animation;
    private Rectangle bounds;
    private boolean alive, falling;
    private Vector2 velocity, center, position;
    private int state, skin;
    private float timer, groundLevel, frameSpeed;

    private Animation normalAnimation, fullMoonAnimation, goldenBonesAnimation,
        hermesAnimation, magnetAnimation, shieldAnimation;

    public Wolf(HowlAway game, int skin) {
        this.game = game;
        bounds = new Rectangle();
        alive = false;
        falling = false;
        this.skin = skin;
        switch(skin) {
            case 0:
                normalFrames = game.assets.get("normalwolf.atlas", TextureAtlas.class);
                goldenBonesFrames = game.assets.get("goldenboneswolf.atlas", TextureAtlas.class);
                magnetFrames = game.assets.get("magnetwolf.atlas", TextureAtlas.class);
                shieldFrames = game.assets.get("shieldwolf.atlas", TextureAtlas.class);
                fullMoonFrames = game.assets.get("werewolf.atlas", TextureAtlas.class);
                hermesFrames = game.assets.get("hermeswolf.atlas", TextureAtlas.class);
                frameSpeed = 0.075f;
                break;
            case 1:
                normalFrames = game.assets.get("skin1normalwolf.atlas", TextureAtlas.class);
                goldenBonesFrames = game.assets.get("skin1goldenboneswolf.atlas", TextureAtlas.class);
                magnetFrames = game.assets.get("skin1magnetwolf.atlas", TextureAtlas.class);
                shieldFrames = game.assets.get("skin1shield.atlas", TextureAtlas.class);
                fullMoonFrames = game.assets.get("skin1werewolf.atlas", TextureAtlas.class);
                hermesFrames = game.assets.get("skin1hermes.atlas", TextureAtlas.class);
                frameSpeed = 0.075f;
                break;
            case 2:
                normalFrames = game.assets.get("skin2wolf.atlas", TextureAtlas.class);
                goldenBonesFrames = game.assets.get("skin2goldenbones.atlas", TextureAtlas.class);
                magnetFrames = game.assets.get("skin2magnet.atlas", TextureAtlas.class);
                shieldFrames = game.assets.get("skin2shield.atlas", TextureAtlas.class);
                fullMoonFrames = game.assets.get("skin2werewolf.atlas", TextureAtlas.class);
                hermesFrames = game.assets.get("skin2hermes.atlas", TextureAtlas.class);
                frameSpeed = 0.075f;
                break;
            default:
                normalFrames = null;
                goldenBonesFrames = null;
                magnetFrames = null;
                shieldFrames = null;
                fullMoonFrames = null;
                hermesFrames = null;
                frameSpeed = 0;
        }

        velocity = new Vector2();
        center = new Vector2();
        position = new Vector2();
        state = NONE;
        timer = 0f;
        groundLevel = 20f;
    }

    public void init(float xPos, float yPos) {
        // 0.2 pag skin 0
        // 0.15f pag skin 1
        normalAnimation = new Animation(frameSpeed, normalFrames.getRegions());
        fullMoonAnimation = new Animation(frameSpeed/0.75f, fullMoonFrames.getRegions());
        goldenBonesAnimation = new Animation(frameSpeed, goldenBonesFrames.getRegions());
        hermesAnimation = new Animation(frameSpeed, hermesFrames.getRegions());
        magnetAnimation = new Animation(frameSpeed, magnetFrames.getRegions());
        shieldAnimation = new Animation(frameSpeed, shieldFrames.getRegions());

        animation = normalAnimation;


        velocity.set(0, 0);
        position.set(xPos, yPos);
        center.set(xPos+WIDTH/2, yPos+HEIGHT/2);
        bounds.set(xPos+WIDTH/2, yPos, WIDTH/2, HEIGHT);
        alive = true;
    }

    public void jump() {
        if(state == HERMES) {
            game.assets.get("sounds/flap_wing.ogg", Sound.class).play(0.5f);
            if(bounds.y == groundLevel) {
                velocity.y = 14f;
            }
            else {
                velocity.y = 10f;
            }
        }
        else if(bounds.y == groundLevel) {
            game.assets.get("sounds/jump.mp3", Sound.class).play(0.5f);
            velocity.y = 14f;
        }
    }

    public void update(float delta, float gravity, float groundLevel) {
        // if above ground, apply linear gravity (no acceleration)
        this.groundLevel = groundLevel;
        velocity.scl(delta);
        velocity.y += gravity * delta;
        velocity.scl(1 / delta);

        position.y += velocity.y;
        bounds.y += velocity.y;
        center.y = bounds.y + HEIGHT/2;

        if(bounds.y < groundLevel && falling == false && position.y < groundLevel) {
            bounds.y = groundLevel;
            position.y = groundLevel;
            velocity.y = 0;
        }

        if(timer > 0) {
            timer -= delta;
        }
        else {
            state = NONE;
            timer = 0f;
        }
        
        switch(state) {
            case FULL_MOON:
                animation = fullMoonAnimation;
                break;
            case GOLDEN_BONES:
                animation = goldenBonesAnimation;
                break;
            case HERMES:
                animation = hermesAnimation;
                break;
            case MAGNET:
                animation = magnetAnimation;
                break;
            case SHIELD:
                animation = shieldAnimation;
                break;
            default:
                animation = normalAnimation;
                break;
        }
    }

    public void dispose() {
        normalFrames.dispose();
        goldenBonesFrames.dispose();
        magnetFrames.dispose();
        shieldFrames.dispose();
        fullMoonFrames.dispose();
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public Vector2 getCenter() {
        return center;
    }
    public Animation getAnimation() {
        return animation;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void kill() {
        this.alive = false;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isAlive() {
        return alive;
    }
}
