package ru.simplykel.kelutils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.ResourcePackStatusC2SPacket;
import net.minecraft.resource.*;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import ru.simplykel.kelutils.config.DiscordConfig;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.UserConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.simplykel.kelutils.config.gui.ConfigScreen;
import ru.simplykel.kelutils.discord.Bot;
import ru.simplykel.kelutils.info.Audio;
import ru.simplykel.kelutils.mixin.NativeImagePointerAccessor;

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
import java.util.concurrent.ExecutionException;

public class Main implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("KelUtils");
    public static Boolean simplyStatus = FabricLoader.getInstance().getModContainer("simplystatus").isPresent();
    public static boolean clothConfig = FabricLoader.getInstance().getModContainer("cloth-config").isPresent();
    public static Boolean fastload = FabricLoader.getInstance().getModContainer("fastload").isPresent();
    public static DecimalFormat DF = new DecimalFormat("#.##");
    private static Timer TIMER = new Timer();
    private static String lastException;
    @Override
    public void onInitializeClient() {
        LOG.info("[KelLibs] Hello, world!");
        UserConfig.load();
        try {
            new Audio();
        } catch (IOException e) {
            e.printStackTrace();
        }

        KeyBinding openConfigKey;
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.openconfig",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
                "kelutils.name"
        ));
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
        KeyBinding volumeUpKey;
        volumeUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.up",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT, // The keycode of the key
                "kelutils.name"
        ));
        KeyBinding volumeDownKey;
        volumeDownKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "kelutils.key.volume.down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT, // The keycode of the key
                "kelutils.name"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            UserConfig.load();
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
            try {
                MinecraftClient.getInstance().getWindow().setIcon(
                        client.getDefaultResourcePack(), UserConfig.ICON_SNAPSHOT ? Icons.SNAPSHOT : Icons.RELEASE);
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
    }
    public static void start(){
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(UserConfig.ENABLE_HUD_INFORMATION) updateHUD();
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
                CLIENT.player.sendMessage(Localization.toText(Localization.getLocalization("hud", true)), true);
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
