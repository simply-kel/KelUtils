package ru.simplykel.kelutils.config;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.json.JSONObject;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.info.Audio;
import ru.simplykel.kelutils.info.Game;
import ru.simplykel.kelutils.info.Player;
import ru.simplykel.kelutils.info.World;
import ru.simplykel.kelutils.mixin.MinecraftClientAccess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Localization {
    /**
     * Получение кода локализации игры который выбрал игрок<br>
     * При запуске может быть null, поэтому иногда en_us;
     */
    public static String getCodeLocalization(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        try{
//            return CLIENT.getGame().getSelectedLanguage().getCode();
            return CLIENT.options.language;
        } catch (Exception e){
            return "en_us";
        }
    }

    /**
     * Получение JSON файл локализации
     * @return JSONObject
     * @throws IOException
     */
    public static JSONObject getJSONFile() throws IOException {
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        File localizationFile = new File(CLIENT.runDirectory + "/KelUtils/lang/"+getCodeLocalization()+".json");
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
        String text = "";
        try {
            JSONObject JSONLocalization = getJSONFile();
            if(JSONLocalization.isNull(type)) text = getText("kelutils." + type).getString();
            else text = JSONLocalization.getString(type);
        } catch (Exception e){
            e.printStackTrace();
            text = getText("kelutils." + type).getString();
        }
        if(parse) return getParsedText(text);
        else return text;
    }
    public static String getLcnDefault(String type){
        String text = getText("kelutils." + type).getString();;
        return text;
    }
    /**
     * Задать значение локализации на определённый текст в JSON файле
     * @param type
     * @param text
     */
    public static void setLocalization(String type, String text){
        try {
            JSONObject JSONLocalization = getJSONFile();
            JSONLocalization.put(type, text);
            MinecraftClient CLIENT = MinecraftClient.getInstance();
            File localizationFile = new File(CLIENT.runDirectory + "/KelUtils/lang/"+getCodeLocalization()+".json");
            Files.createDirectories(localizationFile.toPath().getParent());
            Files.writeString(localizationFile.toPath(), JSONLocalization.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
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
    public static String getParsedText(String text){
        String parsedText = text;
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        parsedText = parsedText.replace("%version%", CLIENT.getGameVersion());
        parsedText = parsedText.replace("%modded%", ClientBrandRetriever.getClientModName());
        parsedText = parsedText.replace("%version_type%", ("release".equalsIgnoreCase(CLIENT.getVersionType()) ? "" : CLIENT.getVersionType()));
        if(Main.simplyStatus){
            if(ru.simplykel.simplystatus.Client.CONNECTED_DISCORD) {
                parsedText = parsedText.replace("%discord_name%", ru.simplykel.simplystatus.Client.USER.username);
                parsedText = parsedText.replace("%discord_discriminator%", ru.simplykel.simplystatus.Client.USER.discriminator);
                parsedText = parsedText.replace("%discord_id%", ru.simplykel.simplystatus.Client.USER.userId);
                parsedText = parsedText.replace("%discord_tag%", ru.simplykel.simplystatus.Client.USER.username + "#" + ru.simplykel.simplystatus.Client.USER.discriminator);

                // Данные функции связаны с замены discord на siscord
                parsedText = parsedText.replace("%siscord_name%", ru.simplykel.simplystatus.Client.USER.username);
                parsedText = parsedText.replace("%siscord_discriminator%", ru.simplykel.simplystatus.Client.USER.discriminator);
                parsedText = parsedText.replace("%siscord_id%", ru.simplykel.simplystatus.Client.USER.userId);
                parsedText = parsedText.replace("%siscord_tag%", ru.simplykel.simplystatus.Client.USER.username+"#"+ru.simplykel.simplystatus.Client.USER.discriminator);
            }
        }
        parsedText = parsedText.replace("%name%", CLIENT.getSession().getUsername());
        switch (Game.getGameState()){
            case 0 -> parsedText = parsedText.replace("%screen%", "%scene%");
            case 1 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.loading", false));
            case 2 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.connect", false));
            case 3 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.disconnect", false));
            case 4 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.options", false));
            case 5 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.multiplayer", false));
            case 6 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.multiplayer.add", false));
            case 7 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.world", false));
            case 8 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.world.create", false));
            case 9 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.world.edit", false));
            case 10 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.world.optimize", false));
            case 11 -> parsedText = parsedText.replace("%screen%", Localization.getLocalization("screens.title", false));
        }
        if(CLIENT.world != null && CLIENT.player != null){
            if(Player.getItemName() == null) parsedText = parsedText.replace("%item%", "");
            if(Player.getItemName() == null) parsedText = parsedText.replace("%item_bot%", "");
            parsedText = parsedText.replace("%item%", Localization.getLocalization("item.format", false));
            parsedText = parsedText.replace("%item_bot%", Localization.getLocalization("item.format.bot", false));
            parsedText = parsedText.replace("%item_name%", Player.getItemName()+"");
            if(Player.getItemCount() >= 2) {
                parsedText = parsedText.replace("%item_pcs%", Localization.getLocalization("item.format.count", false));
                parsedText = parsedText.replace("%item_count%", Player.getItemCount() + "");
            } else {
                parsedText = parsedText.replace("%item_pcs%", "");
                parsedText = parsedText.replace("%item_count%", "");
            }
            parsedText = parsedText.replace("%x%", Player.getX());
            parsedText = parsedText.replace("%y%", Player.getY());
            parsedText = parsedText.replace("%z%", Player.getZ());
            if(!CLIENT.isInSingleplayer() && CLIENT.getCurrentServerEntry() != null){
                ServerConfig.load();
                parsedText = parsedText.replace("%scene%", "%address%");
                if(ServerConfig.SHOW_ADDRESS){
                    if(ServerConfig.SHOW_CUSTOM_NAME) parsedText = parsedText.replace("%address%", ServerConfig.CUSTOM_NAME);
                    else if(ServerConfig.SHOW_NAME_IN_LIST) parsedText = parsedText.replace("%address%", CLIENT.getCurrentServerEntry().name);
                    else parsedText = parsedText.replace("%address%", CLIENT.getCurrentServerEntry().address);
                } else parsedText = parsedText.replace("%address%", getLocalization("address.hidden", false));
            } else parsedText = parsedText.replace("%scene%", Localization.getLocalization("singleplayer", false));
            parsedText = parsedText.replace("%health%", Player.getHealth());
            parsedText = parsedText.replace("%health_max%", Player.getMaxHealth());
            parsedText = parsedText.replace("%health_percent%", Player.getPercentHealth());
            parsedText = parsedText.replace("%armor%", Player.getArmor());
            parsedText = parsedText.replace("%xp%", String.valueOf(CLIENT.player.experienceLevel));
            parsedText = parsedText.replace("%gamma%", Main.DF.format(CLIENT.options.getGamma().getValue()*100));
            parsedText = parsedText.replace("%sps%", MinecraftClientAccess.getCurrentFps()+"FPS");
            parsedText = parsedText.replace("%fps%", MinecraftClientAccess.getCurrentFps()+"FPS");
            parsedText = parsedText.replace("%world%", World.getName());
            parsedText = parsedText.replace("%world_time%", World.getTime());
            parsedText = parsedText.replace("%time%", World.getTime());
            parsedText = parsedText.replace("%near_players%", String.valueOf(CLIENT.world.getPlayers().size()));
            parsedText = parsedText.replace("%near_mobs%", String.valueOf(CLIENT.world.getEntities().spliterator().estimateSize() - CLIENT.world.getPlayers().size()));
            parsedText = parsedText.replace("%near_entities%", String.valueOf(CLIENT.world.getEntities().spliterator().estimateSize()));
        }
        try{
            DateFormat dateFormat = new SimpleDateFormat(Localization.getLocalization("date", false));
            DateFormat timeFormat = new SimpleDateFormat(Localization.getLocalization("date.time", false));
            parsedText = parsedText.replace("%date%", Localization.getLocalization("date.format", false));
            parsedText = parsedText.replace("%sate%", Localization.getLocalization("date.format", false));
            parsedText = parsedText.replace("%date_format%", dateFormat.format(System.currentTimeMillis()));
            parsedText = parsedText.replace("%sate_format%", dateFormat.format(System.currentTimeMillis()));
            parsedText = parsedText.replace("%time_format%", timeFormat.format(System.currentTimeMillis()));
            parsedText = parsedText.replace("%system_volume%", String.valueOf(Audio.getValue()));
        } catch (Exception e) {

        }
        return parsedText;
    }
}
