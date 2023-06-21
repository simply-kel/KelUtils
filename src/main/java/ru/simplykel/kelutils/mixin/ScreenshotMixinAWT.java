package ru.simplykel.kelutils.mixin;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.simplykel.kelutils.Main;

import java.io.File;
import java.util.function.Consumer;

@Mixin(ScreenshotRecorder.class)
public class ScreenshotMixinAWT {
    // Lambda in method_1662 is called method_1661
    // Inject before it starts saving the file (HEAD is the safest place to do this)
    @Inject(at = @At(value = "HEAD"), method = "method_1661")
    private static void screenshotCaptured(NativeImage nativeImage_1, File file_1, Consumer<Text> consumer_1, CallbackInfo ci) {
        Main.handleScreenshotAWT(nativeImage_1);
    }
}