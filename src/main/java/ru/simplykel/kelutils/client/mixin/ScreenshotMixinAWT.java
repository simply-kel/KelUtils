package ru.simplykel.kelutils.client.mixin;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.simplykel.kelutils.client.Main;
import ru.simplykel.kelutils.client.config.DiscordConfig;
import ru.simplykel.kelutils.client.discord.Bot;

import java.io.File;
import java.util.function.Consumer;

@Mixin(ScreenshotRecorder.class)
public class ScreenshotMixinAWT {
    // Lambda in method_1662 is called method_1661
    // Inject before it starts saving the file (HEAD is the safest place to do this)
    @Inject(at = @At(value = "HEAD"), method = "method_1661")
    private static void screenshotCaptured(NativeImage nativeImage_1, File file_1, Consumer<Text> consumer_1, CallbackInfo ci) {
        if(Bot.DISCORD_CONNECTED && DiscordConfig.SCREENSHOTS_TO_PM) Main.handleScreenshotAWT(nativeImage_1);
    }
}