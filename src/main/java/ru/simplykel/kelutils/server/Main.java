package ru.simplykel.kelutils.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.simplykel.kelutils.server.config.ServerConfig;
import ru.simplykel.kelutils.server.discord.Bot;
import ru.simplykel.kelutils.server.motd.MOTD;

public class Main implements DedicatedServerModInitializer {
    public static MinecraftServer server;
    public static Main INSTANCE;
    public static final Logger LOG = LogManager.getLogger("KelUtils");
    @Override
    public void onInitializeServer() {
        INSTANCE = this;
        LOG.info("[KelLibs/Server] Hello, world!");
        ServerLifecycleEvents.SERVER_STARTING.register(server1 -> {
            server = server1;
            ServerConfig.load();
            if (ServerConfig.ENABLE_DISCORD) {
                try {
                    Bot.start();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
         ServerTickEvents.END_SERVER_TICK.register(server1 -> {
            if (ServerConfig.ENABLE_MOTD) MOTD.setMetaData();
        });
    }

}
