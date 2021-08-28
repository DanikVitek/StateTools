package com.danikvitek.statetools.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Colorable;

import java.util.Objects;

public class Colored {
    public static boolean hasColor(Block block) {
        return !block.getType().getKey().getKey().endsWith("_banner") &&
               (block.getType().getKey().getKey().startsWith("white_") ||
                block.getType().getKey().getKey().startsWith("orange_") ||
                block.getType().getKey().getKey().startsWith("magenta_") ||
                block.getType().getKey().getKey().startsWith("light_blue_") ||
                block.getType().getKey().getKey().startsWith("yellow_") ||
                block.getType().getKey().getKey().startsWith("lime_") ||
                block.getType().getKey().getKey().startsWith("pink_") ||
                block.getType().getKey().getKey().startsWith("gray_") ||
                block.getType().getKey().getKey().startsWith("Light_gray_") ||
                block.getType().getKey().getKey().startsWith("cyan_") ||
                block.getType().getKey().getKey().startsWith("purple_") ||
                block.getType().getKey().getKey().startsWith("blue_") ||
                block.getType().getKey().getKey().startsWith("brown_") ||
                block.getType().getKey().getKey().startsWith("green_") ||
                block.getType().getKey().getKey().startsWith("red_") ||
                block.getType().getKey().getKey().startsWith("black_"));
    }

    public static boolean hasColor(LivingEntity entity) {
        return entity instanceof Colorable;
    }

    public static boolean hasSameColor(ItemStack brush, Block block) {
        if (brush.getItemMeta() instanceof LeatherArmorMeta){
            Color brushColor = ((LeatherArmorMeta) brush.getItemMeta()).getColor();
            return !block.getType().getKey().getKey().endsWith("_banner") &&
               ((brushColor.equals(DyeColor.WHITE.getColor()) && block.getType().getKey().getKey().startsWith("white_")) ||
                (brushColor.equals(DyeColor.ORANGE.getColor()) && block.getType().getKey().getKey().startsWith("orange_")) ||
                (brushColor.equals(DyeColor.MAGENTA.getColor()) && block.getType().getKey().getKey().startsWith("magenta_")) ||
                (brushColor.equals(DyeColor.LIGHT_BLUE.getColor()) && block.getType().getKey().getKey().startsWith("light_blue_")) ||
                (brushColor.equals(DyeColor.YELLOW.getColor()) && block.getType().getKey().getKey().startsWith("yellow_")) ||
                (brushColor.equals(DyeColor.LIME.getColor()) && block.getType().getKey().getKey().startsWith("lime_")) ||
                (brushColor.equals(DyeColor.PINK.getColor()) && block.getType().getKey().getKey().startsWith("pink_")) ||
                (brushColor.equals(DyeColor.GRAY.getColor()) && block.getType().getKey().getKey().startsWith("gray_")) ||
                (brushColor.equals(DyeColor.LIGHT_GRAY.getColor()) && block.getType().getKey().getKey().startsWith("light_gray_")) ||
                (brushColor.equals(DyeColor.CYAN.getColor()) && block.getType().getKey().getKey().startsWith("cyan_")) ||
                (brushColor.equals(DyeColor.PURPLE.getColor()) && block.getType().getKey().getKey().startsWith("purple_")) ||
                (brushColor.equals(DyeColor.BLUE.getColor()) && block.getType().getKey().getKey().startsWith("blue_")) ||
                (brushColor.equals(DyeColor.BROWN.getColor()) && block.getType().getKey().getKey().startsWith("brown_")) ||
                (brushColor.equals(DyeColor.GREEN.getColor()) && block.getType().getKey().getKey().startsWith("green_")) ||
                (brushColor.equals(DyeColor.RED.getColor()) && block.getType().getKey().getKey().startsWith("red_")) ||
                (brushColor.equals(DyeColor.BLACK.getColor()) && block.getType().getKey().getKey().startsWith("black_")));
        }
        return false;
    }

    public static boolean hasSameColor(ItemStack brush, LivingEntity entity) {
        if (entity instanceof Colorable) {
            DyeColor brushColor = DyeColor.getByColor(((LeatherArmorMeta) Objects.requireNonNull(brush.getItemMeta())).getColor());
            return brushColor != null && brushColor.equals(((Colorable) entity).getColor());
        }
        return false;
    }
}
