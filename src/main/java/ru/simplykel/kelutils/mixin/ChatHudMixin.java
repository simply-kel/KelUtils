package ru.simplykel.kelutils.mixin;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableTextContent;
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
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.config.ServerConfig;
import ru.simplykel.kelutils.discord.Bot;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"))
    private void addMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        if (refresh) return;
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        boolean isModMessage = false;
        if (message instanceof TranslatableTextContent) {
            String key = ((TranslatableTextContent) message).getKey();
            if(key.startsWith("kelutils.")) isModMessage = true;
        }
        if(isModMessage) return;
        if(!CLIENT.isInSingleplayer() && CLIENT.getCurrentServerEntry() != null) {
            try{
                String messageText = Localization.toString(message);
                String[] messageArray = messageText.split(" ");
                boolean isAuthor = false;
                boolean isFirstPlayer = false;
                String firstPlayer = "";
                boolean isMention = false;
                for(int i = 0; i<messageArray.length;i++){
                    for(PlayerListEntry player : CLIENT.getNetworkHandler().getPlayerList()){
                        String arg = messageArray[i];
                        if(arg.contains(player.getProfile().getName())){
                            if(!isFirstPlayer) {
                                isFirstPlayer = true;
                                firstPlayer = player.getProfile().getName();
                            }
                            if(player.getProfile().getName().equals(CLIENT.getSession().getUsername()) && (isFirstPlayer && firstPlayer.equals(CLIENT.getSession().getUsername()))) isAuthor = true;
                            if(player.getProfile().getName().equals(CLIENT.getSession().getUsername()) && !isAuthor) isMention = true;
                        };
                    }
                }
                if(ServerConfig.SEND_CHAT_TO_PM && Bot.DISCORD_CONNECTED){
                    if(!isAuthor) Bot.sendMessageFromGame(message);
                }
                if(isMention) CLIENT.getSoundManager().play(
                        new PositionedSoundInstance(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP.getId(), SoundCategory.PLAYERS, 1F, 1F, SoundInstance.createRandom(), false, 0, SoundInstance.AttenuationType.NONE, 0, 0, 0, true)
                );
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}