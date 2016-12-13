package Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import Loader.GameLoader;
import Managers.Player.PlayerManager;
import Prefab.MonsterManager;
import Prefab.UpgradePanel;
import Quez.QuestionAnswerResult;
import Quez.MathOperation;
import Prefab.QuestionPanel;
import Quez.QuestionSetting;

public class GameScene extends Scene {

    QuestionPanel panel;

    QuestionSetting currentQuizSetting;

    Label levelText;

    Label goldText;

    Container<Label> levelTextContainer;

    Container<Label> goldTextContainer;

    UpgradePanel upgradePanel;

    Button backButton;

    void loadUpgradePanel() {
        upgradePanel = new UpgradePanel(getStage());
        getStage().addActor(upgradePanel);
    }

    @Override
    public void show() {

        currentQuizSetting = new QuestionSetting(3, MathOperation.Addition);
        Image gameBackground = new Image(new Texture("Graphics/gameBackground.png"));

        gameBackground.setWidth(getStage().getWidth());

        gameBackground.setHeight(getStage().getHeight());

        Skin skin = GameLoader.getSkin("Skin/MathClicker/comic-ui.json");

        panel = new QuestionPanel(getStage(), currentQuizSetting);
        panel.addOnAnswerListener(new QuestionAnswerResult() {
            @Override
            public void onAnswer(boolean isCorrect) {
                if (isCorrect) {
                    MonsterManager.harm(5);
                    PlayerManager.addMoney(PlayerManager.getLvl());
                    if (PlayerManager.getMoney() > PlayerManager.getLvl() * 10) {
                        PlayerManager.setMoney(0);
                        PlayerManager.setLvl(PlayerManager.getLvl() + 1);
                        onDisplayLevelChanged();
                    }
                    onGoldChanged();
                }
            }
        });
        addActor(gameBackground);
        addActor(panel);

        Button.ButtonStyle buyButtonstyle = new Button.ButtonStyle();
        buyButtonstyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("Graphics/GameUIElement/bt_buy.png")));
        buyButtonstyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("Graphics/GameUIElement/bt_buy.png")));
        buyButtonstyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("Graphics/GameUIElement/bt_buy.png")));
        final Button buyButton = new Button(buyButtonstyle);
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upgradePanel.show();
                buyButton.addAction(Actions.fadeOut(0.1f));
                buyButton.setTouchable(Touchable.disabled);
            }
        });

        buyButton.setSize(getStage().getWidth() / 4, getStage().getHeight() / 14);
        buyButton.setPosition(getStage().getWidth() - buyButton.getWidth() / 1.3f, getStage().getHeight() - buyButton.getHeight() * 1.3f);
        addActor(buyButton);

        Image goldBar = new Image(new Texture("Graphics/GameUIElement/goldbar.png"));
        goldBar.setSize(getStage().getWidth() / 2.5f, getStage().getHeight() / 16);
        goldBar.setPosition(goldBar.getWidth() * -0.17f, getStage().getHeight() - goldBar.getHeight() * 1.3f);

        Image levelBar = new Image(new Texture("Graphics/GameUIElement/levelbar.png"));
        levelBar.setSize(getStage().getWidth() / 2.5f, getStage().getHeight() / 16);
        levelBar.setPosition(levelBar.getWidth() * -0.17f, getStage().getHeight() - (goldBar.getHeight() + levelBar.getHeight() * 1.35f));


        goldText = new Label(PlayerManager.getMoney() + "", skin);
        {
            System.out.println("a" + (getStage().getWidth() / getStage().getHeight()) * 2f);
            System.out.println(Gdx.graphics.getWidth());
            goldText.setFontScale(((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight()) * 1f);
            goldText.setWidth(goldBar.getWidth());
            goldText.setHeight(goldBar.getHeight());
        }

        goldTextContainer = new Container<Label>(goldText);
        {
            goldTextContainer.setTransform(true);
            goldTextContainer.setTouchable(Touchable.disabled);
            goldTextContainer.setWidth(goldBar.getWidth());
            goldTextContainer.setHeight(goldBar.getHeight());
            goldTextContainer.setOrigin(goldText.getWidth() / 2, goldText.getHeight() / 2);
            goldTextContainer.setPosition(goldBar.getX() + goldBar.getWidth() / 12, goldBar.getY());
        }

        levelText = new Label(PlayerManager.getLvl() + "", skin);
        levelText.setFontScale(getStage().getWidth() / getStage().getHeight() * 1f);

        levelTextContainer = new Container<Label>(levelText);
        levelTextContainer.setTransform(true);
        levelTextContainer.setWidth(levelText.getWidth());
        levelTextContainer.setHeight(levelText.getHeight());
        levelTextContainer.setTouchable(Touchable.disabled);
        levelTextContainer.setPosition(levelBar.getX() + (levelBar.getWidth() * 0.55f), levelBar.getY() - levelBar.getHeight() / 2 + (levelBar.getHeight() * 0.20f));

        addActor(goldBar);
        addActor(levelBar);
        addActor(levelTextContainer);
        addActor(goldTextContainer);
        MonsterManager.beginSpawningMonster(getStage());
        loadUpgradePanel();

        upgradePanel.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upgradePanel.hide();
                buyButton.addAction(Actions.fadeIn(0.1f));
                buyButton.setTouchable(Touchable.enabled);
            }
        });

    }

    private void onDisplayLevelChanged() {
        levelText.setText(PlayerManager.getLvl() + "");
        float firstXScale = levelTextContainer.getScaleX();
        float firstYScale = levelTextContainer.getScaleY();

        levelTextContainer.addAction(Actions.sequence(Actions.scaleTo(levelTextContainer.getScaleX() * 1.3f, levelTextContainer.getScaleY() * 1.3f, 0.05f),
                Actions.scaleTo(firstXScale, firstYScale, 0.05f)));
    }

    private void onGoldChanged() {
        goldText.setText(PlayerManager.getMoney() + "");
        float firstXScale = goldTextContainer.getScaleX();
        float firstYScale = goldTextContainer.getScaleY();

        goldTextContainer.addAction(Actions.sequence(Actions.scaleTo(goldTextContainer.getScaleX() * 1.3f, goldTextContainer.getScaleY() * 1.3f, 0.05f),
                Actions.scaleTo(firstXScale, firstYScale, 0.05f)));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
