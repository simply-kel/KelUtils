package ru.simplykel.kelutils.discord.listener.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.minecraft.client.MinecraftClient;
import ru.simplykel.kelutils.config.Localization;
import ru.simplykel.kelutils.discord.Bot;
import ru.simplykel.kelutils.info.Player;

import java.awt.*;

public class Information {
    public Information(SlashCommandInteractionEvent e){
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(new Color(0x264653))
                .setTitle(Localization.getLocalization("bot.info.title", true));
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.world == null && client.player == null){
            embed.setDescription(Localization.getLocalization("bot.info.notworld", true));
        } else {
            if(client.player.isDead()){
                embed.addField(
                        Localization.getLocalization("bot.info.title.health", true),
                        Localization.getLocalization("bot.info.dead", true),
                        true
                );
            } else {
                embed.addField(
                        Localization.getLocalization("bot.info.title.health", true),
                        Localization.getLocalization("bot.info.health", true),
                        true
                ).addField(
                        Localization.getLocalization("bot.info.title.armor", true),
                        Localization.getLocalization("bot.info.armor", true),
                        true
                ).addField(
                        Localization.getLocalization("bot.info.title.xp", true),
                        Localization.getLocalization("bot.info.xp", true),
                        true
                );
            }
            embed.addField(
                    Localization.getLocalization("bot.info.title.position", true),
                    Localization.getLocalization("bot.info.position", true),
                    true
            ).addField(
                    Localization.getLocalization("bot.info.title.world", true),
                    Localization.getLocalization("bot.info.world", true),
                    true
            ).addField(
                    Localization.getLocalization("bot.info.title.world.time", true),
                    Localization.getLocalization("bot.info.world.time", true),
                    true
            ).addField(
                    Localization.getLocalization("bot.info.title.scene", true),
                    Localization.getLocalization("bot.info.scene", true),
                    true
            );
            if(Player.getItemName() != null) embed.addField(
                    Localization.getLocalization("bot.info.title.item", true),
                    Localization.getLocalization("bot.info.item", true),
                    true
            );

        }
        e.replyEmbeds(embed.build()).addActionRow(
                Button.secondary("screenshot", Emoji.fromUnicode("📸")).withLabel(Localization.getLocalization("bot.button.screenshot", true)),
                Button.secondary("upvolume", Emoji.fromUnicode("🔊")).withLabel(Localization.getLocalization("bot.button.volume.up", true)),
                Button.secondary("downvolume", Emoji.fromUnicode("🔉")).withLabel(Localization.getLocalization("bot.button.volume.down", true)),
                Button.secondary("exit", Emoji.fromUnicode("❌")).withLabel(Localization.getLocalization("bot.button.exit", true))
        ).queue();
    }
}
