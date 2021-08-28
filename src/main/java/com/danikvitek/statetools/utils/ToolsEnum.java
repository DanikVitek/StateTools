package com.danikvitek.statetools.utils;

import com.danikvitek.statetools.Main;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Objects;

public enum ToolsEnum {
    WRENCH(Main.getPlugin(Main.class).getConfig().getItemStack("wrench")),
    HAMMER(Main.getPlugin(Main.class).getConfig().getItemStack("hammer")),
    BRUSH(Main.getPlugin(Main.class).getConfig().getItemStack("brush")),
    PAINTED_BRUSH(Main.getPlugin(Main.class).getConfig().getItemStack("painted_brush"));

    private final ItemStack item;

    ToolsEnum(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public static boolean isPaintedBrush(@Nullable ItemStack item) {
        return item != null && item.getType() == PAINTED_BRUSH.getItem().getType() &&
            item.hasItemMeta() &&
            Objects.requireNonNull(item.getItemMeta()).hasCustomModelData() &&
            item.getItemMeta().getCustomModelData() == Objects.requireNonNull(PAINTED_BRUSH.getItem().getItemMeta()).getCustomModelData();
    }

    public static boolean isBrush(@Nullable ItemStack item) {
        return item != null && item.getType() == BRUSH.getItem().getType() &&
            item.hasItemMeta() &&
            Objects.requireNonNull(item.getItemMeta()).hasCustomModelData() &&
            item.getItemMeta().getCustomModelData() == Objects.requireNonNull(BRUSH.getItem().getItemMeta()).getCustomModelData();
    }
}
