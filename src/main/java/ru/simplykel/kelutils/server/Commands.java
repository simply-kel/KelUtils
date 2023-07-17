package ru.simplykel.kelutils.server;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import ru.simplykel.kelutils.server.discord.Bot;

import java.util.Arrays;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;

public class Commands {
    public Commands(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, server) ->
                dispatcher.register(CommandManager.literal("kelutils")
                                .then(argument("type", StringArgumentType.greedyString()).executes(context -> {
                                    switch (getString(context, "type")){
                                        case "reload" -> {
                                            Bot.jda.shutdown();
                                            Main.INSTANCE.onInitializeServer();
//                                            context.getSource().sendMessage();
                                            return 1;
                                        }
                                        case "info" -> {
                                            Bot.jda.shutdown();
                                            Main.INSTANCE.onInitializeServer();
                                            return 1;
                                        }
                                        default -> {
                                            return 0;
                                        }
                                    }
                                }))
                ));
    }
}
