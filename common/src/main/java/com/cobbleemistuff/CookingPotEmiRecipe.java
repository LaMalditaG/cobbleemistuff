package com.cobbleemistuff;

import com.cobblemon.mod.common.api.tags.CobblemonItemTags;
import com.cobblemon.mod.common.client.tooltips.*;
import com.cobblemon.mod.common.item.crafting.CookingPotRecipe;
import com.cobblemon.mod.common.item.crafting.CookingPotShapelessRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CookingPotEmiRecipe implements EmiRecipe {

    CookingPotRecipe cookingPotRecipe;
    CookingPotShapelessRecipe cookingPotShapelessRecipe;

    boolean isShapeless;

    CookingPotEmiRecipe(CookingPotRecipe recipe){
        this.cookingPotRecipe = recipe;
        isShapeless = false;
    }

    CookingPotEmiRecipe(CookingPotShapelessRecipe recipe){
        this.cookingPotShapelessRecipe = recipe;
        isShapeless = true;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return CobbleEmiPlugin.COBBLEMON_COOKING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
//        return cookingPotRecipe.
        return null;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        if(isShapeless)
            return cookingPotShapelessRecipe.getIngredients().stream().map(EmiIngredient::of).toList();
        else
            return cookingPotRecipe.getIngredients().stream().map(EmiIngredient::of).toList();
    }

    @Override
    public List<EmiStack> getOutputs() {
        if(isShapeless)
            return List.of(EmiStack.of(cookingPotShapelessRecipe.getResult()));
        else
            return List.of(EmiStack.of(cookingPotRecipe.getResult()));
    }

    @Override
    public int getDisplayWidth() {
        return 148;
    }

    @Override
    public int getDisplayHeight() {
        return 62;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {

        final int offsetX = 15;
        final int offsetY = 0;

        widgetHolder.addTexture(ResourceLocation.fromNamespaceAndPath("cobblemon", "textures/gui/jei/campfire_pot.png"),offsetX-15,offsetY,146,59,0,0,146,59,146,59);

        final int separation = 18;

        NonNullList<Ingredient> ingredients;
        ItemStack result;

        TagKey<Item> seasoningTag;

        if(isShapeless){
            result = cookingPotShapelessRecipe.getResult();
            ingredients = cookingPotShapelessRecipe.getIngredients();
            seasoningTag = cookingPotShapelessRecipe.getSeasoningTag();
        }
        else{
            result = cookingPotRecipe.getResult();
            ingredients = cookingPotRecipe.getPattern().ingredients();
            seasoningTag = cookingPotRecipe.getSeasoningTag();
        }

        EmiIngredient seasoningIngredient = null;
        if(seasoningTag!=CobblemonItemTags.EMPTY){
            seasoningIngredient = EmiIngredient.of(seasoningTag);
        }

        int n = 0;
        for(int j = 0; j < 3; j++){
            for(int i = 0; i < 3; i++){
                if(n >= ingredients.size() || ingredients.get(n).isEmpty()){
                    widgetHolder.addSlot(offsetX+i*separation,offsetY+j*separation);
                }else{
                    ItemStack item = ingredients.get(n).getItems()[0];
                    widgetHolder.addSlot(EmiIngredient.of(Ingredient.of(item)),offsetX+i*separation,offsetY+j*separation);
                }

                n++;
            }
        }

        for(int i = 0; i < 3; i++){
            if(seasoningIngredient==null)
                widgetHolder.addSlot(offsetX+77+i*separation,offsetY);
            else
                widgetHolder.addSlot(seasoningIngredient,offsetX+77+i*separation,offsetY);
        }

        widgetHolder.addSlot(EmiIngredient.of(Ingredient.of(result)),offsetX+91,offsetY+33).recipeContext(this).large(true);
    }
}
