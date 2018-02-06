package com.sam.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.sam.game.screens.Menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class HowlAway extends Game {

    public SpriteBatch batch;
    private int totalBones, topScore, equipped;
    private int[] skinsAvailable  = new int[] {1, 1, 1};
	public AssetManager assets;
    public Texture text0, text1, text2, text3, text4,
            text5, text6, text7, text8, text9;
    private Music music;
	private Preferences preferences;
    @Override
	public void create() {
        preferences = Gdx.app.getPreferences("options");
        equipped = 0;
        totalBones = preferences.getInteger("totalBones");
        topScore = preferences.getInteger("topScore");/*
        skinsAvailable[0] = preferences.getInteger("hasDefaultSkin");
        skinsAvailable[1] = preferences.getInteger("hasSkin1");
        skinsAvailable[2] = preferences.getInteger("hasSkin2");*/
        /*try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            totalBones = Integer.parseInt(reader.readLine());
            topScore = Integer.parseInt(reader.readLine());
            skinsAvailable[0] = Integer.parseInt(reader.readLine());
            skinsAvailable[1] = Integer.parseInt(reader.readLine());
            skinsAvailable[2] = Integer.parseInt(reader.readLine());
            reader.close();
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }*/

        assets = new AssetManager();
        assets.load("city3.png", Texture.class);
        assets.load("zoo.png", Texture.class);
        assets.load("zoo2.png", Texture.class);
        assets.load("zoo3.png", Texture.class);
        assets.load("werewolf_bg.png", Texture.class);
        assets.load("road_blank.png", Texture.class);
        assets.load("road_full.png", Texture.class);
        assets.load("road_both.png", Texture.class);
        assets.load("road_left.png", Texture.class);
        assets.load("road_right.png", Texture.class);
        assets.load("underground.png", Texture.class);
        assets.load("pause_btn.png", Texture.class);
        assets.load("pause.png", Texture.class);
        assets.load("resume_btn.png", Texture.class);
        assets.load("retry_btn.png", Texture.class);
        assets.load("home_btn.png", Texture.class);
        assets.load("gameover_panel.png", Texture.class);
        assets.load("shop_btn.png", Texture.class);
        assets.load("shopBtn.png", Texture.class);
        assets.load("exit_btn.png", Texture.class);
        assets.load("play_btn.png", Texture.class);
        assets.load("free.png", Texture.class);


        assets.load("normalwolf.atlas", TextureAtlas.class);
        assets.load("goldenboneswolf.atlas", TextureAtlas.class);
        assets.load("magnetwolf.atlas", TextureAtlas.class);
        assets.load("shieldwolf.atlas", TextureAtlas.class);
        assets.load("hermeswolf.atlas", TextureAtlas.class);
        assets.load("werewolf.atlas", TextureAtlas.class);
        assets.load("bone.atlas", TextureAtlas.class);
        assets.load("goldenbone.atlas", TextureAtlas.class);
        assets.load("bonepop.atlas", TextureAtlas.class);
        assets.load("goldenbonepop.atlas", TextureAtlas.class);
        assets.load("littlegirl.atlas", TextureAtlas.class);
        assets.load("littlegirldead.atlas", TextureAtlas.class);
        assets.load("catcher.atlas", TextureAtlas.class);
        assets.load("catchdead.atlas", TextureAtlas.class);
        assets.load("catcherdead.atlas", TextureAtlas.class);
        assets.load("car.atlas", TextureAtlas.class);
        assets.load("carSplat.atlas", TextureAtlas.class);
        assets.load("carDead.atlas", TextureAtlas.class);

        assets.load("skin1normalwolf.atlas", TextureAtlas.class);
        assets.load("skin1goldenboneswolf.atlas", TextureAtlas.class);
        assets.load("skin1magnetwolf.atlas", TextureAtlas.class);
        assets.load("skin1werewolf.atlas", TextureAtlas.class);
        assets.load("skin1shield.atlas", TextureAtlas.class);
        assets.load("skin1hermes.atlas", TextureAtlas.class);

        assets.load("skin2wolf.atlas", TextureAtlas.class);
        assets.load("skin2goldenbones.atlas", TextureAtlas.class);
        assets.load("skin2magnet.atlas", TextureAtlas.class);
        assets.load("skin2werewolf.atlas", TextureAtlas.class);
        assets.load("skin2shield.atlas", TextureAtlas.class);
        assets.load("skin2hermes.atlas", TextureAtlas.class);

        assets.load("shop.png", Texture.class);
        assets.load("panel.png", Texture.class);
        assets.load("close_btn.png", Texture.class);
        assets.load("next_btn.png", Texture.class);
        assets.load("prev_btn.png", Texture.class);
        assets.load("buy_btn.png", Texture.class);
        assets.load("use_btn.png", Texture.class);
        assets.load("next_btn_gray.png", Texture.class);
        assets.load("prev_btn_gray.png", Texture.class);
        assets.load("buy_btn_gray.png", Texture.class);
        assets.load("use_btn_gray.png", Texture.class);


        assets.load("powerup_fullmoon.png", Texture.class);
        assets.load("powerup_goldenbones.png", Texture.class);
        assets.load("powerup_hermes.png", Texture.class);
        assets.load("powerup_magnet.png", Texture.class);
        assets.load("powerup_multiplier.png", Texture.class);
        assets.load("powerup_shield.png", Texture.class);
        assets.load("powerup_wolfpack.png", Texture.class);

        assets.load("alphanum/0.png", Texture.class);
        assets.load("alphanum/1.png", Texture.class);
        assets.load("alphanum/2.png", Texture.class);
        assets.load("alphanum/3.png", Texture.class);
        assets.load("alphanum/4.png", Texture.class);
        assets.load("alphanum/5.png", Texture.class);
        assets.load("alphanum/6.png", Texture.class);
        assets.load("alphanum/7.png", Texture.class);
        assets.load("alphanum/8.png", Texture.class);
        assets.load("alphanum/9.png", Texture.class);

        assets.load("icon_fullmoon.png", Texture.class);
        assets.load("icon_fullmoon2.png", Texture.class);
        assets.load("icon_magnet.png", Texture.class);
        assets.load("icon_magnet2.png", Texture.class);
        assets.load("icon_feather.png", Texture.class);
        assets.load("icon_feather2.png", Texture.class);
        assets.load("icon_multiplier.png", Texture.class);
        assets.load("icon_multiplier2.png", Texture.class);
        assets.load("icon_goldenbones.png", Texture.class);
        assets.load("icon_goldenbones2.png", Texture.class);
        assets.load("icon_shield.png", Texture.class);
        assets.load("icon_shield2.png", Texture.class);

        assets.load("sounds/bgm.wav", Music.class);
        assets.load("sounds/girl_scream.mp3", Sound.class);
        assets.load("sounds/flap_wing.ogg", Sound.class);
        assets.load("sounds/jump.mp3", Sound.class);
        assets.load("sounds/wolfpack.wav", Sound.class);
        assets.load("sounds/fullmoon_sound.wav", Sound.class);
        assets.load("sounds/whine.mp3", Sound.class);
        assets.load("sounds/car_explosion.mp3", Sound.class);
        assets.load("sounds/wings.wav", Sound.class);
        assets.load("sounds/man_death.wav", Sound.class);
        assets.load("sounds/magnet.wav", Sound.class);
        assets.load("sounds/cash.mp3", Sound.class);
        assets.load("sounds/tires.wav", Sound.class);
        assets.load("sounds/splat.wav", Sound.class);
        assets.load("sounds/shield.wav", Sound.class);
        while(!assets.update()) {}

        music = assets.get("sounds/bgm.wav", Music.class);
        music.setLooping(true);
        music.play();

        text0 = assets.get("alphanum/0.png", Texture.class);
        text1 = assets.get("alphanum/1.png", Texture.class);
        text2 = assets.get("alphanum/2.png", Texture.class);
        text3 = assets.get("alphanum/3.png", Texture.class);
        text4 = assets.get("alphanum/4.png", Texture.class);
        text5 = assets.get("alphanum/5.png", Texture.class);
        text6 = assets.get("alphanum/6.png", Texture.class);
        text7 = assets.get("alphanum/7.png", Texture.class);
        text8 = assets.get("alphanum/8.png", Texture.class);
        text9 = assets.get("alphanum/9.png", Texture.class);

        batch = new SpriteBatch();
        this.setScreen(new Menu(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        music.dispose();
        text0.dispose();
        text1.dispose();
        text2.dispose();
        text3.dispose();
        text4.dispose();
        text5.dispose();
        text6.dispose();
        text7.dispose();
        text8.dispose();
        text9.dispose();
        saveRecord();
    }

    @Override
	public void render () {
        super.render();

	}

    public int getBones() {
        return totalBones;
    }
    public void incrementBones(int bones) {
        totalBones += bones;
    }
    public void decrementBones(int bones) { totalBones -= bones; }
    public void saveRecord() {
        /*try{
            PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
            writer.println(totalBones);
            writer.println(topScore);
            for(int i = 0; i < 3; i++) {
                writer.println(skinsAvailable[i]);
            }
            writer.close();
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }*/
        preferences.putInteger("totalBones", totalBones);
        preferences.putInteger("topScore", topScore);
        preferences.putInteger("hasDefaultSkin", skinsAvailable[0]);
        preferences.putInteger("hasSkin1", skinsAvailable[1]);
        preferences.putInteger("hasSkin2", skinsAvailable[2]);
    }
    public void setTopScore(int score) {
        topScore = score;
    }
    public int getTopScore() {
        return topScore;
    }
    public int getEquipped() {
        return equipped;
    }
    public void setEquipped(int equipped) {
        this.equipped = equipped;
    }

    public int[] getSkinsAvailable() {
        return skinsAvailable;
    }
}
