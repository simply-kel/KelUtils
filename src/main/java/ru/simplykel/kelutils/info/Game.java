package ru.simplykel.kelutils.info;

import io.github.bumblesoftware.fastload.client.BuildingTerrainScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.screen.world.OptimizeWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import ru.simplykel.kelutils.Main;

public class Game {

    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    private static Screen lastScreen;
    private static boolean update = false;
    private static int gameState = 0;
    public static boolean isUpdate() {
        return update;
    }

    public static void setUpdated(boolean updated) {
        update = updated;
    }

    public static int getGameState() {
        return gameState;
    }

    public static void setGameState(int newGameState) {
        gameState = newGameState;
    }
    public static void tick() {
        if (CLIENT.currentScreen != null) screenTick();
        if (lastScreen != CLIENT.currentScreen) {
            if (lastScreen != null) screenChange(lastScreen);
            lastScreen = CLIENT.currentScreen;
        }
    }

    private static void screenTick() {
        if (isLoadingScreen()) {
            setGameState(1);
        } else if (CLIENT.currentScreen instanceof ProgressScreen || CLIENT.currentScreen instanceof ConnectScreen || CLIENT.currentScreen instanceof DownloadingTerrainScreen) {
            setGameState(2);
        } else if (CLIENT.currentScreen instanceof DisconnectedScreen) {
            setGameState(3);
        } else if (CLIENT.currentScreen instanceof GameOptionsScreen) {
            setGameState(4);
        } else if (CLIENT.currentScreen instanceof MultiplayerScreen) { // MULTIPLAYER
            setGameState(5);
        } else if (CLIENT.currentScreen instanceof AddServerScreen) {
            setGameState(6);
        } else if (CLIENT.currentScreen instanceof SelectWorldScreen) { // WORLDS SCREENS
            setGameState(7);
        } else if (CLIENT.currentScreen instanceof CreateWorldScreen) {
            setGameState(8);
        } else if (CLIENT.currentScreen instanceof EditWorldScreen) {
            setGameState(9);
        } else if (CLIENT.currentScreen instanceof OptimizeWorldScreen) {
            setGameState(10);
        } else if (CLIENT.currentScreen instanceof TitleScreen) {
            setGameState(11);
        } else {
            setGameState(0);
        }
        setUpdated(true);
    }
    private static boolean isLoadingScreen(){
        if(CLIENT.currentScreen instanceof LevelLoadingScreen) return true;
        if(Main.fastload){
            return CLIENT.currentScreen instanceof BuildingTerrainScreen;
        } else return false;
    }

    private static void screenChange(Screen lastScreen) {
        if (isUpdate() && (lastScreen instanceof DisconnectedScreen || lastScreen instanceof LevelLoadingScreen || lastScreen instanceof ProgressScreen || lastScreen instanceof ConnectScreen || lastScreen instanceof ConfirmScreen || lastScreen instanceof DownloadingTerrainScreen)) {
            setUpdated(false);
        }
    }
}
