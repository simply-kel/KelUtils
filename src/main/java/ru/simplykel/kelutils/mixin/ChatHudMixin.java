package ru.simplykel.kelutils.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import ru.simplykel.kelutils.config.ServerConfig;
import ru.simplykel.kelutils.discord.Bot;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"))
    private void addMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        if (refresh) return;
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        if(!CLIENT.isInSingleplayer() && CLIENT.getCurrentServerEntry() != null) {
            if(ServerConfig.SEND_CHAT_TO_PM && Bot.DISCORD_CONNECTED){
                try{
                    Bot.sendMessageFromGame(message);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}