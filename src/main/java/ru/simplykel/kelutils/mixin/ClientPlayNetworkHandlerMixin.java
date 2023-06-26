package ru.simplykel.kelutils.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.discord.Bot;

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
            NativeImage img = ScreenshotRecorder.takeScreenshot(client.getFramebuffer());
            Main.handleScreenshotAWT(img, packet.getMessage());
        }
    }

}