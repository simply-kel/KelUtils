package ru.simplykel.kelutils.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.discord.Bot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "onDeathMessage", at = @At("HEAD"))
    private void onClientDeath(DeathMessageS2CPacket packet, CallbackInfo ci) {
        if (!RenderSystem.isOnRenderThread()) return;
        if (Bot.DISCORD_CONNECTED) {
            NativeImage img = ScreenshotRecorder.takeScreenshot(MinecraftClient.getInstance().getFramebuffer());
            Main.handleScreenshotAWT(img, packet.getMessage());
        }
    }




//    @Inject(method = "onHealthUpdate", at = @At("HEAD"))
//    private void onLegacyClientDeath(HealthUpdateS2CPacket packet, CallbackInfo ci) {
//        if (!DeathLogClient.CONFIG.useLegacyDeathDetection() || !RenderSystem.isOnRenderThread()) return;
//        if (packet.getHealth() > 0 || client.player.isDead()) return;
//
//        DeathLogClient.getClientStorage().store(Text.empty(), client.player);
//
//        if (DeathLogClient.CONFIG.screenshotsEnabled()) {
//            ScreenshotRecorder.saveScreenshot(FabricLoader.getInstance().getGameDir().toFile(), client.getFramebuffer(), text -> {
//                text = Text.literal("§7[§bDeathLog§7] ").append(((MutableText) text).formatted(Formatting.GRAY));
//                client.player.sendMessage(text, false);
//            });
//        }
//    }

}