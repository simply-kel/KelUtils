package ru.simplykel.kelutils.client.discord.listener.commands;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import ru.simplykel.kelutils.client.config.Localization;
import ru.simplykel.kelutils.client.info.Audio;

public class Volume {
    public static void downVolume(ButtonInteractionEvent e){
        Audio.downValue();
        e.reply(Localization.getLocalization("bot.volume.down", true)).setEphemeral(true).queue();
    }
    public static void upVolume(ButtonInteractionEvent e){
        Audio.upValue();
        e.reply(Localization.getLocalization("bot.volume.up", true)).setEphemeral(true).queue();
    }
}
