package com.cobbleemistuff;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class CobbleEmiUtils {
    public static String subId(ResourceLocation id) {
        return id.getNamespace() + "/" + id.getPath();
    }

    public static String subId(Block block) {
        return subId(getBlockRegistry().getKey(block));
    }

    public static String subId(Item item) {
        return subId(getItemRegistry().getKey(item));
    }

    public static String subId(Fluid fluid) {
        return subId(getFluidRegistry().getKey(fluid));
    }

    public static Registry<Item> getItemRegistry() {
        return BuiltInRegistries.ITEM;
    }

    public static Registry<Block> getBlockRegistry() {
        return BuiltInRegistries.BLOCK;
    }

    public static Registry<Fluid> getFluidRegistry() {
        return BuiltInRegistries.FLUID;
    }

    public static Registry<Potion> getPotionRegistry() {
        return BuiltInRegistries.POTION;
    }
}
