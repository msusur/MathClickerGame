package Managers.Language;

import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;


public class Language {
    public String name;
    public String locale;
    public String iconPath;
    public ArrayList<Translation> Translations = new ArrayList<Translation>();
    @Override
    public String toString()
    {
        Json json = new Json();
        return json.toJson(this);
    }
}
