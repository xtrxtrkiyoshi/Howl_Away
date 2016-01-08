package com.sam.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sam.game.effects.BonePop;
import com.sam.game.screens.Play;
import com.sam.game.sprites.Bone;
import com.sam.game.sprites.Car;
import com.sam.game.sprites.Civilian;
import com.sam.game.sprites.PowerUp;
import com.sam.game.sprites.Soldier;
import com.sam.game.sprites.Wolf;

public class HowlAway extends Game {


    public Screen play, menu, gameOver, shop;
    public SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
        play = new Play(this);
        this.setScreen(play);
	}

    @Override
    public void dispose() {
        batch.dispose();
        Wolf.dispose();
        Car.dispose();
        Bone.dispose();
        PowerUp.dispose();
        Civilian.dispose();
        Soldier.dispose();
        BonePop.dispose();
    }

    @Override
	public void render () {
        super.render();
	}


}
