package ru.simplykel.kelutils.client.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CreativeInventoryScreen.class)
public interface MixinCreativeInventoryScreen {

    @Accessor("selectedTab")
    static ItemGroup getSelectedTab() {
        // this is only to tell intellij this won't return null
        return Registries.ITEM_GROUP.get(ItemGroups.INVENTORY);
    }

    @Invoker
    void callSetSelectedTab(ItemGroup group);
}