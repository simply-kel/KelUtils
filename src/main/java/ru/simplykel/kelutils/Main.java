package ru.simplykel.kelutils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import ru.simplykel.kelutils.config.DiscordConfig;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.UserConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Main implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("KelUtils");
    public static Boolean simplyStatus = FabricLoader.getInstance().getModContainer("simplystatus").isPresent();
    public static boolean clothConfig = FabricLoader.getInstance().getModContainer("cloth-config").isPresent();
    public static DecimalFormat DF = new DecimalFormat("#.#");
    private static Timer TIMER = new Timer();
    @Override
    public void onInitializeClient() {
        LOG.info("[KelLibs] Hello, world!");
        UserConfig.load();
        KeyBinding gammaUpKey;
        gammaUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UP, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding gammaDownKey;
        gammaDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding gammaToggleKey;
        gammaToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G, // The keycode of the key
                "kelutils.name"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            UserConfig.load();
            assert client.player != null;
            while (gammaUpKey.wasPressed()) {
                double current = UserConfig.CURRENT_GAMMA_VOLUME + 0.1;
                if(current >= 15.0) current = 15.0;
                UserConfig.CURRENT_GAMMA_VOLUME = current;
                UserConfig.GAMMA_ACTIVATED = current != UserConfig.NORMAL_GAMMA_VOLUME;
                client.options.getGamma().setValue(current);
                UserConfig.save();
            }
            while (gammaDownKey.wasPressed()) {
                double current = UserConfig.CURRENT_GAMMA_VOLUME - 0.1;
                if(current <= 0) current = 0;
                UserConfig.CURRENT_GAMMA_VOLUME = current;
                UserConfig.GAMMA_ACTIVATED = current != UserConfig.NORMAL_GAMMA_VOLUME;
                client.options.getGamma().setValue(current);
                UserConfig.save();
            }
            while (gammaToggleKey.wasPressed()) {
                UserConfig.GAMMA_ACTIVATED = !UserConfig.GAMMA_ACTIVATED;
                if(UserConfig.GAMMA_ACTIVATED){
                    if(UserConfig.SELECT_GAMMA_VOLUME > 15.0) UserConfig.SELECT_GAMMA_VOLUME = 15.0;
                    if(UserConfig.SELECT_GAMMA_VOLUME < 0) UserConfig.SELECT_GAMMA_VOLUME = 0;
                    UserConfig.CURRENT_GAMMA_VOLUME = UserConfig.SELECT_GAMMA_VOLUME;
                    client.options.getGamma().setValue(UserConfig.SELECT_GAMMA_VOLUME);
                } else {
                    if(UserConfig.NORMAL_GAMMA_VOLUME > 15.0) UserConfig.NORMAL_GAMMA_VOLUME = 15.0;
                    if(UserConfig.NORMAL_GAMMA_VOLUME < 0) UserConfig.NORMAL_GAMMA_VOLUME = 0;
                    UserConfig.CURRENT_GAMMA_VOLUME = UserConfig.NORMAL_GAMMA_VOLUME;
                    client.options.getGamma().setValue(UserConfig.NORMAL_GAMMA_VOLUME);
                }
                UserConfig.save();
            }
        });
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            client.options.getGamma().setValue(UserConfig.CURRENT_GAMMA_VOLUME);
            start();
            if(UserConfig.DISCORD_USE && !UserConfig.DISCORD_TOKEN.isBlank()) startDiscord();
        });
    }
    public void start(){
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(UserConfig.ENABLE_HUD_INFORMATION) updateHUD();
            }
        }, 250, 250);
    }
    public void startDiscord(){
        DiscordConfig.load();
        if(DiscordConfig.DISCORD_TOKEN.isBlank()){
            LOG.error("Discord Token is blank! Start canceled!");
            return;
        }
    }
    public void updateHUD(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        if(CLIENT.world != null && CLIENT.player != null){
            CLIENT.player.sendMessage(Localization.toText(Localization.getLocalization("hud", true)), true);
        }
    }
}
