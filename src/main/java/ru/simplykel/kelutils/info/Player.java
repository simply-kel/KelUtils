package ru.simplykel.kelutils.info;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import ru.simplykel.kelutils.config.UserConfig;
import ru.simplykel.kelutils.Main;

public class Player {
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

}
