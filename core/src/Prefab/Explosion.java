package Prefab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by ilasm on 3.12.2016.
 */

public class Explosion extends Actor {

    private static final int FRAME_COLUMN = 4;
    private static final int FRAME_ROW = 4;

    Texture explosionSheet;
    TextureRegion[] explosionFrames;
    TextureRegion currentFrame;
    Animation explosionAnimation;

    float stateTime;

    long startTime = 0;

    private float y;

    private float x;

    private float width;

    private float height;

    public Explosion(float _x, float _y, float _width, float _height) {
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        startTime = TimeUtils.nanoTime();
        explosionSheet = new Texture(Gdx.files.internal("Graphics/exp2.png"));
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet, explosionSheet.getWidth() / FRAME_COLUMN, explosionSheet.getHeight() / FRAME_ROW);
        explosionFrames = new TextureRegion[FRAME_COLUMN * FRAME_ROW];
        int index = 0;
        for (int i = 0; i < FRAME_ROW; i++) {
            for (int j = 0; j < FRAME_COLUMN; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }
        explosionAnimation = new Animation(0.025f, explosionFrames);
        stateTime = 0f;
    }




    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = explosionAnimation.getKeyFrame(stateTime);
        batch.draw(currentFrame, x, y, width, height);
        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            remove();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
