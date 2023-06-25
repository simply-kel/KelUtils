package ru.simplykel.kelutils.config.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Util;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.config.gui.ConfigScreen;
import net.minecraft.client.gui.screen.*;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(Main.clothConfig) {
            return ConfigScreen::buildScreen;
        } else {
            return null;
        }
    }

}
