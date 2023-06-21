package ru.simplykel.kelutils.mixin;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.discord.Bot;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow public abstract void close();

    @Inject(method = "render", at = @At("TAIL"))
    private void render(float f, long l, boolean bl, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(Bot.takeScreenshotBot){
            Bot.takeScreenshotBot = false;
            NativeImage img = ScreenshotRecorder.takeScreenshot(MinecraftClient.getInstance().getFramebuffer());
            Main.handleScreenshotAWT(img);
        }
    }
}