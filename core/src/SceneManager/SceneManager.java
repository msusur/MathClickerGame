package SceneManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import Scenes.Scene;

/**
 * Created by ilasm on 24.11.2016.
 */

public class SceneManager {
    public static void load(Scene scene, Game game) {
        game.setScreen(scene);
    }
}
