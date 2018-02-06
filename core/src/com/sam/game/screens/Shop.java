package com.sam.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.sam.game.HowlAway;
import com.sam.game.sprites.Bone;
import com.sam.game.sprites.Wolf;

import org.lwjgl.Sys;
import static com.sam.game.Constants.*;

public class Shop implements Screen{

    final HowlAway game;
    private Texture shopBG, panel, closeBtn, nextBtn, prevBtn, buyBtn, useBtn, buyBtnGray, useBtnGray;
    private Rectangle closeBtnBound, panelBound, nextBtnBound, prevBtnBound, buyBtnBound;
    private OrthographicCamera camera;
    private Array<Wolf> wolves;
    private Array<Integer> prices;
    private Array<Texture> currentBones, priceBones;
    private Vector3 touch;
    private int current;
    private Bone bone1, bone2;
    private float elapsedTime;
    public Shop(HowlAway game) {
        this.game = game;
        current = 0;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 684, 336);
        wolves = new Array<Wolf>();
        for(int i=0; i<3; i++) {
            wolves.add(new Wolf(game, i));
            wolves.get(i).init(camera.viewportWidth/2-300,
                    camera.viewportHeight/2-57);
        }
        prices = new Array<Integer>();
        prices.add(0);
        prices.add(1000);
        prices.add(3000);
        currentBones = new Array<Texture>();
        priceBones = new Array<Texture>();



        // ADD MORE PRICES DEPENDE HA

        for(int i=0; i<7; i++)
            currentBones.add(game.text0);
        for(int i=0; i<4; i++)
            priceBones.add(game.text0);

        shopBG = game.assets.get("shop.png");
        panel = game.assets.get("panel.png");
        closeBtn = game.assets.get("close_btn.png");
        nextBtn = game.assets.get("next_btn.png");
        prevBtn = game.assets.get("prev_btn.png");
        buyBtn = game.assets.get("buy_btn.png");
        useBtn = game.assets.get("use_btn.png");
        buyBtnGray = game.assets.get("buy_btn_gray.png");
        useBtnGray = game.assets.get("use_btn_gray.png");


        closeBtnBound = new Rectangle(camera.viewportWidth-closeBtn.getWidth(),
                camera.viewportHeight-closeBtn.getHeight(),
                closeBtn.getWidth(), closeBtn.getHeight());
        panelBound = new Rectangle(camera.viewportWidth/2 - panel.getWidth()/2,
                camera.viewportHeight/2-panel.getHeight()/2,
                panel.getWidth(), panel.getHeight());
        buyBtnBound = new Rectangle(camera.viewportWidth/2 - buyBtn.getWidth()/2,
                camera.viewportHeight/8,
                buyBtn.getWidth(), buyBtn.getHeight());
        nextBtnBound = new Rectangle(camera.viewportWidth*3/4 - nextBtn.getWidth()/2,
                camera.viewportHeight/10 + nextBtn.getHeight()/8,
                nextBtn.getWidth(), nextBtn.getHeight());
        prevBtnBound = new Rectangle(camera.viewportWidth/4 - prevBtn.getWidth()/2,
                camera.viewportHeight/10 + prevBtn.getHeight()/8,
                prevBtn.getWidth(), prevBtn.getHeight());
        touch = new Vector3();

        elapsedTime = 0;
        bone1 = new Bone(game);
        bone2 = new Bone(game);
        bone1.init(50, camera.viewportHeight-90, true);
        bone2.init(camera.viewportWidth/2, camera.viewportHeight/2-30, false);
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void show() {

    }

    public void handleInput() {
        if(Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if(closeBtnBound.contains(touch.x, touch.y)) {
                camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
                game.setScreen(new Menu(game));
            }
            if(nextBtnBound.contains(touch.x, touch.y)) {
                current++;
                if(current == wolves.size) {
                    current = 0;
                }
            }
            if(prevBtnBound.contains(touch.x, touch.y)) {
                current--;
                if(current<0) {
                    current = wolves.size - 1;
                }
            }
            if(buyBtnBound.contains(touch.x, touch.y)) {
                if(game.getSkinsAvailable()[current] != 0 && game.getEquipped() != current) {
                    game.setEquipped(current);
                } else if (game.getSkinsAvailable()[current] == 0 && game.getBones() > prices.get(current)) {
                    game.getSkinsAvailable()[current] =  1;
                    game.decrementBones(prices.get(current));
                    game.saveRecord();
                }
            }
        }
    }

    public void update(float delta) {

        handleInput();
        elapsedTime += delta;

        change(prices.get(current), priceBones);
        change(game.getBones(), currentBones);

    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(shopBG, 0, 0);
        game.batch.draw(closeBtn, closeBtnBound.x, closeBtnBound.y);
        game.batch.draw(panel, panelBound.x, panelBound.y);
        if(game.getSkinsAvailable()[current] != 0 && game.getEquipped() == current) {
            game.batch.draw(useBtnGray, buyBtnBound.x, buyBtnBound.y);
        }
        else if(game.getSkinsAvailable()[current] != 0 && game.getEquipped() != current) {
            game.batch.draw(useBtn, buyBtnBound.x, buyBtnBound.y);
        }
        else if(game.getSkinsAvailable()[current] == 0 && game.getBones() > prices.get(current)) {
            game.batch.draw(buyBtn, buyBtnBound.x, buyBtnBound.y);
        }
        else {
            game.batch.draw(buyBtnGray, buyBtnBound.x, buyBtnBound.y);
        }
        game.batch.draw(prevBtn, prevBtnBound.x, prevBtnBound.y);
        game.batch.draw(nextBtn, nextBtnBound.x, nextBtnBound.y);

        if(current == 0) {
            game.batch.draw(game.assets.get("free.png", Texture.class), panelBound.x, panelBound.y);
        }
        else if(game.getSkinsAvailable()[current] == 1) {
            game.batch.draw(game.assets.get("free.png", Texture.class), panelBound.x, panelBound.y);
        }
        else {
            for(int i=0; i<4; i++){
                game.batch.draw(priceBones.get(i), camera.viewportWidth-150-currentBones.get(i).getWidth()*i,
                        camera.viewportHeight/2-20);
            }
            game.batch.draw(bone2.getAnimation().getKeyFrame(elapsedTime, true), bone2.getBounds().x, bone2.getBounds().y);
        }
        game.batch.draw(wolves.get(current).getAnimation().getKeyFrame(elapsedTime / 2, true),
                wolves.get(current).getBounds().x - wolves.get(current).getBounds().width,
                wolves.get(current).getBounds().y);
        for(int i=0; i<4; i++){
            game.batch.draw(currentBones.get(i), 60+camera.viewportWidth/4-currentBones.get(i).getWidth()*i,
                    camera.viewportHeight-80);
        }

        game.batch.draw(bone1.getAnimation().getKeyFrame(elapsedTime, true), bone1.getBounds().x, bone1.getBounds().y);

        game.batch.end();
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
        shopBG.dispose();
        panel.dispose();
        closeBtn.dispose();
        nextBtn.dispose();
        prevBtn.dispose();
        buyBtn.dispose();

    }

    public void change(int num, Array<Texture> text){
        int expa=1, expb=0;
        for(int i=0; i<text.size; i++){
            int count = (num%((int)Math.pow(10,expa)))/(int)Math.pow(10,expb);
            num = num-(num%((int)Math.pow(10,expa)));
            expa++;
            expb++;
            switch (count){
                case 0:
                    text.set(i, game.text0);
                    break;
                case 1:
                    text.set(i, game.text1);
                    break;
                case 2:
                    text.set(i, game.text2);
                    break;
                case 3:
                    text.set(i, game.text3);
                    break;
                case 4:
                    text.set(i, game.text4);
                    break;
                case 5:
                    text.set(i, game.text5);
                    break;
                case 6:
                    text.set(i, game.text6);
                    break;
                case 7:
                    text.set(i, game.text7);
                    break;
                case 8:
                    text.set(i, game.text8);
                    break;
                case 9:
                    text.set(i, game.text9);
                    break;
            }

        }
    }
}

