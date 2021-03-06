package com.sam.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sam.game.HowlAway;

import static com.sam.game.Constants.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GAME_WIDTH;
        config.height = GAME_HEIGHT;
        config.title = GAME_TITLE;
        config.resizable = false;
        new LwjglApplication(new HowlAway(), config);
	}
}
