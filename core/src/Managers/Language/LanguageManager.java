package Managers.Language;

import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;

import Logger.Log;
import Managers.Manager;


public class LanguageManager extends Manager {

    public String defaultLocale;
    public ArrayList<Language> Languages = new ArrayList<Language>();

    @Override
    public void parseXml(XmlReader.Element value) {

        defaultLocale = value.getAttribute("defaultLocale");
        if(Log.debug) {
            Log.NowLn("\n\t\t\t DefaultLocale : " + defaultLocale);
        }
        XmlReader.Element languages = value.getChildByName("Languages");
        XmlReader.Element translations = value.getChildByName("Translations");

        for (int x = 0; x < languages.getChildCount(); x++) {
            Language language = new Language();
            XmlReader.Element languageXmlElement = languages.getChild(x);
            language.name = languageXmlElement.getAttribute("name");
            language.locale = languageXmlElement.getAttribute("locale");
            language.iconPath = languageXmlElement.getAttribute("iconPath");

            for (int y = 0; y < translations.getChildCount(); y++) {
                XmlReader.Element xmlTranslation = translations.getChild(y);

                Translation translation = new Translation();
                for (int b = 0; b < xmlTranslation.getChildCount(); b++) {
                    XmlReader.Element xmlText = xmlTranslation.getChild(b);
                    if (language.locale.equals(xmlText.getAttribute("locale"))) {
                        Text text = new Text();
                        text.locale = xmlText.getAttribute("locale");
                        text.value = xmlText.getText();
                        translation.Texts.add(text);
                    }
                }

                language.Translations.add(translation);
            }
            Languages.add(language);
        }
        if(Log.debug) {
            for (Language l : Languages) {
                Log.NowLn("\t\t\t\t\t\t" + l.toString());
            }
        }
    }
}
