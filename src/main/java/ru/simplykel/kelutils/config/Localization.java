package ru.simplykel.kelutils.config;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
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
import ru.simplykel.kelutils.lavaplayer.MusicPlayer;
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
        MusicPlayer music = Main.music;
        parsedText = parsedText.replace("%music%", music.getAudioPlayer().getPlayingTrack() == null ? "" : music.getAudioPlayer().isPaused() ? Localization.getLocalization("music.pause", false) : Localization.getLocalization("music.format", false));
        if(music.getAudioPlayer().getPlayingTrack() != null) {
            AudioTrackInfo info = music.getAudioPlayer().getPlayingTrack().getInfo();
            parsedText = parsedText.replace("%music_author_format%", info.author.equals("Unknown artist") ? "" : Localization.getLocalization("music.format.author", false));
            parsedText = parsedText.replace("%music_author%", info.author);
            parsedText = parsedText.replace("%music_title%", info.title);
            parsedText = parsedText.replace("%music_volume%", String.valueOf(music.getAudioPlayer().getVolume()));
            if (music.getAudioPlayer().getPlayingTrack().getInfo().isStream) {
                parsedText = parsedText.replace("%music_time_format%", Localization.getLocalization("music.live", false));
            } else{
                parsedText = parsedText.replace("%music_time_format%", Localization.getLocalization("music.time.format", false));
                parsedText = parsedText.replace("%music_time%", getTimestamp(music.getAudioPlayer().getPlayingTrack().getPosition()));
                parsedText = parsedText.replace("%music_time_max%", getTimestamp(music.getAudioPlayer().getPlayingTrack().getDuration()));
            }
        }
        if(Main.simplyStatus){
            parsedText = ru.simplykel.simplystatus.config.Localization.getParsedText(parsedText);
        }
        return parsedText;
    }
    public static String getTimestamp(long milliseconds)
    {
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours   = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        if (hours > 0)
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else
            return String.format("%02d:%02d", minutes, seconds);
    }
}
