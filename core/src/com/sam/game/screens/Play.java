package com.sam.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.sam.game.HowlAway;
import com.sam.game.effects.BonePop;
import com.sam.game.effects.CarDead;
import com.sam.game.effects.CarSplat;
import com.sam.game.effects.CatchDead;
import com.sam.game.effects.CatcherDead;
import com.sam.game.effects.Effect;
import com.sam.game.effects.LittleGirlDead;
import com.sam.game.sprites.Bone;
import com.sam.game.sprites.Car;
import com.sam.game.sprites.Civilian;
import com.sam.game.sprites.PowerUp;
import com.sam.game.sprites.Soldier;
import com.sam.game.sprites.Sprite;
import com.sam.game.sprites.Wolf;

import static com.sam.game.Constants.*;

public class Play implements Screen, GestureDetector.GestureListener, InputProcessor{

    private final HowlAway game;
    private OrthographicCamera camera;
    private GestureDetector gestureDetector;
    private Wolf wolf;
    private PowerUp powerUp;
    private Texture zoo2,zoo3, background, ground1, ground2, ground3, pausePanel, pauseBtn, resumeBtn,
        retryBtn, homeBtn, first, second, third, forth, fifth, sixth, seventh,
        gameoverPanel, icon, iconMagnet, iconMagnet2, iconFullMoon, iconFullMoon2,
        iconMultiplier, iconMultiplier2, iconFeather, iconFeather2, iconGoldenBones, iconGoldenBones2,
        iconShield, iconShield2, shopBtn;
    private Rectangle zoo2Bound,zoo3Bound, bg1Bound, bg2Bound, g1Bound, g2Bound, g3Bound,
            pausePanelBound, pauseBtnBound, resumeBtnBound, retryBtnBound, homeBtnBound,
            hole1, hole2, hole3, gameoverBound, shopBtnBound;
    private Array<Texture> scoreBoard;
    private Array<Sprite> sprites;
    private Array<Effect> effects;

    private final Pool<Bone> bonePool = new Pool<Bone>() {
        @Override
        protected Bone newObject() {
            return new Bone(game);
        }
    };
    private final Pool<Car> carPool = new Pool<Car>() {
        @Override
        protected Car newObject() {
            return new Car(game);
        }
    };
    private final Pool<Civilian> civilianPool = new Pool<Civilian>() {
        @Override
        protected Civilian newObject() {
            return new Civilian(game);
        }
    };
    private final Pool<Soldier> soldierPool = new Pool<Soldier>() {
        @Override
        protected Soldier newObject() {
            return new Soldier(game);
        }
    };


    private final Pool<BonePop> bonePopPool = new Pool<BonePop>() {
        @Override
        protected BonePop newObject() {
            return new BonePop(game);
        }
    };
    private final Pool<LittleGirlDead> littleGirlDeadPool = new Pool<LittleGirlDead>() {
        @Override
        protected LittleGirlDead newObject() {
            return new LittleGirlDead(game);
        }
    };
    private final Pool<CarSplat> carSplatPool = new Pool<CarSplat>() {
        @Override
        protected CarSplat newObject() {
            return new CarSplat(game);
        }
    };
    private final Pool<CarDead> carDeadPool = new Pool<CarDead>() {
        @Override
        protected CarDead newObject() {
            return new CarDead(game);
        }
    };
    private final Pool<CatcherDead> catcherDeadPool = new Pool<CatcherDead>() {
        @Override
        protected CatcherDead newObject() {
            return new CatcherDead(game);
        }
    };
    private final Pool<CatchDead> catchDeadPool = new Pool<CatchDead>() {
        @Override
        protected CatchDead newObject() {
            return new CatchDead(game);
        }
    };
    private Vector3 touch;
    private float elapsedTime, seconds, gravity, boneSpawn, powerUpSpawn, civilianSpawn,
            civilianSpawnRate, soldierSpawn, soldierSpawnRate, carSpawn,
            carSpawnRate, groundLevel;
    public static float ground_speed = 400;
    private int bonesCollected, runDistance, lastPowerUp;
    private boolean paused, gameover;

    public Play(HowlAway game) {
        this.game = game;
        ground_speed = 400f;
        gravity = -1f;
        InputMultiplexer multiplexer = new InputMultiplexer();
        gestureDetector = new GestureDetector(this);
        gestureDetector.setLongPressSeconds(0.2f);
        multiplexer.addProcessor(gestureDetector);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);

        wolf = new Wolf(game, game.getEquipped());
        wolf.init(20, 20);

        powerUp = new PowerUp(game);

        zoo2 = game.assets.get("zoo2.png", Texture.class);
        zoo3 = game.assets.get("zoo3.png", Texture.class);
        background = game.assets.get("city3.png", Texture.class);
        ground1 = game.assets.get("road_full.png", Texture.class);
        ground2 = ground1;
        ground3 = ground2;
        pauseBtn = game.assets.get("pause_btn.png", Texture.class);
        pausePanel = game.assets.get("pause.png", Texture.class);
        resumeBtn = game.assets.get("resume_btn.png", Texture.class);
        retryBtn = game.assets.get("retry_btn.png", Texture.class);
        homeBtn = game.assets.get("home_btn.png", Texture.class);
        shopBtn = game.assets.get("shopBtn.png", Texture.class);
        gameoverPanel = game.assets.get("gameover_panel.png", Texture.class);

        iconMagnet = game.assets.get("icon_magnet.png", Texture.class);
        iconMagnet2 = game.assets.get("icon_magnet2.png", Texture.class);
        iconFullMoon = game.assets.get("icon_fullmoon.png", Texture.class);
        iconFullMoon2 = game.assets.get("icon_fullmoon2.png", Texture.class);
        iconMultiplier = game.assets.get("icon_multiplier.png", Texture.class);
        iconMultiplier2 = game.assets.get("icon_multiplier2.png", Texture.class);
        iconFeather = game.assets.get("icon_feather.png", Texture.class);
        iconFeather2 = game.assets.get("icon_feather2.png", Texture.class);
        iconGoldenBones = game.assets.get("icon_goldenbones.png", Texture.class);
        iconGoldenBones2 = game.assets.get("icon_goldenbones2.png", Texture.class);
        iconShield = game.assets.get("icon_shield.png", Texture.class);
        iconShield2 = game.assets.get("icon_shield2.png", Texture.class);
        first = game.text0;
        second = game.text0;
        third = game.text0;
        forth = game.text0;
        fifth = game.text0;
        sixth = game.text0;
        seventh = game.text0;


        zoo2Bound = new Rectangle(0, 0, zoo2.getWidth(), zoo2.getHeight());
        zoo3Bound = new Rectangle(zoo2.getWidth(), 0, zoo3.getWidth(), zoo3.getHeight());
        bg1Bound = new Rectangle(0, 0, background.getWidth(), background.getHeight());
        bg2Bound = new Rectangle(background.getWidth(), 0,
                background.getWidth(), background.getHeight());

        g1Bound = new Rectangle(0, 0, ground1.getWidth(), ground1.getHeight());
        hole1 = new Rectangle(g1Bound.width, 0, 0, ground1.getHeight());
        g2Bound = new Rectangle(ground1.getWidth(), 0,
                ground1.getWidth(), ground1.getHeight());
        hole2 = new Rectangle(ground1.getWidth()*2, 0, 0, ground1.getHeight());
        g3Bound = new Rectangle(ground1.getWidth()*2, 0,
                ground1.getWidth(), ground1.getHeight());
        hole3 = new Rectangle(ground1.getWidth()*3, 0, 0, ground1.getHeight());



        pauseBtnBound = new Rectangle(camera.viewportWidth-pauseBtn.getWidth()-20,
                camera.viewportHeight-pauseBtn.getHeight()-20,
                pauseBtn.getWidth(), pauseBtn.getHeight());
        pausePanelBound = new Rectangle(camera.viewportWidth/2-pausePanel.getWidth()/2,
                camera.viewportHeight/2-pausePanel.getHeight()/2,
                pausePanel.getWidth(), pausePanel.getHeight());
        resumeBtnBound = new Rectangle(camera.viewportWidth/2-resumeBtn.getWidth()/2-100,
                camera.viewportHeight/2-resumeBtn.getHeight()/2-50,
                resumeBtn.getWidth(), resumeBtn.getHeight());
        retryBtnBound = new Rectangle(camera.viewportWidth/2-retryBtn.getWidth()/2,
                camera.viewportHeight/2-retryBtn.getHeight()/2-50,
                retryBtn.getWidth(), retryBtn.getHeight());
        homeBtnBound = new Rectangle(camera.viewportWidth/2-homeBtn.getWidth()/2+100,
                camera.viewportHeight/2-homeBtn.getHeight()/2-50,
                homeBtn.getWidth(), homeBtn.getWidth());
        shopBtnBound = new Rectangle(camera.viewportWidth/2-shopBtn.getWidth()/2,
                camera.viewportHeight/2-shopBtn.getHeight()/2-120, shopBtn.getWidth(),
                shopBtn.getHeight());
        gameoverBound = new Rectangle(camera.viewportWidth/2-gameoverPanel.getWidth()/2,
                camera.viewportHeight/2-gameoverPanel.getHeight()/2,
                gameoverPanel.getWidth(), gameoverPanel.getHeight());


        sprites = new Array<Sprite>();
        effects = new Array<Effect>();
        scoreBoard = new Array<Texture>();
        scoreBoard.add(seventh);
        scoreBoard.add(sixth);
        scoreBoard.add(fifth);
        scoreBoard.add(forth);
        scoreBoard.add(third);
        scoreBoard.add(second);
        scoreBoard.add(first);


        touch = new Vector3();
        elapsedTime = 0f;
        seconds = 0f;
        boneSpawn = 0f;
        powerUpSpawn = 0f;
        civilianSpawn = 0f;
        civilianSpawnRate = 0f;
        soldierSpawn = 0f;
        soldierSpawnRate = 0f;
        carSpawn = 0f;
        carSpawnRate = 5f;
        groundLevel = 20f;
        bonesCollected = 0;
        runDistance = 0;
        lastPowerUp = 0;
        paused = false;
        gameover = false;
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void show() {

    }

    public void spawnBones() {
        int formation = MathUtils.random(1, 3);

        switch(formation) {
            case 1:
                for(int j=0; j<2; j++) {
                    for(int i=0; i<8; i++) {
                        Bone bone = bonePool.obtain();
                        bone.init(camera.viewportWidth + bone.getBounds().width*j,
                                camera.viewportHeight*i/8,
                                wolf.getState() == GOLDEN_BONES);
                        sprites.add(bone);
                    }
                }
                break;
            case 2:
                float height = MathUtils.random(64, camera.viewportHeight-64);
                for(int j=0; j<2; j++) {
                    for(int i=0; i<8; i++) {
                        Bone bone = bonePool.obtain();
                        bone.init(camera.viewportWidth+(bone.getBounds().width*i),
                                height-(64*j),
                                wolf.getState() == GOLDEN_BONES);
                        sprites.add(bone);
                    }
                }
                break;
            case 3:
                for(int i=0; i<8; i++) {
                    Bone bone = bonePool.obtain();
                    bone.init(camera.viewportWidth+(bone.getBounds().width*i),
                            camera.viewportHeight-bone.getBounds().height,
                            wolf.getState() == GOLDEN_BONES);
                    sprites.add(bone);
                }
                for(int i=0; i<8; i++) {
                    Bone bone = bonePool.obtain();
                    bone.init(camera.viewportWidth+(bone.getBounds().width*i),
                            0,
                            wolf.getState() == GOLDEN_BONES);
                    sprites.add(bone);
                }

                break;
        }

    }

    public void changeScore() {
        int divider = 1000000;
        int tempDistance = runDistance;
        for(int i=0; i<scoreBoard.size; i++) {
            int count = tempDistance/divider;
            tempDistance -= count * divider;
            divider = divider/10;
            switch(count) {
                case 0:
                    scoreBoard.set(i, game.text0);
                    break;
                case 1:
                    scoreBoard.set(i, game.text1);
                    break;
                case 2:
                    scoreBoard.set(i, game.text2);
                    break;
                case 3:
                    scoreBoard.set(i, game.text3);
                    break;
                case 4:
                    scoreBoard.set(i, game.text4);
                    break;
                case 5:
                    scoreBoard.set(i, game.text5);
                    break;
                case 6:
                    scoreBoard.set(i, game.text6);
                    break;
                case 7:
                    scoreBoard.set(i, game.text7);
                    break;
                case 8:
                    scoreBoard.set(i, game.text8);
                    break;
                case 9:
                    scoreBoard.set(i, game.text9);
                    break;
            }
        }
    }

    public void update(float delta) {
        if(!paused || !gameover) {
            if(wolf.getState() == FULL_MOON) {
                background = game.assets.get("werewolf_bg.png");
            } else {
                background = game.assets.get("city3.png");
            }
            ground_speed += delta * 4;
            seconds += delta;
            boneSpawn += delta;
            powerUpSpawn += delta;
            civilianSpawn += delta;
            soldierSpawn += delta;
            carSpawn += delta;
            if(gameover) {
                if(ground_speed > 0) {
                    ground_speed -= ground_speed/30;
                    if(ground_speed < 10) {
                        ground_speed = 0;
                    }
                } else {
                    // switch screen
                }
            }
            if(seconds > 0.1f-(ground_speed-400)/10000) {
                seconds = 0;
                if(!gameover) {
                    if(wolf.getState() == MULTIPLIER) {
                        runDistance += 2;
                    }
                    else {
                        runDistance += 1;
                    }
                }
            }
            if(boneSpawn > 5f-(ground_speed-400)/1000) {
                spawnBones();
                boneSpawn = 0f;
            }
            if(powerUpSpawn > 4f) {
                int whichPU;
                do {
                    whichPU = MathUtils.random(1, 7);
                } while(whichPU == lastPowerUp);
                lastPowerUp = whichPU;
                powerUp.init(camera.viewportWidth, MathUtils.random(100, camera.viewportHeight-80), whichPU);
                powerUpSpawn = 0f;
            }
            if(civilianSpawn > civilianSpawnRate) {
                Civilian civilian = civilianPool.obtain();
                civilian.init(camera.viewportWidth, MathUtils.random(0, 50));
                if(civilian.getBounds().overlaps(hole1) ||
                        civilian.getBounds().overlaps(hole2) ||
                        civilian.getBounds().overlaps(hole3)) {
                    civilian.kill();
                } else {
                    sprites.add(civilian);
                }
                civilianSpawn = 0f;
                civilianSpawnRate = MathUtils.random(1, 4);
            }
            if(soldierSpawn > soldierSpawnRate) {
                if(hole1.contains(camera.viewportWidth, 0) ||
                        hole2.contains(camera.viewportWidth, 0) ||
                        hole3.contains(camera.viewportWidth, 0)) {
                    soldierSpawn = 0;
                    soldierSpawnRate = 2.5f;
                    System.out.println("could not spawn");
                } else {
                    Soldier soldier = soldierPool.obtain();
                    soldier.init(camera.viewportWidth, MathUtils.random(0, 50));
                    if(soldier.getBounds().overlaps(hole1) ||
                            soldier.getBounds().overlaps(hole2) ||
                            soldier.getBounds().overlaps(hole3)) {
                        soldier.kill();
                    } else {
                        sprites.add(soldier);
                    }
                    soldierSpawn = 0f;
                    soldierSpawnRate = MathUtils.random(4, 7);
                }
            }
            if(carSpawn > carSpawnRate) {
                Car car = carPool.obtain();
                car.init(camera.viewportWidth, 0);
                if(car.getBounds().overlaps(hole1) ||
                        car.getBounds().overlaps(hole2) ||
                        car.getBounds().overlaps(hole3)) {
                    car.kill();
                } else {
                    sprites.add(car);
                }
                carSpawn = 0f;
                carSpawnRate = MathUtils.random(7, 9);
            }
            changeScore();
            int currState = wolf.getState();
            wolf.update(delta, gravity, groundLevel);
            if(currState == FULL_MOON && wolf.getState() == NONE) {
                ground_speed -= 200f;
                wolf.getBounds().set(wolf.getBounds().x, wolf.getBounds().y,
                        96, 64);
            }
            System.out.println(wolf.getBounds().y);
            if(((wolf.getBounds().overlaps(hole1) && hole1.width > 0)||
                    (wolf.getBounds().overlaps(hole2) && hole2.width > 0) ||
                    (wolf.getBounds().overlaps(hole3) && hole3.width > 0)) &&
                    !(wolf.getBounds().overlaps(g1Bound) ||
                    wolf.getBounds().overlaps(g2Bound) ||
                            wolf.getBounds().overlaps(g3Bound)) &&
                    wolf.getBounds().y <= 20) {
                wolf.setFalling(true);
                if(wolf.getState() != HERMES) {
                    gameover = true;
                }
                //game over
            }
            if (wolf.getBounds().overlaps(g1Bound) ||
                    wolf.getBounds().overlaps(g2Bound) ||
                    wolf.getBounds().overlaps(g3Bound) && gameover == false) {
                wolf.setFalling(false);
            }
            for(Sprite sprite : sprites) {
                if(sprite instanceof Car) {
                    if(wolf.getState() == FULL_MOON && wolf.getBounds().overlaps(((Car) sprite).getPlatformBounds())) {
                        if(wolf.getState() == MULTIPLIER) {
                            runDistance += 100;
                        } else {
                            runDistance += 50;
                        }
                        sprite.kill();
                        CarDead carDead = carDeadPool.obtain();
                        carDead.init(sprite.getBounds().x, sprite.getBounds().y);
                        effects.add(carDead);
                        game.assets.get("sounds/car_explosion.mp3", Sound.class).play(0.15f);
                    }
                    else if(((Car)sprite).getPlatformBounds().x <= wolf.getBounds().x+wolf.getBounds().width
                            && ((Car)sprite).getPlatformBounds().x + ((Car)sprite).getPlatformBounds().width
                            >= wolf.getBounds().x) {
                        groundLevel = ((Car)sprite).getPlatformBounds().height-1;
                    } else {
                        groundLevel = 20;
                    }
                }

                if(sprite instanceof Soldier) {
                    ((Soldier) sprite).update(delta, sprite.getBounds().x-wolf.getBounds().x+wolf.getBounds().width < 400);
                    if (((sprite.getBounds().overlaps(hole1) && hole1.width > 0)||
                            (sprite.getBounds().overlaps(hole2) && hole2.width > 0) ||
                            (sprite.getBounds().overlaps(hole3) && hole3.width > 0)) &&
                            !(sprite.getBounds().overlaps(g1Bound) ||
                                    sprite.getBounds().overlaps(g2Bound) ||
                                    sprite.getBounds().overlaps(g3Bound))) {
                        ((Soldier) sprite).setFalling(true);
                    }
                } else {
                    sprite.update(delta);
                }
                if(sprite instanceof Bone) {
                    if(wolf.getState() == MAGNET) {
                        double distance = Math.sqrt(Math.pow(wolf.getCenter().x - ((Bone) sprite).getCenter().x,2)
                                + Math.pow(wolf.getCenter().y - ((Bone) sprite).getCenter().y,2));
                        if(distance < 300) {
                            double x = wolf.getBounds().x + wolf.getBounds().width - ((Bone) sprite).getCenter().x;
                            double y = wolf.getCenter().y - ((Bone) sprite).getCenter().y;
                            double angle = Math.atan(y/x);
                            float deltaX = (float)Math.cos(angle)* ground_speed*5/4;
                            float deltaY = (float)Math.sin(angle)* ground_speed*5/4;
                            if(x > 0) {
                                ((Bone) sprite).setVelocity(-deltaX, -deltaY);
                            }
                            else if(x < 0) {
                                ((Bone) sprite).setVelocity(deltaX, deltaY);
                            }
                        }
                    }
                }
                if(sprite instanceof Civilian && !((Civilian) sprite).isFalling()) {
                    if (((sprite.getBounds().overlaps(hole1) && hole1.width > 0)||
                            (sprite.getBounds().overlaps(hole2) && hole2.width > 0) ||
                            (sprite.getBounds().overlaps(hole3) && hole3.width > 0)) &&
                            !(sprite.getBounds().overlaps(g1Bound) ||
                                    sprite.getBounds().overlaps(g2Bound) ||
                                    sprite.getBounds().overlaps(g3Bound))) {
                        ((Civilian) sprite).setFalling(true);
                        game.assets.get("sounds/girl_scream.mp3", Sound.class).play(
                                0.15f, MathUtils.random(0.9f, 1.1f), 0);

                    }
                }
                if(wolf.getBounds().overlaps(sprite.getBounds())) {
                    if(sprite instanceof Bone) {
                        BonePop bonepop = bonePopPool.obtain();
                        bonepop.init(sprite.getBounds().x, sprite.getBounds().y,
                                ((Bone) sprite).getGolden());
                        effects.add(bonepop);
                        sprite.kill();
                        if(((Bone) sprite).getGolden()) {
                            bonesCollected += 2;
                        } else {
                            bonesCollected += 1;
                        }
                    }
                    else if(sprite instanceof Civilian) {
                        sprite.kill();
                        //playBiteSound();
                        game.assets.get("sounds/girl_scream.mp3", Sound.class).play(
                                0.15f, MathUtils.random(0.9f, 1.1f), 0);
                        Bone bone = bonePool.obtain();
                        bone.init(sprite.getBounds().x + sprite.getBounds().width*1/2,
                                sprite.getBounds().y,
                                wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                        Bone bone1 = bonePool.obtain();
                        bone1.init(sprite.getBounds().x + sprite.getBounds().width * 1 / 2,
                                sprite.getBounds().y + sprite.getBounds().height / 3,
                                wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                        Bone bone2 = bonePool.obtain();
                        bone2.init(sprite.getBounds().x + sprite.getBounds().width * 1 / 2,
                                sprite.getBounds().y + sprite.getBounds().height / 3 * 2,
                                wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                        sprites.add(bone);
                        sprites.add(bone1);
                        sprites.add(bone2);
                        LittleGirlDead littleGirlDead = littleGirlDeadPool.obtain();
                        littleGirlDead.init(sprite.getBounds().x, sprite.getBounds().y);
                        effects.add(littleGirlDead);
                        if(wolf.getState() == MULTIPLIER) {
                            runDistance += 40;
                        } else {
                            runDistance += 20;
                        }
                    }
                    else if(wolf.getState() == FULL_MOON || wolf.getState() == SHIELD) {
                        if(sprite instanceof Soldier) {
                            sprite.kill();
                            game.assets.get("sounds/man_death.wav", Sound.class).play();
                            //playBiteSound();
                            Bone bone = bonePool.obtain();
                            bone.init(sprite.getBounds().x + sprite.getBounds().width*1/2,
                                    sprite.getBounds().y,
                                    wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                            Bone bone1 = bonePool.obtain();
                            bone1.init(sprite.getBounds().x + sprite.getBounds().width * 1 / 2,
                                    sprite.getBounds().y + sprite.getBounds().height / 3,
                                    wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                            Bone bone2 = bonePool.obtain();
                            bone2.init(sprite.getBounds().x + sprite.getBounds().width * 1 / 2,
                                    sprite.getBounds().y + sprite.getBounds().height / 3 * 2,
                                    wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                            sprites.add(bone);
                            sprites.add(bone1);
                            sprites.add(bone2);

                            CatcherDead catcherDead = catcherDeadPool.obtain();
                            catcherDead.init(sprite.getBounds().x, sprite.getBounds().y);
                            effects.add(catcherDead);

                            if(wolf.getState() == MULTIPLIER) {
                                runDistance += 80;
                            } else {
                                runDistance += 40;
                            }
                        }
                        else if(sprite instanceof Car) {
                            sprite.kill();
                            //playBiteSound();
                            CarDead carDead = carDeadPool.obtain();
                            carDead.init(sprite.getBounds().x, sprite.getBounds().y);
                            effects.add(carDead);
                            if(wolf.getState() == MULTIPLIER) {
                                runDistance += 160;
                            } else {
                                runDistance += 80;
                            }
                            game.assets.get("sounds/car_explosion.mp3", Sound.class).play(0.15f);
                        }
                        if(wolf.getState() == SHIELD) {
                            wolf.setState(NONE);
                        }
                    }
                    else if(sprite instanceof Car) {
                        gameover = true;
                        wolf.kill();
                        wolf.getBounds().set(-camera.viewportWidth, camera.viewportHeight, 0, 0);
                        game.assets.get("sounds/splat.wav", Sound.class).play();
                        game.assets.get("sounds/tires.wav", Sound.class).play(0.75f);
                        CarSplat carSplat = carSplatPool.obtain();
                        carSplat.init(sprite.getBounds().x, sprite.getBounds().y);
                        effects.add(carSplat);
                        sprite.kill();
                    }
                    else {
                        game.assets.get("sounds/whine.mp3", Sound.class).play();
                        wolf.kill();
                        wolf.getBounds().set(-camera.viewportWidth, camera.viewportHeight, 0, 0);
                        sprite.kill();
                        CatchDead catchDead = catchDeadPool.obtain();
                        catchDead.init(sprite.getBounds().x, sprite.getBounds().y);
                        effects.add(catchDead);
                        gameover = true;
                        //switch screen here
                    }
                }
                if(sprite instanceof Car) {
                    if(((Car) sprite).getPlatformBounds().x < -((Car) sprite).getPlatformBounds().width) {
                        sprite.kill();
                    }
                } else {
                    if(sprite.getBounds().x < -sprite.getBounds().getWidth()) {
                        sprite.kill();
                    }
                }

                if(!sprite.isAlive()) {
                    if(sprite instanceof Bone)
                        bonePool.free((Bone) sprite);
                    if(sprite instanceof Car)
                        carPool.free((Car)sprite);
                    if(sprite instanceof Civilian)
                        civilianPool.free((Civilian)sprite);
                    if(sprite instanceof Soldier)
                        soldierPool.free((Soldier)sprite);
                    sprites.removeValue(sprite, false);
                }
            }

            if(powerUp.isAlive()) {
                powerUp.update(delta);

                if(wolf.getBounds().overlaps(powerUp.getBounds())) {

                    switch(powerUp.getType()) {

                        case MAGNET:
                            game.assets.get("sounds/magnet.wav", Sound.class).play();
                            if(wolf.getState() == FULL_MOON) {
                                ground_speed -= 200;
                                wolf.getBounds().set(wolf.getBounds().x, wolf.getBounds().y,
                                        96, 110);
                            }
                            wolf.setState(MAGNET);
                            wolf.setTimer(10f);
                            break;

                        case FULL_MOON:
                            game.assets.get("sounds/fullmoon_sound.wav", Sound.class).play();
                            if(wolf.getState() != FULL_MOON) {
                                ground_speed += 200;
                            }
                            wolf.getBounds().set(wolf.getPosition().x + 100, wolf.getPosition().y,
                                    200, 160);
                            wolf.setState(FULL_MOON);
                            wolf.setTimer(10f);
                            break;

                        case SHIELD:
                            game.assets.get("sounds/shield.wav", Sound.class).play();
                            if(wolf.getState() == FULL_MOON) {
                                ground_speed -= 200;
                                wolf.getBounds().set(wolf.getBounds().x, wolf.getBounds().y,
                                        96, 110);
                            }
                            wolf.setState(SHIELD);
                            wolf.setTimer(10f);
                            break;


                        case WOLF_PACK:
                            game.assets.get("sounds/wolfpack.wav", Sound.class).play();
                            for(Sprite sprite : sprites) {
                                if(!(sprite instanceof Bone)) {
                                    if(sprite instanceof Civilian) {
                                        LittleGirlDead littleGirlDead = littleGirlDeadPool.obtain();
                                        littleGirlDead.init(sprite.getBounds().x, sprite.getBounds().y);
                                        effects.add(littleGirlDead);
                                        if(wolf.getState() == MULTIPLIER) {
                                            runDistance += 40;
                                        } else {
                                            runDistance += 20;
                                        }
                                        game.assets.get("sounds/girl_scream.mp3", Sound.class).play(
                                                0.15f, MathUtils.random(0.9f, 1.1f), 0);
                                    }
                                    if(sprite instanceof Car) {
                                        if(wolf.getState() == MULTIPLIER) {
                                            runDistance += 100;
                                        } else {
                                            runDistance += 50;
                                        }
                                        CarDead carDead = carDeadPool.obtain();
                                        carDead.init(sprite.getBounds().x, sprite.getBounds().y);
                                        effects.add(carDead);
                                        game.assets.get("sounds/car_explosion.mp3", Sound.class).play(0.15f);
                                    }
                                    if(sprite instanceof Soldier) {
                                        CatcherDead catcherDead = catcherDeadPool.obtain();
                                        catcherDead.init(sprite.getBounds().x, sprite.getBounds().y);
                                        effects.add(catcherDead);
                                        if(wolf.getState() == MULTIPLIER) {
                                            runDistance += 50;
                                        } else {
                                            runDistance += 30;
                                        }
                                        game.assets.get("sounds/man_death.wav", Sound.class).play();
                                    }

                                    Bone bone = bonePool.obtain();
                                    bone.init(sprite.getBounds().x + sprite.getBounds().width*1/2,
                                            sprite.getBounds().y,
                                            wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                                    Bone bone1 = bonePool.obtain();
                                    bone1.init(sprite.getBounds().x + sprite.getBounds().width * 1 / 2,
                                            sprite.getBounds().y + sprite.getBounds().height / 3,
                                            wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                                    Bone bone2 = bonePool.obtain();
                                    bone2.init(sprite.getBounds().x + sprite.getBounds().width * 1 / 2,
                                            sprite.getBounds().y + sprite.getBounds().height / 3 * 2,
                                            wolf.getState() == GOLDEN_BONES && wolf.getTimer() > 0);
                                    sprites.add(bone);
                                    sprites.add(bone1);
                                    sprites.add(bone2);
                                    sprite.kill();
                                }
                            }

                            break;

                        case GOLDEN_BONES:
                            game.assets.get("sounds/cash.mp3", Sound.class).play();
                            if(wolf.getState() == FULL_MOON) {
                                ground_speed -= 200;
                                wolf.getBounds().set(wolf.getBounds().x, wolf.getBounds().y,
                                        96, 110);
                            }
                            wolf.setState(GOLDEN_BONES);
                            wolf.setTimer(10f);
                            break;

                        case HERMES:
                            game.assets.get("sounds/wings.wav", Sound.class).play();
                            if(wolf.getState() == FULL_MOON) {
                                ground_speed -= 200;
                                wolf.getBounds().set(wolf.getBounds().x, wolf.getBounds().y,
                                        96, 110);
                            }
                            wolf.setState(HERMES);
                            wolf.setTimer(10f);
                            break;

                        case MULTIPLIER:
                            if(wolf.getState() == FULL_MOON) {
                                ground_speed -= 200;
                                wolf.getBounds().set(wolf.getBounds().x, wolf.getBounds().y,
                                        96, 110);
                            }
                            wolf.setState(MULTIPLIER);
                            wolf.setTimer(10f);
                            break;
                    }
                    powerUp.kill();
                }

            }
            boolean hasCar = false;
            for(Sprite sprite : sprites) {
                if(sprite instanceof Car) {
                    hasCar = true;
                }
            }
            if(!hasCar) {
                groundLevel = 20;
            }




            for(Effect effect : effects) {
                effect.update(delta);

                if(effect.getBounds().x < -effect.getBounds().width) {
                    effects.removeValue(effect, false);
                }
            }
            zoo2Bound.x -= ground_speed * delta;
            zoo3Bound.x -= ground_speed * delta;
            bg1Bound.x -= ground_speed * delta * 0.1f;
            bg2Bound.x -= ground_speed * delta * 0.1f;
            g1Bound.x -= ground_speed * delta;
            hole1.x -= ground_speed * delta;
            g2Bound.x -= ground_speed * delta;
            hole2.x -= ground_speed * delta;
            g3Bound.x -= ground_speed * delta;
            hole3.x -= ground_speed * delta;

            if(bg1Bound.x < -bg1Bound.getWidth()) {
                bg1Bound.x = bg2Bound.x + bg2Bound.getWidth();
            }
            if(bg2Bound.x < -bg2Bound.getWidth()) {
                bg2Bound.x = bg1Bound.x + bg1Bound.getWidth();
            }

            if(hole1.x < -hole1.getWidth()-100) {
                if(MathUtils.randomBoolean()) {
                    if(wolf.getState() == FULL_MOON) {
                        hole1.setWidth(MathUtils.random(500, 800));
                    }
                    else {
                        hole1.setWidth(MathUtils.random(200,600));
                    }
                }
                else {
                    hole1.setWidth(0);
                }
                hole1.x = g1Bound.x + g1Bound.getWidth();
            }
            if(hole2.x < -hole2.getWidth()-100) {
                if(MathUtils.randomBoolean()) {
                    if(wolf.getState() == FULL_MOON) {
                        hole2.setWidth(MathUtils.random(500, 800));
                    }
                    else {
                        hole2.setWidth(MathUtils.random(200,600));
                    }
                }
                else {
                    hole2.setWidth(0);
                }
                hole2.x = g2Bound.x + g2Bound.getWidth();
            }
            if(hole3.x < -hole3.getWidth()-100) {
                if(MathUtils.randomBoolean()) {
                    if(wolf.getState() == FULL_MOON) {
                        hole3.setWidth(MathUtils.random(500, 800));
                    }
                    else {
                        hole3.setWidth(MathUtils.random(200,600));
                    }
                }
                else {
                    hole3.setWidth(0);
                }
                hole3.x = g3Bound.x + g3Bound.getWidth();
            }
            if(g1Bound.x < -g1Bound.getWidth()-100) {
                g1Bound.x = hole3.x + hole3.getWidth();
            }
            if(g2Bound.x < -g2Bound.getWidth()-100) {
                g2Bound.x = hole1.x + hole1.getWidth();
            }
            if(g3Bound.x < -g3Bound.getWidth()-100) {
                g3Bound.x = hole2.x + hole2.getWidth();
            }

            ground1 = game.assets.get("road_full.png", Texture.class);
            ground2 = ground1;
            ground3 = ground2;
            g1Bound.width = ground1.getWidth();
            g2Bound.width = ground1.getWidth();
            g2Bound.width = ground1.getWidth();

            if(hole1.getWidth() > 0) {
                ground1 = game.assets.get("road_left.png", Texture.class);
                g1Bound.width = ground1.getWidth();
                ground2 = game.assets.get("road_right.png", Texture.class);
                g2Bound.width = ground2.getWidth();
            }
            if(hole2.getWidth() > 0) {
                ground2 = game.assets.get("road_left.png", Texture.class);
                g2Bound.width = ground2.getWidth();
                ground3 = game.assets.get("road_right.png", Texture.class);
                g3Bound.width = ground3.getWidth();
            }
            if(hole3.getWidth() > 0) {
                ground3 = game.assets.get("road_left.png", Texture.class);
                g3Bound.width = ground3.getWidth();
                ground1 = game.assets.get("road_right.png", Texture.class);
                g1Bound.width = ground1.getWidth();
            }
            if(hole3.getWidth() > 0 && hole1.getWidth() > 0) {
                ground1 = game.assets.get("road_both.png", Texture.class);
                g1Bound.width = ground1.getWidth();
            }
            if(hole1.getWidth() > 0 && hole2.getWidth() > 0) {
                ground2 = game.assets.get("road_both.png", Texture.class);
                g2Bound.width = ground2.getWidth();
            }
            if(hole2.getWidth() > 0 && hole3.getWidth() > 0) {
                ground3 = game.assets.get("road_both.png", Texture.class);
                g3Bound.width = ground3.getWidth();
            }

        }


    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        if(!paused && ground_speed > 0) {
            elapsedTime += delta;
            update(delta);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, bg1Bound.x, bg2Bound.y);
        game.batch.draw(background, bg2Bound.x, bg2Bound.y);
        int i = 0;
        for(Texture score : scoreBoard) {
                game.batch.draw(score, score.getWidth()*i+20, camera.viewportHeight-score.getHeight()-15);
                i++;
        }

        game.batch.draw(ground1, g1Bound.x, g1Bound.y);
        game.batch.draw(ground2, g2Bound.x, g2Bound.y);
        game.batch.draw(ground3, g3Bound.x, g3Bound.y);

        /*game.batch.draw(retryBtn, wolf.getBounds().x, wolf.getBounds().y);
        game.batch.draw(retryBtn, wolf.getBounds().x+wolf.getBounds().getWidth()-retryBtn.getWidth(),
                wolf.getBounds().y);
        game.batch.draw(retryBtn, wolf.getBounds().x, wolf.getBounds().y+wolf.getBounds().height-
            retryBtn.getHeight());
        game.batch.draw(retryBtn, wolf.getBounds().x + wolf.getBounds().getWidth() - retryBtn.getWidth(),
                wolf.getBounds().y + wolf.getBounds().getHeight() - retryBtn.getHeight());*/

        for(Sprite sprite : sprites) {
            if(sprite instanceof Civilian) {
                game.batch.draw(sprite.getAnimation().getKeyFrame(((Civilian) sprite).getElapsedTime(), true),
                        sprite.getBounds().x,
                        sprite.getBounds().y);
            }
            else if(sprite instanceof Soldier) {
                game.batch.draw(sprite.getAnimation().getKeyFrame(((Soldier) sprite).getElapsedTime(), false),
                        sprite.getBounds().x-160,
                        sprite.getBounds().y);
            }
            else {
                game.batch.draw(sprite.getAnimation().getKeyFrame(elapsedTime, true),
                        sprite.getBounds().x,
                        sprite.getBounds().y);
            }
        }


        for(Effect effect : effects) {
            game.batch.draw(effect.getAnimation().getKeyFrame(effect.getElapsedTime(), false),
                    effect.getBounds().x,
                    effect.getBounds().y);
        }
        if(zoo3Bound.x > -zoo3Bound.getWidth()) {
            game.batch.draw(zoo3, zoo3Bound.x, zoo3Bound.y);
        }
        if(wolf.isAlive()) {
            if(wolf.getState() == HERMES && game.getEquipped() != 0) {
                game.batch.draw(wolf.getAnimation().getKeyFrame(elapsedTime, true),
                        wolf.getBounds().x-90, wolf.getBounds().y-55);
            }
            else {
                game.batch.draw(wolf.getAnimation().getKeyFrame(elapsedTime, true),
                        wolf.getBounds().x-90, wolf.getBounds().y);
            }
        }
        if(zoo2Bound.x > -zoo2Bound.getWidth()) {
            game.batch.draw(zoo2, zoo2Bound.x, zoo2Bound.y);
        }

        if(powerUp.isAlive()) {
            game.batch.draw(powerUp.getTexture(), powerUp.getBounds().x, powerUp.getBounds().y);
        }

        game.batch.draw(pauseBtn, pauseBtnBound.x, pauseBtnBound.y);

        if(ground_speed == 0) {
            game.batch.draw(gameoverPanel, gameoverBound.x, gameoverBound.y);
            game.batch.draw(shopBtn, shopBtnBound.x, shopBtnBound.y);
            i = 0;
            for(Texture score : scoreBoard) {
                game.batch.draw(score,  GAME_WIDTH/3+score.getWidth()*i,
                        camera.viewportHeight/2);
                i++;
            }
            resumeBtnBound.set(camera.viewportWidth/2-resumeBtn.getWidth()/2-120,
                    camera.viewportHeight/2-resumeBtn.getHeight()/2-120, resumeBtn.getWidth(),
                    resumeBtn.getHeight());
            game.batch.draw(resumeBtn, resumeBtnBound.x, resumeBtnBound.y);

            homeBtnBound.set(camera.viewportWidth/2-homeBtn.getWidth()/2+120,
                    camera.viewportHeight/2-homeBtn.getHeight()/2-120, homeBtn.getWidth(),
                    homeBtn.getHeight());
            game.batch.draw(homeBtn, homeBtnBound.x, homeBtnBound.y);
        }
        else if(paused) {
            game.batch.draw(pausePanel, pausePanelBound.x, pausePanelBound.y);
            game.batch.draw(resumeBtn, resumeBtnBound.x, resumeBtnBound.y);
            game.batch.draw(retryBtn, retryBtnBound.x, retryBtnBound.y);
            game.batch.draw(homeBtn, homeBtnBound.x, homeBtnBound.y);
        }

        if(wolf.getState() != NONE && wolf.getState() != WOLF_PACK) {
            switch(wolf.getState()) {
                case FULL_MOON:
                    if(wolf.getTimer() > 3)
                        icon = iconFullMoon;
                    else if(wolf.getTimer() > 2)
                        icon = iconFullMoon2;
                    else if(wolf.getTimer() > 1)
                        icon = iconFullMoon;
                    else
                        icon = iconFullMoon2;
                    break;
                case HERMES:
                    if(wolf.getTimer() > 3)
                        icon = iconFeather;
                    else if(wolf.getTimer() > 2)
                        icon = iconFeather2;
                    else if(wolf.getTimer() > 1)
                        icon = iconFeather;
                    else
                        icon = iconFeather2;
                    break;
                case MULTIPLIER:
                    if(wolf.getTimer() > 3)
                        icon = iconMultiplier;
                    else if(wolf.getTimer() > 2)
                        icon = iconMultiplier2;
                    else if(wolf.getTimer() > 1)
                        icon = iconMultiplier;
                    else
                        icon = iconMultiplier2;
                    break;
                case MAGNET:
                    if(wolf.getTimer() > 3)
                        icon = iconMagnet;
                    else if(wolf.getTimer() > 2)
                        icon = iconMagnet2;
                    else if(wolf.getTimer() > 1)
                        icon = iconMagnet;
                    else
                        icon = iconMagnet2;
                    break;

                case GOLDEN_BONES:
                    if(wolf.getTimer() > 3)
                        icon = iconGoldenBones;
                    else if(wolf.getTimer() > 2)
                        icon = iconGoldenBones2;
                    else if(wolf.getTimer() > 1)
                        icon = iconGoldenBones;
                    else
                        icon = iconGoldenBones2;
                    break;

                case SHIELD:
                    if(wolf.getTimer() > 3)
                        icon = iconShield;
                    else if(wolf.getTimer() > 2)
                        icon = iconShield2;
                    else if(wolf.getTimer() > 1)
                        icon = iconShield;
                    else
                        icon = iconShield2;
                    break;

                default:
                icon = iconMagnet;
                break;

            }

            game.batch.draw(icon, camera.viewportWidth-140, camera.viewportHeight-66);
        }

        game.batch.end();




    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        paused = true;
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
        ground1.dispose();
        ground2.dispose();
        ground3.dispose();
        pausePanel.dispose();
        pauseBtn.dispose();
        resumeBtn.dispose();
        retryBtn.dispose();
        homeBtn.dispose();
        powerUp.dispose();
        wolf.dispose();
        zoo2.dispose();
        zoo3.dispose();
        for(Sprite sprite : sprites) {
            sprite.dispose();
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if(ground_speed == 0) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if(resumeBtnBound.contains(touch.x, touch.y)){
                game.incrementBones(bonesCollected);
                if(game.getTopScore() < runDistance) {
                    game.setTopScore(runDistance);
                }
                game.setScreen(new Play(game));
            }
            if(shopBtnBound.contains(touch.x, touch.y)){
                shopBtnBound.set(0, 0, 0, 0);
                homeBtnBound.set(0, 0, 0, 0);
                resumeBtnBound.set(0, 0, 0, 0);
                game.incrementBones(bonesCollected);
                if(game.getTopScore() < runDistance) {
                    game.setTopScore(runDistance);
                }
                game.setScreen(new Shop(game));
            }
            if(homeBtnBound.contains(touch.x, touch.y)){
                game.incrementBones(bonesCollected);
                if(game.getTopScore() < runDistance) {
                    game.setTopScore(runDistance);
                }
                game.setScreen(new Menu(game));
            }
        }
        else if(paused){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if(resumeBtnBound.contains(touch.x, touch.y)) {
                paused = false;
            }
            if(retryBtnBound.contains(touch.x, touch.y)) {
                game.incrementBones(bonesCollected);
                if(game.getTopScore() < runDistance) {
                    game.setTopScore(runDistance);
                }
                game.saveRecord();
                game.setScreen(new Play(game));
            }
            if(homeBtnBound.contains(touch.x, touch.y)) {
                game.incrementBones(bonesCollected);
                if(game.getTopScore() < runDistance) {
                    game.setTopScore(runDistance);
                }
                game.saveRecord();
                game.setScreen(new Menu(game));
            }

        }
        else {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if(pauseBtnBound.contains(touch.x, touch.y)) {
                paused = !paused;
            }
            else if(!gameover && elapsedTime > 1f) {
                wolf.jump();
            }
        }
        return true;
    }


    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        gravity = -0.2f;
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gravity = -0.5f;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
