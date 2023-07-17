package ru.simplykel.kelutils.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.kelutils.client.discord.Bot;
import ru.simplykel.kelutils.client.info.Game;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow public abstract void close();

    @Inject(method = "render", at = @At("TAIL"))
    private void render(float f, long l, boolean bl, CallbackInfo ci) {
        Game.tick();
        MinecraftClient mc = MinecraftClient.getInstance();
        if(Bot.takeScreenshotBot){
            Bot.takeScreenshotBot = false;
            NativeImage img = ScreenshotRecorder.takeScreenshot(mc.getFramebuffer());
            Main.handleScreenshotAWT(img);
        }
//        if(Bot.takeScreenshotInvBot){
//            Bot.takeScreenshotInvBot = false;
//            if(mc.player != null && mc.world != null){
//                Screen current = mc.currentScreen;
//                mc.setScreen(new InventoryScreen(mc.player));
//                NativeImage img = ScreenshotRecorder.takeScreenshot(mc.getFramebuffer());
//                Main.handleScreenshotAWTInv(img);
//                mc.send(() -> mc.setScreen(current));
//            }
//        }

    }
}