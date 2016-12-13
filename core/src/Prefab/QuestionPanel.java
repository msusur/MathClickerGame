package Prefab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

import Loader.GameLoader;
import Quez.Question;
import Quez.QuestionAnswerResult;
import Quez.QuestionSetting;
import Quez.QuestionManager;


public class QuestionPanel extends Prefab {

    public class QuestionScreen extends Prefab {

        private float panelWidth;

        private float panelHeight;

        private float panelX;

        private float panelY;

        private Skin panelSkin;

        private Image backgroundFrame;

        public Container labelContainer;

        public Question currentQuestion;

        public Label currentLabel;

        @Override
        public float getWidth() {
            return panelWidth;
        }

        @Override
        public float getHeight() {
            return panelHeight;
        }

        @Override
        public float getX() {
            return panelX;
        }

        @Override
        public float getY() {
            return panelY;
        }

        @Override
        public void setX(float x) {
            panelX = x;
            super.setX(x);
        }

        @Override
        public void setY(float y) {
            panelY = y;
            super.setX(y);
        }

        @Override
        public void setHeight(float height) {
            panelHeight = height;
            super.setHeight(height);
        }

        @Override
        public void setWidth(float width) {
            panelWidth = width;
            super.setWidth(width);
        }


        public QuestionScreen(float x, float y, float width, float height, Skin skin, Stage stage) {
            super(stage);
            this.panelWidth = width;
            this.panelHeight = height;
            this.panelX = x;
            this.panelY = y;
            this.panelSkin = skin;
        }

        public void initialize() {
            Texture textureOfbackground = new Texture(Gdx.files.internal("Graphics/bgonblack.png"));
            backgroundFrame = new Image(textureOfbackground);
            backgroundFrame.setWidth(getWidth());
            backgroundFrame.setHeight(getHeight());
            backgroundFrame.setX(getX());
            backgroundFrame.setY(getY());
            backgroundFrame.setTouchable(Touchable.disabled);
            addActor(backgroundFrame);
        }

        public void createQuestion(QuestionSetting questionSetting) {
            if (labelContainer != null) {
                final Container con = labelContainer;
                labelContainer.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(getX(), getY() - getHeight() / 2, 0.3f), Actions.scaleTo(0, 0, 0.3f)), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        removeActor(con);
                        con.remove();
                    }
                })));
            }
            currentQuestion = QuestionManager.createQuestion(questionSetting);
            currentLabel = new Label(currentQuestion.toString(), panelSkin);
            currentLabel.setWidth(getWidth());
            currentLabel.setHeight(getHeight());
            currentLabel.setFontScale((getStage().getWidth() / getStage().getHeight()) * 3.2f);
            currentLabel.setAlignment(Align.center);
            currentLabel.setTouchable(Touchable.disabled);
            labelContainer = new Container(currentLabel);
            labelContainer.setTransform(true);
            labelContainer.setWidth(getWidth());
            labelContainer.setHeight(getHeight());
            labelContainer.setZIndex(5);
            labelContainer.setTouchable(Touchable.disabled);

            for (int b = 0; b < buttonGroupChilds.size(); b++) {
                TextButton button = buttonGroupChilds.get(b);
                button.getLabel().setText(currentQuestion.randomAnswers[b] + "");
                button.setZIndex(0);
            }

            labelContainer.setOrigin(labelContainer.getWidth() / 2, labelContainer.getHeight() / 2);
            labelContainer.setScale(getWidth() * 0.4f / 1080);
            labelContainer.setPosition(getX(), getY() + getHeight() / 2);
            labelContainer.addAction(Actions.parallel(Actions.moveTo(getX(), getY(), 0.3f), Actions.scaleTo(getWidth() * 2 / 1080, getWidth() * 2 / 1080, 0.3f)));
            addActor(labelContainer);
        }
    }

    public QuestionScreen screen;

    public QuestionSetting quizSetting;

    public ArrayList<TextButton> buttonGroupChilds;

    public List<QuestionAnswerResult> onQuestionListeners = new ArrayList<QuestionAnswerResult>();

    public void addOnAnswerListener(QuestionAnswerResult result){
        onQuestionListeners.add(result);
    }

    private void fireAnswerResult(boolean result)
    {
        for(QuestionAnswerResult r : onQuestionListeners)
        {
            r.onAnswer(result);
        }
    }

    public QuestionPanel(Stage _stage, QuestionSetting setting) {
        super(_stage);
        quizSetting = setting;
        Skin skin = GameLoader.getSkin("Skin/MathClicker/comic-ui.json");
        Texture text2 = new Texture(Gdx.files.internal("Graphics/bgtop.png"));
        text2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image bg = new Image(text2);
        bg.setWidth(getStage().getWidth() / 1.1f);
        bg.setHeight(getStage().getHeight() / 3);
        bg.setPosition(getStage().getWidth() / 2 - bg.getWidth() / 2, (getStage().getHeight() * 25 / 1080));

        setWidth(bg.getWidth());
        setHeight(bg.getHeight());

        screen = new QuestionScreen(bg.getX() + bg.getWidth() * 19 / 1080, bg.getHeight() * 570 / 1080f, bg.getWidth() / 1.04f, bg.getHeight() / 2, skin, getStage());
        Group buttonGroup = new Group();

        for (int i = 0; i < 3; i++) {
            TextButton button = new TextButton("2x2", skin);
            button.setWidth(bg.getWidth() / 3.2f);
            button.setHeight(bg.getHeight() / 2.1f);
            button.setPosition(bg.getX() + button.getWidth() * i, (getStage().getHeight() * 30 / 1080));
            button.getLabel().setFontScale(getStage().getWidth() * 2 / 1080);
            TextButton.TextButtonStyle style = button.getStyle();
            style.pressedOffsetY = button.getHeight() / 6;
            style.checkedOffsetY = button.getHeight() / 5;
            style.unpressedOffsetY = button.getHeight() / 5;
            final int index = i;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    if (screen.currentQuestion == null) {
                        System.out.print("question is null");
                        return;
                    }
                    if (screen.currentQuestion.correctQuestion == screen.currentQuestion.randomAnswers[index]) {
                        QuestionPanel.this.onCorrectAnswer();
                    } else {
                        QuestionPanel.this.onFalseAnswer();
                    }
                }
            });
            buttonGroup.addActor(button);
        }

        buttonGroupChilds = new ArrayList<TextButton>();
        for (int k = 0; k < buttonGroup.getChildren().size; k++) {
            buttonGroupChilds.add(k, ((TextButton) buttonGroup.getChildren().get(k)));
        }

        buttonGroup.setPosition(bg.getWidth() / 2 - screen.getWidth() / 2, 0);

        Image mask = new Image(new Texture("Graphics/bgbottom.png"));
        mask.setWidth(bg.getWidth());
        mask.setHeight(bg.getHeight());
        mask.setPosition(bg.getX(), bg.getY());
        mask.setTouchable(Touchable.disabled);

        this.addActor(bg);
        this.addActor(mask);
        this.addActor(screen);
        this.addActor(buttonGroup);
        this.setOrigin(getStage().getWidth() / 2, bg.getHeight() / 2);
        screen.initialize();
        screen.createQuestion(quizSetting);
    }

    float monsterWidth;
    float monsterHeight;
    float monsterX;
    float monsterY;

    public void onCorrectAnswer() {
        screen.createQuestion(quizSetting);

        final Image bomb = new Image(new Texture(Gdx.files.internal("Graphics/PropertyIcons/icon_bomb.png")));
        MonsterGroup monster = MonsterManager.getCurrentMonster();
        monsterWidth = monster.getWidth()/2;
        monsterHeight = monster.getWidth()/2;
        monsterX = getStage().getWidth() / 2 - monsterWidth / 2;
        monsterY = monster.getMonster(0).getY()-monster.getMonster(0).getHeight()/2;
        bomb.setWidth(getStage().getWidth() / 3.6f);
        bomb.setHeight(getStage().getHeight() / 6);
        bomb.setX(monsterX);
        bomb.setY(getStage().getWidth() / 2);

        float turnAndMoveSpeed = 1;
        bomb.addAction(Actions.sequence(Actions.parallel(Actions.sizeTo(bomb.getWidth() / 2, bomb.getWidth() / 2, turnAndMoveSpeed),
                Actions.moveTo((monsterX + monsterWidth / 2) - bomb.getWidth() / 4,
                        (monsterY + monsterHeight / 2) - bomb.getHeight() / 2, turnAndMoveSpeed),
                Actions.rotateTo(360, turnAndMoveSpeed)), Actions.run(new Runnable() {
            @Override
            public void run() {
                createExplosion();
                bomb.remove();
                fireAnswerResult(true);
            }
        })));
        getStage().addActor(bomb);
    }

    public void onFalseAnswer() {
        shakeScreen();
        screen.createQuestion(quizSetting);
        fireAnswerResult(false);
    }

    public void createExplosion() {
        float explosionWidth = monsterWidth;
        float explosionHeight = monsterHeight;
        float explosionX = monsterX;
        float explosionY = monsterY;
        Explosion exp = new Explosion(explosionX, explosionY, explosionWidth, explosionHeight);
        getStage().addActor(exp);
    }


    public void shakeScreen() {
        float shakeRatio = getStage().getWidth() / 20;
        this.addAction(Actions.sequence(Actions.moveTo(getX() - shakeRatio, 0, 0.05f), Actions.moveTo(getX() + shakeRatio, 0, 0.05f), Actions.moveTo(getX(), getY(), 0.05f)));
    }
}
