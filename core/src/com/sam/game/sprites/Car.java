package com.sam.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.sam.game.HowlAway;
import com.sam.game.screens.Play;

import javax.xml.soap.Text;

import static com.sam.game.Constants.*;

public class Car extends Sprite{
    private Rectangle platformBounds;
    private TextureAtlas car;

    public Car(HowlAway game) {
        super(game);
        platformBounds = new Rectangle();
        car = game.assets.get("car.atlas", TextureAtlas.class);
    }

    public void init(float xPos, float yPos) {
        bounds.set(xPos, yPos, 5, 100); // tentive height
        platformBounds.set(xPos+5, yPos, 500, 100);
        animation = new Animation(0.2f, car.getRegions());
        alive = true;
    }


    public void update(float delta) {
        platformBounds.x -= (Play.ground_speed+175) * delta;
        bounds.x -= (Play.ground_speed+175) * delta;
    }

    public void dispose() {
        car.dispose();
    }

    public Rectangle getPlatformBounds() {
        return platformBounds;
    }
}