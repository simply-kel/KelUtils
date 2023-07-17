package ru.simplykel.kelutils.client.mixin;

import net.minecraft.client.gui.hud.SpectatorHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import ru.simplykel.kelutils.client.config.HUDConfig;

@Mixin(value = SpectatorHud.class, priority = -1)
public class SpectatorHudMixin {

    @ModifyArg(method = "renderSpectatorMenu(Lnet/minecraft/client/gui/DrawContext;FIILnet/minecraft/client/gui/hud/spectator/SpectatorMenuState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"), index = 2)
    private int modifySpectatorMenu(int value) {
        return value - HUDConfig.HUD;
    }

    @ModifyArg(method = "renderSpectatorMenu(Lnet/minecraft/client/gui/DrawContext;FIILnet/minecraft/client/gui/hud/spectator/SpectatorMenuState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1), index = 6)
    private int modifySpectatorMenuSelector(int value) {
        return value + 2;
    }

    @ModifyArg(method = "renderSpectatorMenu(Lnet/minecraft/client/gui/DrawContext;FIILnet/minecraft/client/gui/hud/spectator/SpectatorMenuState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SpectatorHud;renderSpectatorCommand(Lnet/minecraft/client/gui/DrawContext;IIFFLnet/minecraft/client/gui/hud/spectator/SpectatorMenuCommand;)V"), index = 3)
    private float modifySpectatorCommand(float value) {
        return value - HUDConfig.HUD;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"), index = 3)
    private int modifyText(int value) {
        return value - HUDConfig.HUD;
    }

}
