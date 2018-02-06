package com.sam.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

import javax.xml.soap.Text;

import static com.sam.game.Constants.*;


public class Bone extends Sprite{
    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;
    public final TextureAtlas normalFrames;
    public final TextureAtlas goldenFrames;

    private boolean isGolden;
    private Vector2 center, velocity;

    public Bone(HowlAway game) {
        super(game);
        normalFrames = game.assets.get("bone.atlas", TextureAtlas.class);
        goldenFrames = game.assets.get("goldenbone.atlas", TextureAtlas.class);
        center = new Vector2();
        velocity = new Vector2();
    }

    public void init(float xPos, float yPos, boolean isGolden) {
        this.isGolden = isGolden;
        if(isGolden) {
           animation = new Animation(0.15f, goldenFrames.getRegions());
        } else {
            animation = new Animation(0.15f, normalFrames.getRegions());
        }
        bounds.set(xPos, yPos, WIDTH, HEIGHT);
        alive = true;
        center.set(xPos + WIDTH/2, yPos + HEIGHT/2);
        velocity.set(0, 0);
    }


    public void update(float delta) {
        bounds.x -= Play.ground_speed * delta;
        bounds.x -= velocity.x * delta;
        bounds.y -= velocity.y * delta;
        center.x = bounds.x + WIDTH/2;
        center.y = bounds.y + HEIGHT/2;
    }

    @Override
    public void dispose() {
        normalFrames.dispose();
        goldenFrames.dispose();
    }

    public boolean getGolden() {
        return isGolden;
    }
    public Vector2 getCenter() {
        return center;
    }
    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }
}
