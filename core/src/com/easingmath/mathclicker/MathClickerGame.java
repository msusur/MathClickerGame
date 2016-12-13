package com.easingmath.mathclicker;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import Scenes.LoadingScene;
import Scenes.Scene;

public class MathClickerGame extends Game {

	 Scene activeSene;

	@Override
	public void create () {
		activeSene = new LoadingScene();
		activeSene.game = this;
		setScreen(activeSene);
	}

}
