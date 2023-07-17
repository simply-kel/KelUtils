package ru.simplykel.kelutils.client.info;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Icons;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.kelutils.client.config.Localization;
import ru.simplykel.kelutils.client.config.UserConfig;

import java.io.IOException;

public class Window {
    public static String lastTitle = "";
    public static void setIcon(MinecraftClient client) throws IOException {
        if(Main.is120Update) {
            MinecraftClient.getInstance().getWindow().setIcon(
                    client.getDefaultResourcePack(), UserConfig.ICON_SNAPSHOT ? Icons.SNAPSHOT : Icons.RELEASE
            );
        }
    }
    public static void setTitle(MinecraftClient client){
        StringBuilder title = new StringBuilder().append(Localization.getLocalization("title", true));
        if(client.world != null && client.player != null){
            if(client.isInSingleplayer()) title.append(Localization.getLocalization("title.singleplayer", true));
            else if(client.getCurrentServerEntry() != null) title.append(Localization.getLocalization("title.multiplayer", true));
            else if(Main.replayMod) title.append(Localization.getLocalization("title.replaymod", true));
        } else {
            if(Game.getGameState() == 1) title.append(Localization.getLocalization("title.loading", true));
            else if(Game.getGameState() == 2) title.append(Localization.getLocalization("title.connect", true));
            else if(Game.getGameState() == 3) title.append(Localization.getLocalization("title.disconnect", true));
            else title.append(Localization.getLocalization("title.menu", true));
        }
        if(lastTitle.contentEquals(title)) return;
        client.getWindow().setTitle(title.toString());
        lastTitle = title.toString();
    }
}