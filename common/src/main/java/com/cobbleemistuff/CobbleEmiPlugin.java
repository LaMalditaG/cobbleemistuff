package com.cobbleemistuff;

import com.cobblemon.mod.common.CobblemonRecipeBookTypes;
import com.cobblemon.mod.common.CobblemonRecipeCategories;
import com.cobblemon.mod.common.CobblemonRecipeTypes;
import com.cobblemon.mod.common.api.tags.CobblemonBlockTags;
import com.cobblemon.mod.common.api.tags.CobblemonItemTags;
import com.cobblemon.mod.common.integration.jei.CobblemonJeiProvider;
import com.cobblemon.mod.common.integration.jei.cooking.CampfirePotJeiProvider;
import com.cobblemon.mod.common.integration.jei.cooking.CampfirePotRecipeCategory;
import com.cobblemon.mod.common.item.crafting.CookingPotRecipe;
import com.cobblemon.mod.common.item.crafting.CookingPotRecipeBase;
import com.cobblemon.mod.common.item.crafting.CookingPotShapelessRecipe;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.nbt.TagTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

@EmiEntrypoint
public class CobbleEmiPlugin implements EmiPlugin {
    public static final EmiRecipeCategory COBBLEMON_COOKING = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(CobbleEmiStuff.MOD_ID,"cobblemon_cooking"), EmiStack.of( BuiltInRegistries.ITEM.get(ResourceLocation.tryBuild("cobblemon","campfire_pot_red")).getDefaultInstance()));

    @Override
    public void register(EmiRegistry registry) {


        registry.addCategory(COBBLEMON_COOKING);
        registry.addWorkstation(COBBLEMON_COOKING,EmiIngredient.of(TagKey.create(BuiltInRegistries.ITEM.key(),ResourceLocation.tryBuild("cobblemon","campfire_pots"))));

        TagKey.create(BuiltInRegistries.ITEM.key(),ResourceLocation.tryBuild("cobblemon","campfire_pots"));

        if(Minecraft.getInstance().level == null) {
            return;
        }
        var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        RecipeType<CookingPotRecipe> recipeCookingPot = (RecipeType<CookingPotRecipe>) BuiltInRegistries.RECIPE_TYPE.get(ResourceLocation.fromNamespaceAndPath("cobblemon","cooking_pot"));
        RecipeType<CookingPotShapelessRecipe> recipeCookingPotShapeless = (RecipeType<CookingPotShapelessRecipe>) BuiltInRegistries.RECIPE_TYPE.get(ResourceLocation.fromNamespaceAndPath("cobblemon","cooking_pot_shapeless"));

        var cookingPotRecipes = recipeManager.getAllRecipesFor(recipeCookingPot);
        var cookingPotShapelessRecipes = recipeManager.getAllRecipesFor(recipeCookingPotShapeless);

        for( var recipe : cookingPotRecipes){

            if(recipe.value() instanceof CookingPotRecipe cookingPotRecipe){

                registry.addRecipe(new CookingPotEmiRecipe(cookingPotRecipe));
            }
        }

        for( var recipe : cookingPotShapelessRecipes){

            if(recipe.value() instanceof CookingPotShapelessRecipe cookingPotRecipe){

                registry.addRecipe(new CookingPotEmiRecipe(cookingPotRecipe));
            }
        }


//        for()

        // Removes the version of the item with no nbt
        // Calling this after adding is not necessary, since remove calls are deferred anyway, but it is good for conveying purpose
//        registry.removeEmiStacks(normal);

        // Removes every EmiStack that is not an item
        // You really don't want to do this, but it's an example of what is possible
//        registry.removeEmiStacks(s -> s.getItemStack().isEmpty());
    }
}
