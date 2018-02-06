package com.sam.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sam.game.HowlAway;

import static com.sam.game.Constants.*;

public class Menu implements Screen {
    final HowlAway game;
    private Vector3 touch;
    private OrthographicCamera camera;
    private Texture city, zoo, playBtn, shopBtn, exitBtn;
    private Rectangle playBound, shopBound, exitBound;

    public Menu(HowlAway game) {
        this.game = game;
        Gdx.gl.glClearColor(0, 0, 0, 1);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);

        city = game.assets.get("city3.png");
        zoo = game.assets.get("zoo.png");
        playBtn = game.assets.get("play_btn.png");
        shopBtn = game.assets.get("shop_btn.png");
        exitBtn = game.assets.get("exit_btn.png");

        playBound = new Rectangle(camera.viewportWidth-playBtn.getWidth()-50,
                camera.viewportHeight-playBtn.getHeight()-75,
                playBtn.getWidth(), playBtn.getHeight());
        shopBound = new Rectangle(camera.viewportWidth-shopBtn.getWidth()-150,
                camera.viewportHeight-shopBtn.getHeight()-160,
                shopBtn.getWidth(), shopBtn.getHeight());
        exitBound = new Rectangle(camera.viewportWidth-exitBtn.getWidth()-50,
                camera.viewportHeight-exitBtn.getHeight()-160,
                exitBtn.getWidth(), exitBtn.getHeight());

        touch = new Vector3();
    }

    @Override
    public void show() {

    }

    public void handleInput() {
        if(Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if(playBound.contains(touch.x, touch.y)) {
                game.setScreen(new Play(game));
            }
            if(shopBound.contains(touch.x, touch.y)) {
                game.setScreen(new Shop(game));
            }
            if(exitBound.contains(touch.x, touch.y)) {
                game.saveRecord();
                Gdx.app.exit();
            }

        }

    }

    public void update(float delta) {
        handleInput();
    }


    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(city, 0, 0);
        game.batch.draw(zoo, 0, 0);
        game.batch.draw(playBtn, playBound.x, playBound.y);
        game.batch.draw(shopBtn, shopBound.x, shopBound.y);
        game.batch.draw(exitBtn, exitBound.x, exitBound.y);

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
        city.dispose();
        zoo.dispose();
        playBtn.dispose();
        exitBtn.dispose();
        shopBtn.dispose();
        game.saveRecord();
    }
}
