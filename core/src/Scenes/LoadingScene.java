package Scenes;

import Loader.GameLoader;
import Managers.Game.GameConfigManager;
import Managers.Language.LanguageManager;
import Managers.Sound.SoundManager;
import SceneManager.SceneManager;


public class LoadingScene extends Scene {

    @Override
    public void show(){
        long l = System.currentTimeMillis();
        {
            GameLoader.add("Xml/LanguageConfig.xml", new LanguageManager());
            GameLoader.add("Xml/GameConfig.xml", new GameConfigManager());
            GameLoader.add("Xml/SoundConfig.xml", new SoundManager());
            GameLoader.addSkin("Skin/MathClicker/commonatlas.atlas", "Skin/MathClicker/comic-ui.json");
            GameLoader.loadAll();
        }
        long l2 = System.currentTimeMillis();
        SceneManager.load(new GameScene(),this.game);
    }
}
