package ru.simplykel.kelutils;

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
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import ru.simplykel.kelutils.config.DiscordConfig;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.UserConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.simplykel.kelutils.info.Player;
import ru.simplykel.kelutils.screens.ConfigScreen;
import ru.simplykel.kelutils.discord.Bot;
import ru.simplykel.kelutils.info.Audio;
import ru.simplykel.kelutils.info.Window;
import ru.simplykel.kelutils.lavaplayer.MusicPlayer;
import ru.simplykel.kelutils.screens.MusicScreen;
import ru.simplykel.kelutils.mixin.NativeImagePointerAccessor;
import ru.simplykel.kelutils.screens.PlaylistScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
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
        LOG.info("[KelLibs] Hello, world!");
        UserConfig.load();
        try {
            new Audio();
        } catch (IOException e) {
            e.printStackTrace();
        }
        music = new MusicPlayer();
        music.startAudioOutput();
        KeyBinding openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.openconfig",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding gammaUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UP, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding gammaDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding gammaToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.gamma.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding volumeUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding volumeDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding playOrPause = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.pause",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding loadTrack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.load",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding skipTrack = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.skip",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding volumeMusicUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.music.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding volumeMusicDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.music.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding resetQueueKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.music.reset",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding shuffleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.music.shuffle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding repeatingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.music.repeating",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding swapItemKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.swap",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R, // The keycode of the key
                "kelutils.name"
        ));
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
                Player.swapItem(client);
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


        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
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
                ));
    }
    // STARTING FUNCTIONS
    public static void start(){
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(UserConfig.ENABLE_HUD_INFORMATION) updateHUD();
                if(UserConfig.ENABLE_BOSSBAR_INFORMATION) updateBossBar();
                else if(lastBossBar) clearBossBar();
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

    public static void updateHUD(){
        try{
            MinecraftClient CLIENT = MinecraftClient.getInstance();
            if(CLIENT.world != null && CLIENT.player != null){
                if(!CLIENT.player.isSleeping()) CLIENT.player.sendMessage(Localization.toText(Localization.getLocalization("hud", true)), true);
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
                        bossBar = new ClientBossBar(Main.bossBarUUID, Localization.toText(Localization.getLocalization("bossbar.music.pause", true)), 1F
                                , BossBar.Color.YELLOW, BossBar.Style.PROGRESS,false, false, false);
                    } else {
                        if(Main.music.getAudioPlayer().getPlayingTrack().getInfo().isStream)
                            bossBar = new ClientBossBar(Main.bossBarUUID, Localization.toText(Localization.getLocalization("bossbar.music.live", true)), (
                                    1F
                            ), BossBar.Color.RED, BossBar.Style.PROGRESS,false, false, false);
                        else bossBar = new ClientBossBar(Main.bossBarUUID, Localization.toText(Localization.getLocalization("bossbar.music", true)), (
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
                        case "world_time" -> {
                            long currentTime = client.world.getLunarTime() % 24000;
                            percent = (float) currentTime / 24000;
                            if (percent > 1f) percent = 1f;
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
                        }
                        case "real_time" -> {
                            DateFormat dateFormat = new SimpleDateFormat("HH");
                            long currentTime = Long.parseLong(dateFormat.format(System.currentTimeMillis()));
                            percent = (float) currentTime / 24;
                            if (percent > 1f) percent = 1f;
                            if (currentTime < 11 && currentTime > 0) {
                                color = BossBar.Color.YELLOW;
                            } else if (currentTime < 17 && currentTime > 11) {
                                color = BossBar.Color.GREEN;
                            } else if (currentTime < 22 && currentTime > 17) {
                                color = BossBar.Color.YELLOW;
                            } else if (currentTime > 22) {
                                color = BossBar.Color.RED;
                            } else {
                                color = BossBar.Color.WHITE;
                            }
                        }
                    }
                    bossBar = new ClientBossBar(Main.bossBarUUID, Localization.toText(Localization.getLocalization("bossbar", true)), (
                            percent
                    ), color, BossBar.Style.PROGRESS,false, false, false);
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
    // SCREENSHOT
    public static void handleScreenshotAWTInv(NativeImage img) {
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

        handleScreenshotAWTInv(buf, img.getWidth(), img.getHeight(), 4);
    }

    public static void handleScreenshotAWTInv(ByteBuffer byteBuffer, int width, int height, int components) {
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

        doCopyInv(array, width, height, components);
    }

    private static void doCopyInv(byte[] imageData, int width, int height, int components) {
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
                Bot.sendScreenshotInv(is);
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
