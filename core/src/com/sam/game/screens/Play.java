package com.sam.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.sam.game.HowlAway;
import com.sam.game.effects.BonePop;
import com.sam.game.effects.Effect;
import com.sam.game.sprites.Bone;
import com.sam.game.sprites.Car;
import com.sam.game.sprites.Civilian;
import com.sam.game.sprites.PowerUp;
import com.sam.game.sprites.Soldier;
import com.sam.game.sprites.Sprite;
import com.sam.game.sprites.Wolf;

import static com.sam.game.Constants.*;

public class Play implements Screen{

    private final HowlAway game;
    private OrthographicCamera camera;
    private Wolf wolf;
    private Texture background, ground;
    private Rectangle bg1Bound, bg2Bound, g1Bound, g2Bound;
    private Array<Sprite> sprites;
    private Array<Effect> effects;

    private final Pool<Bone> bonePool = new Pool<Bone>() {
        @Override
        protected Bone newObject() {
            return new Bone();
        }
    };
    private final Pool<PowerUp> powerUpPool = new Pool<PowerUp>() {
        @Override
        protected PowerUp newObject() {
            return new PowerUp();
        }
    };
    private final Pool<Car> carPool = new Pool<Car>() {
        @Override
        protected Car newObject() {
            return new Car();
        }
    };
    private final Pool<Civilian> civilianPool = new Pool<Civilian>() {
        @Override
        protected Civilian newObject() {
            return new Civilian();
        }
    };
    private final Pool<Soldier> soldierPool = new Pool<Soldier>() {
        @Override
        protected Soldier newObject() {
            return new Soldier();
        }
    };


    private final Pool<BonePop> bonePopPool = new Pool<BonePop>() {
        @Override
        protected BonePop newObject() {
            return new BonePop();
        }
    };

    private float elapsedTime;

    public Play(HowlAway game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_WIDTH*2, GAME_HEIGHT*2);

        wolf = new Wolf();
        wolf.init(100, 0);
        /*background = new Texture(Gdx.files.internal(""));
        ground = new Texture(Gdx.files.internal(""));

        bg1Bound = new Rectangle(0, 0, background.getWidth(), background.getHeight());
        bg2Bound = new Rectangle(background.getHeight(), 0,
                background.getWidth(), background.getHeight());
        g1Bound = new Rectangle(0, 0, ground.getWidth(), ground.getHeight());
        g2Bound = new Rectangle(ground.getWidth(), 0,
                ground.getWidth(), ground.getHeight());
*/
        sprites = new Array<Sprite>();
        effects = new Array<Effect>();



        sprites.add(wolf);

        elapsedTime = 0f;
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void show() {

    }

    public void handleInput() {
        if(Gdx.input.justTouched()) {
            wolf.jump();
        }
    }

    public void update(float delta) {
        handleInput();

        if((int)elapsedTime % 5 == 0) {
            Bone bone = bonePool.obtain();
            bone.init(MathUtils.random(5, camera.viewportWidth),
                    MathUtils.random(0, camera.viewportHeight),
                    MathUtils.randomBoolean());
            sprites.add(bone);
            System.out.println("spawn bone");
        }

        for(Sprite sprite : sprites) {

            sprite.update(delta);
            if(sprite.getBounds().x < -sprite.getBounds().getWidth()) {
                sprite.kill();
            }

            if(!sprite.isAlive()) {
                if(sprite instanceof Bone) {
                    sprites.removeValue(sprite, false);
                    bonePool.free((Bone) sprite);
                }
                if(sprite instanceof Car)
                    carPool.free((Car)sprite);
                if(sprite instanceof Civilian)
                    civilianPool.free((Civilian)sprite);
                if(sprite instanceof Soldier)
                    soldierPool.free((Soldier)sprite);
                if(sprite instanceof PowerUp)
                    powerUpPool.free((PowerUp)sprite);

                sprites.removeValue(sprite, false);
            }
        }

        for(Sprite sprite : sprites) {
            if(!sprite.equals(wolf)) {
                if(wolf.getBounds().overlaps(sprite.getBounds())) {
                    if(sprite instanceof Bone) {
                        BonePop bonepop = bonePopPool.obtain();
                        bonepop.init(sprite.getBounds().x, sprite.getBounds().y,
                                ((Bone) sprite).getGolden());
                        effects.add(bonepop);
                        sprite.kill();
                    }
                    else {
                        wolf.kill();
                        //game over
                    }
                }
            }
        }

        for(Effect effect : effects) {
            effect.update(delta);

            if(effect.getBounds().x < -effect.getBounds().width) {
                effects.removeValue(effect, false);
            }
        }

        /*bg1Bound.x -= GROUND_SPEED * delta;
        bg2Bound.x -= GROUND_SPEED * delta;
        g1Bound.x -= GROUND_SPEED * delta;
        g2Bound.x -= GROUND_SPEED * delta;

        if(bg1Bound.x < -bg1Bound.getWidth()) {
            bg1Bound.x = bg2Bound.getWidth();
        }
        if(bg2Bound.x < -bg2Bound.getWidth()) {
            bg2Bound.x = bg1Bound.getWidth();
        }
        if(g1Bound.x < -g1Bound.getWidth()) {
            g1Bound.x = g2Bound.getWidth();
        }
        if(g2Bound.x < g2Bound.getWidth()) {
            g2Bound.x = g1Bound.getWidth();
        }*/


    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        //game.batch.draw(background, bg1Bound.x, bg2Bound.y);
        //game.batch.draw(background, bg2Bound.x, bg2Bound.y);
        //game.batch.draw(ground, g1Bound.x, g1Bound.y);
        //game.batch.draw(ground, g2Bound.x, g2Bound.y);
        for(Sprite sprite : sprites) {
            game.batch.draw(sprite.getAnimation().getKeyFrame(elapsedTime, true),
                    sprite.getBounds().x,
                    sprite.getBounds().y);
        }
        for(Effect effect : effects) {
            game.batch.draw(effect.getAnimation().getKeyFrame(effect.getElapsedTime(), false),
                    effect.getBounds().x,
                    effect.getBounds().y);
        }
        game.batch.end();

        update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
    }
}
