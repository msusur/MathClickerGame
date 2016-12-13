package Prefab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import Loader.GameLoader;

/**
 * Created by ilasm on 11.12.2016.
 */

public class UpgradePanel extends Prefab {

    Image background;

    private boolean showing;

    public Button backButton;

    public boolean isShowing() {
        return showing;
    }

    public void show() {
        float screen_width = Gdx.graphics.getWidth();
        float screen_height = Gdx.graphics.getHeight();
        addAction(Actions.moveTo(screen_width / 2 - background.getWidth() / 2,
                screen_height / 2 - background.getHeight() / 2, 0.3f));
        showing = true;
    }

    public void hide() {
        float screen_width = Gdx.graphics.getWidth();
        float screen_height = Gdx.graphics.getHeight();
        addAction(Actions.moveTo(screen_width, screen_height / 2 - background.getHeight() / 2, 0.3f));
        showing = false;
    }

    public UpgradePanel(Stage _stage) {
        super(_stage);

        Skin skin = GameLoader.getSkin("Skin/MathClicker/comic-ui.json");
        background = new Image(new Texture("Graphics/upgradeBackground.png"));
        background.setWidth(Gdx.graphics.getWidth() / 1.05f);
        background.setHeight(Gdx.graphics.getHeight() / 1.05f);
        setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2 - background.getHeight() / 2);
        backButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(
                "Graphics/back.png"
        ))));

        backButton.setSize(getStage().getWidth() / 6, getStage().getWidth() / 8);
        backButton.setPosition(background.getX(), background.getHeight() - backButton.getHeight());
        addActor(background);
        addActor(backButton);
    }

}
