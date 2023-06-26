package ru.simplykel.kelutils.config.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.simplykel.kelutils.Main;

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
