package ru.simplykel.kelutils.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.simplykel.kelutils.client.config.UserConfig;

@Mixin(value = MinecraftClient.class, priority = -1)
public class WindowMixin {
        @Inject(at = @At("HEAD"), method = "updateWindowTitle", cancellable = true)
        private void updateTitle(final CallbackInfo callbackInfo) {
            if(UserConfig.USE_CUSTOM_TITLE) callbackInfo.cancel();
        }
    }
