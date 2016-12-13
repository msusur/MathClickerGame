package Prefab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;


public class MonsterManager {

    private static Stage stage;

    public static int MONSTER_COUNT = 8;

    private static MonsterGroup monsterGroup;

    private static List<Image> monsterImages = new ArrayList<Image>();

    public static void beginSpawningMonster(Stage _stage) {
        stage = _stage;
        fillMonsterImages();
        spawn();
    }

    public static MonsterGroup getCurrentMonster() {
        return monsterGroup;
    }

    static int monsterLevel = 4;
    static int monsterCount = 1;

    public static void spawn() {
        if (monsterGroup != null) {
            monsterGroup.remove();
        }
        monsterGroup = new MonsterGroup(stage);
        for (int i = 0; i < monsterCount; i++) {
            int rand = MathUtils.random(0, monsterLevel);
            monsterGroup.addMonster(monsterImages.get(rand));
        }

        monsterGroup.onDefeated(new Runnable() {
            @Override
            public void run() {
                spawn();
            }
        });
    }

    public static void fillMonsterImages() {
        for (int i = 1; i <= MONSTER_COUNT; i++) {
            monsterImages.add(new Image(new Texture("Graphics/Monster/monster_egg_" + i + ".png")));
        }
    }

    public static void harm(int ratio) {
        monsterGroup.harm(ratio);
    }
}
