package ru.simplykel.kelutils.info;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import ru.simplykel.kelutils.config.UserConfig;
import ru.simplykel.kelutils.Main;
import ru.simplykel.kelutils.mixin.MixinCreativeInventoryScreen;

public class Player {

    public static final int LEFT_BOTTOM_ROW_SLOT_INDEX = 27;

    private static final int LEFT_HOTBAR_SLOT_INDEX = 36;
    private static final int BOTTOM_RIGHT_CRAFTING_SLOT_INDEX = 4;
    public static String getItemName(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        ItemStack main_hand = CLIENT.player.getStackInHand(Hand.MAIN_HAND);
        String main_hand_item = main_hand.getItem().toString();
        if(main_hand_item.equals("air") && UserConfig.VIEW_ITEM_OFF_HAND){
            ItemStack off_hand = CLIENT.player.getStackInHand(Hand.OFF_HAND);
            String off_hand_item = off_hand.getItem().toString();
            if(off_hand_item.equals("air") || off_hand.getName() == null) return null;
            else return off_hand.getName().getString();
        } else {
            if(main_hand_item.equals("air") || main_hand.getName() == null) return null;
            else return main_hand.getName().getString();
        }
    }
    public static int getItemCount(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        ItemStack main_hand = CLIENT.player.getStackInHand(Hand.MAIN_HAND);
        String main_hand_item = main_hand.getItem().toString();
        if(main_hand_item.equals("air") && UserConfig.VIEW_ITEM_OFF_HAND){
            ItemStack off_hand = CLIENT.player.getStackInHand(Hand.OFF_HAND);
            String off_hand_item = off_hand.getItem().toString();
            if(off_hand_item.equals("air") || off_hand.getName() == null) return 0;
            else return off_hand.getCount();
        } else {
            if(main_hand_item.equals("air") || main_hand.getName() == null) return 0;
            else return main_hand.getCount();
        }
    }
    public static String getHealth(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        return Main.DF.format(CLIENT.player.getHealth()/2);
    }
    public static String getMaxHealth(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        return Main.DF.format(CLIENT.player.getMaxHealth()/2);
    }
    public static String getPercentHealth(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        return Main.DF.format((CLIENT.player.getHealth()*100)/CLIENT.player.getMaxHealth());
    }
    public static String getArmor(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        return Main.DF.format(CLIENT.player.getArmor()/2);
    }
    public static String getX(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        return Main.DF.format(CLIENT.player.capeX);
    }
    public static String getY(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        return Main.DF.format(CLIENT.player.capeY);
    }
    public static String getZ(){
        MinecraftClient CLIENT = MinecraftClient.getInstance();
        return Main.DF.format(CLIENT.player.capeZ);
    }
    public static void swapItem(final MinecraftClient client) {
        final ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        final InventoryScreen inventory = new InventoryScreen(player);
        client.setScreen(inventory);

        final Screen currentScreen = client.currentScreen;
        if (currentScreen == null) {
            return;
        }

        // For the switcheroo to work, we need to be in the inventory window
        final ItemGroup group;
        if (currentScreen instanceof CreativeInventoryScreen) {
            group = MixinCreativeInventoryScreen.getSelectedTab();
            if (group.getType() != ItemGroup.Type.INVENTORY) {
                ((MixinCreativeInventoryScreen) currentScreen).callSetSelectedTab(Registries.ITEM_GROUP.get(ItemGroups.INVENTORY));
            }
        } else {
            group = null;
        }

        final int syncId = inventory.getScreenHandler().syncId;
        final ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (interactionManager != null) {
            final int currentItem = player.getInventory().selectedSlot;
            swapItem(interactionManager, player, syncId, currentItem);
        }


        // If group == null then it's not a creative inventory, if its type is INVENTORY then there's no need to change it back to itself
        if (group != null && group.getType() != ItemGroup.Type.INVENTORY) {
            ((MixinCreativeInventoryScreen) currentScreen).callSetSelectedTab(group);
        }
        client.setScreen(null);
    }
    private static void swapItem(final ClientPlayerInteractionManager interactionManager, ClientPlayerEntity player, final int syncId, final int slotId) {
        /*
         * Implementation note:
         * There are fancy click mechanisms to swap item stacks without using a temporary slot, but when swapping between two identical item
         * stacks, things can get messed up. Using a temporary slot that we know is guaranteed to be empty is the safest option.
         */

        // Move hotbar item to crafting slot
        interactionManager.clickSlot(syncId, slotId + LEFT_HOTBAR_SLOT_INDEX, 0, SlotActionType.PICKUP, player);
        interactionManager.clickSlot(syncId, BOTTOM_RIGHT_CRAFTING_SLOT_INDEX, 0, SlotActionType.PICKUP, player);
        // Move bottom row item to hotbar
        interactionManager.clickSlot(syncId, slotId + LEFT_BOTTOM_ROW_SLOT_INDEX, 0, SlotActionType.PICKUP, player);
        interactionManager.clickSlot(syncId, slotId + LEFT_HOTBAR_SLOT_INDEX, 0, SlotActionType.PICKUP, player);
        // Move crafting slot item to bottom row
        interactionManager.clickSlot(syncId, BOTTOM_RIGHT_CRAFTING_SLOT_INDEX, 0, SlotActionType.PICKUP, player);
        interactionManager.clickSlot(syncId, slotId + LEFT_BOTTOM_ROW_SLOT_INDEX, 0, SlotActionType.PICKUP, player);
    }
}
