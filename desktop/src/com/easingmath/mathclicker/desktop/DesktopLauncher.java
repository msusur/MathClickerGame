package com.easingmath.mathclicker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.easingmath.mathclicker.MathClickerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1080/2;
		config.height=1920/2;
		new LwjglApplication(new MathClickerGame(), config);
	}
}
