package ru.simplykel.kelutils.server.config;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.json.JSONObject;
import ru.simplykel.kelutils.server.Main;
import ru.simplykel.kelutils.common.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Localization {

    /**
     * Получение JSON файл локализации
     * @return JSONObject
     * @throws IOException
     */
    public static JSONObject getJSONFile() throws IOException {
        MinecraftServer server = Main.server;
        File localizationFile = new File(server.getRunDirectory() + "/KelUtils/lang.json");
        if(localizationFile.exists()){
            return new JSONObject(Files.readString(localizationFile.toPath()));
        } else {
            return new JSONObject();
        }
    }

    /**
     * Получение текста локализации
     * @param type
     * @param parse
     * @return String
     */
    public static String getLocalization(String type, boolean parse){
        return getLocalization(type, parse, true);
    }
    public static String getLocalization(String type, boolean parse, boolean clearColor){
        String text = "";
        try {
            JSONObject JSONLocalization = getJSONFile();
            if(JSONLocalization.isNull(type)) text = getText("kelutils." + type).getString();
            else text = JSONLocalization.getString(type);
        } catch (Exception e){
            e.printStackTrace();
            text = getText("kelutils." + type).getString();
        }
        if(parse) return getParsedText(text, clearColor);
        else return text;
    }
    public static String getLcnDefault(String type){
        String text = getText("kelutils." + type).getString();;
        return text;
    }
    /**
     * Хуета которая может быть спасёт от Mojang которые сука постоянно меняют либо название класса либо еще что-то
     * @return MutableText
     * @param key
     */
    public static MutableText getText(String key){
        return Text.translatable(key);
    }

    /**
     * Перевод String в MutableText
     * @param text
     * @return MutableText
     */
    public static MutableText toText(String text){
        return Text.literal(text);
    }

    /**
     * Перевод Text в String
     * @param text
     * @return MutableText
     */
    public static String toString(Text text){
        return text.getString();
    }


    /**
     * Парс текста
     * @param text
     * @return String
     */
    public static String getParsedText(String text, boolean clearColor){
        String parsedText = text;
        MinecraftServer server = Main.server;
        parsedText = parsedText.replace("%version%", server.getVersion());

        parsedText = parsedText.replace("%address%", "%ip%:%port%");
        parsedText = parsedText.replace("%ip%", server.getServerIp());
        parsedText = parsedText.replace("%port%", server.getServerPort()+"");
        parsedText = parsedText.replace("%name%", server.getName());

        if(clearColor) parsedText = Utils.clearFormatCodes(parsedText);
        else parsedText = Utils.fixFormatCodes(parsedText);
        return parsedText;
    }
}
