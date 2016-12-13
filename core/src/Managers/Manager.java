package Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Logger.Log;


public abstract class Manager {

    public abstract void parseXml(XmlReader.Element value);
}

