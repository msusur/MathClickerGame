package Prefab;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ilasm on 30.11.2016.
 */

public class Prefab extends Group {

    private Stage stage;

    public Prefab(Stage _stage)
    {
        this.stage = _stage;
    }

    @Override
    public Stage getStage(){
        return stage;
    }

}
