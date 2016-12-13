package Prefab;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

import Loader.GameLoader;

public class MonsterGroup extends Prefab {

    public class MonsterRectangle {
        public float x;
        public float y;
        public float width;
        public float height;

        public MonsterRectangle(float _x, float _y, float _width, float _height) {
            this.x = _x;
            this.y = _y;
            this.width = _width;
            this.height = _height;
        }
    }

    private List<Image> monsters = new ArrayList<Image>();

    private List<Runnable> runnables = new ArrayList<Runnable>();

    private List<MonsterRectangle> monsterConstantPositions = new ArrayList<MonsterRectangle>();

    private float maxHealth = 50;

    private float health = maxHealth;

    private ProgressBar healthBar;

    public MonsterGroup(Stage _stage) {
        super(_stage);
        float monsterWidth = getStage().getWidth() / 4.1f;
        float monsterHeight = getStage().getHeight() / 5;
        float monsterX = getStage().getWidth() / 2 - monsterWidth / 2;
        float monsterY = getStage().getHeight() / 2f + monsterHeight / 2;
        Skin skin = GameLoader.getSkin("Skin/MathClicker/comic-ui.json");

        {
            monsterConstantPositions.add(
                    new MonsterRectangle(monsterX, monsterY, monsterWidth, monsterHeight));
            monsterConstantPositions.add(
                    new MonsterRectangle(monsterX - monsterWidth / 1.3f, monsterY - monsterHeight / 2.9f, monsterWidth, monsterHeight));
            monsterConstantPositions.add(
                    new MonsterRectangle(monsterX + monsterWidth / 1.3f, monsterY - monsterHeight / 2.9f, monsterWidth, monsterHeight));
        }

        healthBar = new ProgressBar(0, maxHealth, 1, false, skin);
        {
            healthBar.setWidth(getStage().getWidth() / 3);
            healthBar.getStyle().knobBefore.setMinHeight(getStage().getHeight() / 35);
            healthBar.setPosition(monsterX, monsterY);
            healthBar.setPosition(healthBar.getX(), healthBar.getY() + (monsterHeight * 1.2f));
            healthBar.setValue(health);
        }


        this.addActor(healthBar);
        getStage().addActor(healthBar);
    }

    public Image getMonster(int i) {
        return monsters.get(i);
    }

    public void onDefeated(Runnable run) {
        runnables.add(run);
    }
    private void defeated() {
        for (Runnable r : runnables) {
            r.run();
        }
        for (Image i : monsters) {
            i.remove();
        }
        healthBar.remove();
    }

    public void harm(float ratio) {
        health -= ratio;
        shake();
        if (isDead()) {
            defeated();
        }
        healthBar.setValue(health);
    }

    public void shake() {
        for (Image monsterImage : monsters) {
            float currentX = monsterImage.getX();
            float currentY = monsterImage.getY();
            float currentSizeX = monsterImage.getWidth();
            float currentSizeY = monsterImage.getHeight();

            float speed = MathUtils.random(0.02f, 0.08f);
            float rand = MathUtils.random(0, currentSizeX / 4);
            //Actions.sequence(Actions.sizeTo(currentSizeX+getStage().getWidth()/70,currentSizeY+getStage().getHeight()/70,0.05f),Actions.sizeTo(currentSizeX,currentSizeY,0.05f)),
            monsterImage.addAction(Actions.parallel(
                    Actions.sequence(Actions.moveTo(currentX - ((getStage().getWidth() / 30) + rand), currentY, speed), Actions.moveTo((currentX + getStage().getWidth() / 30) - rand, currentY, speed), Actions.moveTo(currentX, currentY, speed))));
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void addMonster(Image monsterImage) {
        Image monsterImageClone = new Image(monsterImage.getDrawable());
        monsterImageClone.setOrigin(monsterImageClone.getX() / 2, monsterImageClone.getY() / 2);
        MonsterRectangle rect = monsterConstantPositions.get(monsters.size());
        monsterImageClone.setSize(rect.width, rect.height);
        monsterImageClone.setPosition(rect.x, rect.y);
        monsters.add(monsterImageClone);
        healthBar.setPosition(healthBar.getX() - monsterImageClone.getWidth() / 15, healthBar.getY());
        getStage().addActor(monsterImageClone);
    }

    @Override
    public float getWidth() {
        Image first = monsters.get(0);
        Image last = monsters.get(monsters.size() - 1);
        float width = first.getX() + (last.getX() + last.getHeight());
        return width;
    }

    @Override
    public float getHeight() {
        return monsters.get(0).getHeight();
    }
}
