package Loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Logger.Log;
import Managers.Manager;


public class GameLoader {


    static class SkinAtlas
    {
        public String atlasPath;
        public String skinPath;
        public SkinAtlas(String _atlasPath,String _skinPath)
        {
            atlasPath = _atlasPath;
            skinPath = _skinPath;
        }
    }
    private static Map<String, Manager> LoadedXmlFiles = new HashMap<String, Manager>();
    private static Map<SkinAtlas, Skin> LoadedSkinFiles = new HashMap<SkinAtlas, Skin>();
    private static Map<String, TextureAtlas> LoadedAtlasFiles = new HashMap<String, TextureAtlas>();

    public static Skin getSkin(String skinPath) {
        for(Map.Entry<SkinAtlas,Skin> atlas : LoadedSkinFiles.entrySet())
        {
          if(atlas.getKey().skinPath.equals(skinPath)){
              return atlas.getValue();
          }
        }
        return null;
    }

    public static void addSkin(String atlasPath, String path)
    {
        SkinAtlas skinAtlas = new SkinAtlas(atlasPath,path);
        LoadedSkinFiles.put(skinAtlas,null);
    }

    public static void add(String path, Manager parser) {
        if (Log.debug) {
            Log.Now("\t GameLoader Adding: ( " + path + " : " + parser.getClass() + ")");
        }

        LoadedXmlFiles.put(path, parser);

        if (Log.debug) {
            Log.NowLn("...added");
        }
    }


    public static void loadAll() {
        if (Log.debug) {
            Log.NowLn("Loading Files...");
        }
        if (Log.debug) {
            Log.NowLn("\t Xml & Parsing :   ");
        }
        for (Map.Entry<String, Manager> parser : LoadedXmlFiles.entrySet()) {
            if (Log.debug) {
                Log.Now("\t \t " + "(" + parser.getKey() + ")");
            }
            XmlReader reader = new XmlReader();
            XmlReader.Element element = null;
            try {
                if (Log.debug) {
                    Log.NowLn();
                    Log.Now("\t\t\t Parsing... " + parser.getValue().getClass().getName());
                }
                element = reader.parse(Gdx.files.internal(parser.getKey()));
                if (Log.debug) {
                    Log.Now("\t\t\t ...Parsed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            parser.getValue().parseXml(element);
            if (Log.debug) {
                Log.NowLn("");
            }
        }
        if (Log.debug) {
            Log.NowLn("\t\t\t Xml Loaded...");
        }

        if (Log.debug) {
            Log.NowLn("\t\t\t Atlas Loading...");
        }

        for(Map.Entry<String,TextureAtlas> atlas : LoadedAtlasFiles.entrySet())
        {
            TextureAtlas aval= atlas.getValue();
            aval = new TextureAtlas(Gdx.files.internal(atlas.getKey()));
        }
        if (Log.debug) {
            Log.NowLn("\t\t\t Atlas Loaded...");
        }

        if (Log.debug) {
            Log.NowLn("\t\t\t Skin Loading...");
        }
        for(Map.Entry<SkinAtlas,Skin> atlasSkinEntry :  LoadedSkinFiles.entrySet())
        {
            SkinAtlas atlas = atlasSkinEntry.getKey();
            TextureAtlas a = new TextureAtlas(Gdx.files.internal(atlas.atlasPath));
            Skin skin = new Skin(Gdx.files.internal(atlas.skinPath),a);
            atlasSkinEntry.setValue(skin);
        }
        if (Log.debug) {
            Log.NowLn("\t\t\t Skin Loaded...");
        }
        if (Log.debug) {
            Log.NowLn("Loaded...");
        }
    }
}
