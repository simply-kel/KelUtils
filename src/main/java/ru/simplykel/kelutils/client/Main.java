package ru.simplykel.kelutils.client;

import net.dv8tion.jda.api.entities.User;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import ru.simplykel.kelutils.client.config.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.simplykel.kelutils.client.info.*;
import ru.simplykel.kelutils.client.info.Window;
import ru.simplykel.kelutils.client.screens.ConfigScreen;
import ru.simplykel.kelutils.client.discord.Bot;
import ru.simplykel.kelutils.client.lavaplayer.MusicPlayer;
import ru.simplykel.kelutils.client.screens.MusicScreen;
import ru.simplykel.kelutils.client.mixin.NativeImagePointerAccessor;
import ru.simplykel.kelutils.client.screens.PlaylistScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static java.lang.Float.parseFloat;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

public class Main implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("KelUtils");
    public static Boolean simplyStatus = FabricLoader.getInstance().getModContainer("simplystatus").isPresent();
    public static boolean clothConfig = FabricLoader.getInstance().getModContainer("cloth-config").isPresent();
    public static Boolean fastload = FabricLoader.getInstance().getModContainer("fastload").isPresent();
    public static Boolean replayMod = FabricLoader.getInstance().getModContainer("replaymod").isPresent();
    public static UUID bossBarUUID = UUID.randomUUID();
    public static boolean is120Update = false;
    public static DecimalFormat DF = new DecimalFormat("#.##");
    private static final Timer TIMER = new Timer();
    private static String lastException;
    private static boolean lastBossBar = true;
    public static MusicPlayer music;
    @Override
    public void onInitializeClient() {
        LOG.info("[KelLibs/Client] Hello, world!");
        UserConfig.load();
        HUDConfig.load();
        F3Config.load();
        try {
            new Audio();
        } catch (IOException e) {
            e.printStackTrace();
        }
        music = new MusicPlayer();
        music.startAudioOutput();
        // Other
        KeyBinding openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.openconfig",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
                "kelutils.name"
        ));
        // Game
        KeyBinding gammaUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UP, // The keycode of the key
                "kelutils.name.game"
        ));
        KeyBinding gammaDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN, // The keycode of the key
                "kelutils.name.game"
        ));
        KeyBinding gammaToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G, // The keycode of the key
                "kelutils.name.game"
        ));
        KeyBinding swapItemKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.swap",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_MOUSE_BUTTON_5, // The keycode of the key
                "kelutils.name.game"
        ));
        // Music
        KeyBinding loadTrack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.load",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name.music"
        ));
        KeyBinding playOrPause = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.pause",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P, // The keycode of the key
                "kelutils.name.music"
        ));
        KeyBinding skipTrack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.skip",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_MOUSE_BUTTON_4, // The keycode of the key
                "kelutils.name.music"
        ));
        KeyBinding resetQueueKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.reset",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name.music"
        ));
        KeyBinding shuffleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.shuffle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z, // The keycode of the key
                "kelutils.name.music"
        ));
        KeyBinding repeatingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.repeating",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name.music"
        ));
        KeyBinding volumeMusicUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.music.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT, // The keycode of the key
                "kelutils.name.music"
        ));
        KeyBinding volumeMusicDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.music.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT, // The keycode of the key
                "kelutils.name.music"
        ));
        // System
        KeyBinding volumeUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name.system"
        ));
        KeyBinding volumeDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name.system"
        ));
        // KEY BIND
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            UserConfig.load();
            while (playOrPause.wasPressed()){
                music.getAudioPlayer().setPaused(!music.getAudioPlayer().isPaused());
                if(client.player != null) client.player.sendMessage(Localization.getText(music.getAudioPlayer().isPaused() ? "kelutils.music.key.pause" : "kelutils.music.key.play"));
            }
            while (repeatingKey.wasPressed()){
                music.getTrackManager().setRepeating(!music.getTrackManager().isRepeating());
                if(client.player != null) client.player.sendMessage(Localization.getText(music.getTrackManager().isRepeating() ? "kelutils.music.key.repeat" : "kelutils.music.key.repeat.no"));
            }
            while (resetQueueKey.wasPressed()){
                music.getTrackManager().queue.clear();
                if(client.player != null) client.player.sendMessage(Localization.getText("kelutils.music.key.reset"));
            }
            while (shuffleKey.wasPressed()){
                music.getTrackManager().shuffle();
                if(client.player != null) client.player.sendMessage(Localization.getText("kelutils.music.key.shuffle"));
            }
            while (loadTrack.wasPressed()){
                client.setScreen(MusicScreen.buildScreen(client.currentScreen));
            }
            while (skipTrack.wasPressed()){
                music.getTrackManager().nextTrack();
                if(client.player != null) client.player.sendMessage(Localization.getText("kelutils.music.skip"));
            }
            while (volumeMusicUpKey.wasPressed()){
                int current = UserConfig.CURRENT_MUSIC_VOLUME + UserConfig.SELECT_MUSIC_VOLUME;
                if(current >= 100) current = 100;
                UserConfig.CURRENT_MUSIC_VOLUME = current;
                music.getAudioPlayer().setVolume(current);
                UserConfig.save();
            }
            while (volumeMusicDownKey.wasPressed()){
                int current = UserConfig.CURRENT_MUSIC_VOLUME - UserConfig.SELECT_MUSIC_VOLUME;
                if(current <= 1) current = 1;
                UserConfig.CURRENT_MUSIC_VOLUME = current;
                music.getAudioPlayer().setVolume(current);
                UserConfig.save();
            }
            assert client.player != null;
            while (openConfigKey.wasPressed()) {
                if(!Main.clothConfig){
                    client.player.sendMessage(Localization.getText(("kelutils.message.clothConfigNotFound")), false);
                    return;
                }
                final Screen current = client.currentScreen;
                Screen configScreen = ConfigScreen.buildScreen(current);
                client.setScreen(configScreen);
            }
            while (swapItemKey.wasPressed()){
                if(!client.isInSingleplayer() && client.getCurrentServerEntry() != null){
                    if(ServerConfig.FOLLOW_SWAP_ITEM) Player.swapItem(client);
                    else client.player.sendMessage(Localization.getText(("kelutils.message.dontFollowSwap")), false);
                } else Player.swapItem(client);
            }
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
            while (volumeDownKey.wasPressed()){
                try {
                    Audio.downValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            while (volumeUpKey.wasPressed()){
                try {
                    Audio.upValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            client.getTutorialManager().setStep(TutorialStep.NONE);
            is120Update = MinecraftClient.getInstance().getGameVersion().startsWith("1.20");
            try {
                Window.setIcon(client);
            } catch (Exception e){
                e.printStackTrace();
            }
            start();
            if(UserConfig.DISCORD_USE) {
                try {
                    startDiscord();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
                dispatcher.register(ClientCommandManager.literal("playlist")
                                .then(
                                        argument("name", greedyString()).executes(context -> {
                                            if(!Main.clothConfig){
                                                context.getSource().getPlayer().sendMessage(Localization.getText(("kelutils.message.clothConfigNotFound")), false);
                                            } else {
                                                final Screen current = MinecraftClient.getInstance().currentScreen;
                                                Screen configScreen = new PlaylistScreen().buildScreen(current, getString(context, "name"));
//                                                MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(configScreen));
                                                MinecraftClient client = context.getSource().getClient();
                                                client.send(() -> client.setScreen(configScreen));
                                            }
                                            return 1;
                                        })
                                )
                );
            dispatcher.register(ClientCommandManager.literal("nethercoords").executes(context ->
            {
                if(World.getCodeName().equals("minecraft:nether")){
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.nether"));
                    context.getSource().getPlayer().sendMessage(Localization.toText("X: " + parseFloat(Player.getX())*8 + " Z: " + parseFloat(Player.getZ())*8));
                    return 1;
                } else if(World.getCodeName().equals("minecraft:overworld")){
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.nether.overworld"));
                    context.getSource().getPlayer().sendMessage(Localization.toText("X: " + parseFloat(Player.getX())/8 + " Z: " + parseFloat(Player.getZ())/8));
                    return 1;
                } else {
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.nether.worldNotSupport"));
                    return 1;
                }
            }));
            dispatcher.register(ClientCommandManager.literal("kelutilsreload").executes(context ->
            {
                context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.start"));
                // Main
                try{
                    UserConfig.load();
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.done.user"));
                } catch (Exception ex){
                    Main.LOG.error("[KelUtils/Client/UserConfig] Error: "+ex.getMessage()+" | Full Error:");
                    ex.printStackTrace();
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.error.user"));
                }
                // F3
                try{
                    F3Config.load();
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.done.f3"));
                } catch (Exception ex){
                    Main.LOG.error("[KelUtils/Client/F3Config] Error: "+ex.getMessage()+" | Full Error:");
                    ex.printStackTrace();
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.error.f3"));
                }
                // HUD
                try{
                    HUDConfig.load();
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.done.hud"));
                } catch (Exception ex){
                    Main.LOG.error("[KelUtils/Client/HUDConfig] Error: "+ex.getMessage()+" | Full Error:");
                    ex.printStackTrace();
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.error.hud"));
                }
                // Discord
                try{
                    Bot.jda.shutdown();
                    DiscordConfig.load();
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.done.discord"));
                    if(UserConfig.DISCORD_USE) Bot.start();
                } catch (Exception ex){
                    Main.LOG.error("[KelUtils/Client/DiscordConfig] Error: "+ex.getMessage()+" | Full Error:");
                    ex.printStackTrace();
                    context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.error.discord"));
                }
                // Server
                if(context.getSource().getClient().getCurrentServerEntry() != null && !context.getSource().getClient().isInSingleplayer()){
                    try{
                        ServerConfig.load();
                        context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.done.server"));
                    } catch (Exception ex){
                        Main.LOG.error("[KelUtils/Client/ServerConfig] Error: "+ex.getMessage()+" | Full Error:");
                        ex.printStackTrace();
                        context.getSource().getPlayer().sendMessage(Localization.getText("kelutils.message.reload.error.server"));
                    }
                }
                return 1;
            }));
    });
        
    }
    // STARTING FUNCTIONS
    public static void start(){
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(UserConfig.ENABLE_HUD_INFORMATION) updateHUD();
                if(UserConfig.ENABLE_BOSSBAR_INFORMATION) updateBossBar();
                else if(lastBossBar) clearBossBar();
                if(UserConfig.USE_CUSTOM_TITLE) updateTitle();
            }
        }, 250, 250);
    }
    public static void startDiscord() throws InterruptedException {
        DiscordConfig.load();
        if(DiscordConfig.DISCORD_TOKEN.isBlank()){
            LOG.error("Discord Token is blank! Start canceled!");
            return;
        }
        Bot.start();
    }
    public static void updateTitle(){
        MinecraftClient client = MinecraftClient.getInstance();
        
        if(UserConfig.USE_CUSTOM_TITLE){
            if(client.world != null && client.player != null){
                if(client.getCurrentServerEntry() != null && !client.isInSingleplayer()){
                    client.getWindow().setTitle(Localization.getLocalization("title.multiplayer", true, true));
                } else if(client.isInSingleplayer()){
                    client.getWindow().setTitle(Localization.getLocalization("title.singleplayer", true, true));
                } else if(Main.replayMod){
                    client.getWindow().setTitle(Localization.getLocalization("title.replaymod", true, true));
                } else {
                    client.getWindow().setTitle(Localization.getLocalization("title.unknown", true, true));
                }
            } else {
                switch (Game.getGameState()){
                    case 1 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.loading", true, true));
                    case 2 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.connect", true, true));
                    case 3 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.disconnect", true, true));
                    case 4 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.options", true, true));
                    case 5 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.multiplayer", true, true));
                    case 6 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.multiplayer.add", true, true));
                    case 7 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.world", true, true));
                    case 8 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.world.create", true, true));
                    case 9 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.world.edit", true, true));
                    case 10 -> client.getWindow().setTitle(Localization.getLocalization("title.menu.world.optimize", true, true));
                    default -> client.getWindow().setTitle(Localization.getLocalization("title.menu", true, true));
                }
            }
        }
    }

    public static void updateHUD(){
        try{
            MinecraftClient CLIENT = MinecraftClient.getInstance();
            if(CLIENT.world != null && CLIENT.player != null){
                if(!CLIENT.player.isSleeping()) CLIENT.player.sendMessage(Localization.toText(Localization.getLocalization("hud", true, false)), true);
            }
            if(lastException != null) lastException = null;
        } catch (Exception ex){
            if(lastException == null || !lastException.equals(ex.getMessage())){
                ex.printStackTrace();
                lastException = ex.getMessage();
            }
        }
    }
    public static ClientBossBar bossBar;
    public static String[] bossBarTypes = {
            "fps",
            "health",
            "food",
            "world_time",
            "real_time",
            "hide"
    };
    public static void updateBossBar(){
        if(!lastBossBar) lastBossBar = true;
        boolean music = false;
        try{
            MinecraftClient client = MinecraftClient.getInstance();
            if(client.world != null && client.player != null) {
                if(Main.music.getAudioPlayer().getPlayingTrack() != null){
                    if(Main.music.getAudioPlayer().isPaused()){
                        bossBar = new ClientBossBar(Main.bossBarUUID, Localization.toText(Localization.getLocalization("bossbar.music.pause", true, false)), 1F
                                , BossBar.Color.YELLOW, BossBar.Style.PROGRESS,false, false, false);
                    } else {
                        if(Main.music.getAudioPlayer().getPlayingTrack().getInfo().isStream)
                            bossBar = new ClientBossBar(Main.bossBarUUID, Localization.toText(Localization.getLocalization("bossbar.music.live", true, false)), (
                                    1F
                            ), BossBar.Color.RED, BossBar.Style.PROGRESS,false, false, false);
                        else bossBar = new ClientBossBar(Main.bossBarUUID, Localization.toText(Localization.getLocalization("bossbar.music", true, false)), (
                                (float) Main.music.getAudioPlayer().getPlayingTrack().getPosition()/Main.music.getAudioPlayer().getPlayingTrack().getDuration()
                        ), BossBar.Color.GREEN, BossBar.Style.PROGRESS,false, false, false);
                    }
                    music = true;
                } else {
                    float percent = 1F;
                    BossBar.Color color = BossBar.Color.BLUE;
                    switch (UserConfig.BOSSBAR_TYPE) {
                        case "fps" -> {
                            percent = (float) client.getCurrentFps() / client.options.getMaxFps().getValue();
                            if (percent > 1f) percent = 1f;
                            color = percent <= 0.25f ? BossBar.Color.RED : percent <= 0.75f ? BossBar.Color.YELLOW : BossBar.Color.GREEN;
                        }
                        case "health" -> {
                            percent = (float) client.player.getHealth() / client.player.getMaxHealth();
                            if (percent > 1f) percent = 1f;
                            color = BossBar.Color.RED;
                        }
                        case "food" -> {
                            int current = client.player.getHungerManager().getFoodLevel();
                            int max = client.player.getHungerManager().getPrevFoodLevel();
                            percent = (float) current/max;
                            if (percent > 1f) percent = 1f;
                            color = percent <= 0.3f ? BossBar.Color.RED : percent <= 0.6f ? BossBar.Color.YELLOW : BossBar.Color.GREEN;
                        }
                        case "world_time" -> {
                            String type = World.getCodeName();
                            long currentTime = client.world.getLunarTime() % 24000;
                            percent = (float) currentTime / 24000;
                            if (percent > 1f) percent = 1f;
                            if(type.equals("minecraft:the_moon")) {
                                color = BossBar.Color.WHITE;
                            } else if(type.equals("minecraft:the_end")) {
                                color = BossBar.Color.PURPLE;
                            } else if(type.equals("minecraft:the_nether")) {
                                color = BossBar.Color.RED;
                            } else if(type.equals("minecraft:overworld")){
                                if (currentTime < 6000 && currentTime > 0) {
                                    color = BossBar.Color.YELLOW;
                                } else if (currentTime < 12000 && currentTime > 6000) {
                                    color = BossBar.Color.GREEN;
                                } else if (currentTime < 16500 && currentTime > 12000) {
                                    color = BossBar.Color.YELLOW;
                                } else if (currentTime > 16500) {
                                    color = BossBar.Color.RED;
                                } else {
                                    color = BossBar.Color.WHITE;
                                }
                            } else {
                                color = BossBar.Color.BLUE;
                            }
                        }
                        case "real_time" -> {
                            int timezone = 0;
                            boolean isMinus = false;
                            if(UserConfig.TIME_ZONE <= -1) {
                                isMinus = true;
                                timezone = (UserConfig.TIME_ZONE * -1) * 3600000;
                            } else timezone = UserConfig.TIME_ZONE * 3600000;
                            long currentTime = 0;
                            if(isMinus) currentTime = (System.currentTimeMillis()-timezone) % 86400000;
                            else currentTime = (System.currentTimeMillis()+timezone) % 86400000;
                            percent = (float) currentTime / 86400000;
                            if (percent > 1f) percent = 1f;
                            if (currentTime < 39600000 && currentTime > 0) {
                                color = BossBar.Color.YELLOW;
                            } else if (currentTime < 61200000 && currentTime > 39600000) {
                                color = BossBar.Color.GREEN;
                            } else if (currentTime < 79200000 && currentTime > 61200000) {
                                color = BossBar.Color.YELLOW;
                            } else if (currentTime > 79200000) {
                                color = BossBar.Color.RED;
                            } else {
                                color = BossBar.Color.WHITE;
                            }
                        }
                    }
                    bossBar = new ClientBossBar(Main.bossBarUUID, Localization.toText(Localization.getLocalization("bossbar", true, false)), (
                            percent
                    ), color, BossBar.Style.NOTCHED_12,false, false, false);
                }
                if(UserConfig.BOSSBAR_TYPE.equals("hide") && !music) client.inGameHud.getBossBarHud().handlePacket(BossBarS2CPacket.remove(bossBarUUID));
                else client.inGameHud.getBossBarHud().handlePacket(BossBarS2CPacket.add(bossBar));
            }
            if(lastException != null) lastException = null;
        } catch (Exception ex){
            if(lastException == null || !lastException.equals(ex.getMessage())){
                ex.printStackTrace();
                lastException = ex.getMessage();
            }
        }
    }
    public static void clearBossBar(){
        try{
            if(lastBossBar) lastBossBar = false;
            MinecraftClient client = MinecraftClient.getInstance();
            if(client.world != null && client.player != null) {
                client.inGameHud.getBossBarHud().handlePacket(BossBarS2CPacket.remove(bossBarUUID));
            }
            if(lastException != null) lastException = null;
        } catch (Exception ex){
            if(lastException == null || !lastException.equals(ex.getMessage())){
                ex.printStackTrace();
                lastException = ex.getMessage();
            }
        }
    }
    // SCREENSHOT
    public static void handleScreenshotAWT(NativeImage img) {
        if (MinecraftClient.IS_SYSTEM_MAC) {
            return;
        }

        // Only allow RGBA
        if (img.getFormat() != NativeImage.Format.RGBA) {
            LOG.warn("Failed to capture screenshot: wrong format");
            return;
        }

        // IntellIJ doesn't like this
        //noinspection ConstantConditions
        long imagePointer = ((NativeImagePointerAccessor) (Object) img).getPointer();
        ByteBuffer buf = MemoryUtil.memByteBufferSafe(imagePointer, img.getWidth() * img.getHeight() * 4);
        if (buf == null) {
            throw new RuntimeException("Invalid image");
        }

        handleScreenshotAWT(buf, img.getWidth(), img.getHeight(), 4);
    }

    public static void handleScreenshotAWT(ByteBuffer byteBuffer, int width, int height, int components) {
        if (MinecraftClient.IS_SYSTEM_MAC) {
            return;
        }

        byte[] array;
        if (byteBuffer.hasArray()) {
            array = byteBuffer.array();
        } else {
            // can't use .array() as the buffer is not array-backed
            array = new byte[height * width * components];
            byteBuffer.get(array);
        }

        doCopy(array, width, height, components);
    }

    private static void doCopy(byte[] imageData, int width, int height, int components) {
        new Thread(() -> {
            DataBufferByte buf = new DataBufferByte(imageData, imageData.length);
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
            // Ignore the alpha channel, due to JDK-8204187
            int[] nBits = {8, 8, 8};
            int[] bOffs = {0, 1, 2}; // is this efficient, no transformation is being done?
            ColorModel cm = new ComponentColorModel(cs, nBits, false, false,
                    Transparency.TRANSLUCENT,
                    DataBuffer.TYPE_BYTE);
            BufferedImage bufImg = new BufferedImage(cm, Raster.createInterleavedRaster(buf,
                    width, height,
                    width * components, components,
                    bOffs, null), false, null);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufImg, "png", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                Bot.sendScreenshot(is);
            } catch (IOException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "KelUtils").start();
    }
    // SCREENSHOT DEAD
    public static void handleScreenshotAWT(NativeImage img, Text msg) {
        if (MinecraftClient.IS_SYSTEM_MAC) {
            return;
        }

        // Only allow RGBA
        if (img.getFormat() != NativeImage.Format.RGBA) {
            Main.LOG.warn("Failed to capture screenshot: wrong format");
            return;
        }

        // IntellIJ doesn't like this
        //noinspection ConstantConditions
        long imagePointer = ((NativeImagePointerAccessor) (Object) img).getPointer();
        ByteBuffer buf = MemoryUtil.memByteBufferSafe(imagePointer, img.getWidth() * img.getHeight() * 4);
        if (buf == null) {
            throw new RuntimeException("Invalid image");
        }

        handleScreenshotAWT(buf, img.getWidth(), img.getHeight(), 4, msg);
    }

    public static void handleScreenshotAWT(ByteBuffer byteBuffer, int width, int height, int components, Text msg) {
        if (MinecraftClient.IS_SYSTEM_MAC) {
            return;
        }

        byte[] array;
        if (byteBuffer.hasArray()) {
            array = byteBuffer.array();
        } else {
            // can't use .array() as the buffer is not array-backed
            array = new byte[height * width * components];
            byteBuffer.get(array);
        }

        doCopy(array, width, height, components, msg);
    }

    private static void doCopy(byte[] imageData, int width, int height, int components, Text msg) {
        new Thread(() -> {
            DataBufferByte buf = new DataBufferByte(imageData, imageData.length);
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
            // Ignore the alpha channel, due to JDK-8204187
            int[] nBits = {8, 8, 8};
            int[] bOffs = {0, 1, 2}; // is this efficient, no transformation is being done?
            ColorModel cm = new ComponentColorModel(cs, nBits, false, false,
                    Transparency.TRANSLUCENT,
                    DataBuffer.TYPE_BYTE);
            BufferedImage bufImg = new BufferedImage(cm, Raster.createInterleavedRaster(buf,
                    width, height,
                    width * components, components,
                    bOffs, null), false, null);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufImg, "png", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                Bot.sendScreenshotDeath(is, msg);
            } catch (IOException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "KelUtils").start();
    }
}
