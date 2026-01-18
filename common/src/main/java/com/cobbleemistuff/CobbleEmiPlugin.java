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
import com.cobblemon.mod.common.item.crafting.brewingstand.BrewingStandRecipe;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import net.minecraft.world.item.crafting.RecipeType;

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

        RecipeType<BrewingStandRecipe> recipeBrewingStand = (RecipeType<BrewingStandRecipe>) BuiltInRegistries.RECIPE_TYPE.get(ResourceLocation.fromNamespaceAndPath("cobblemon","brewing_stand"));

        var brewingStandRecipes = recipeManager.getAllRecipesFor(recipeBrewingStand);

        for ( var recipe : brewingStandRecipes){
            if(recipe.value() instanceof BrewingStandRecipe brewingStandRecipe){

                registry.addRecipe(new BrewingStandEmiRecipe(brewingStandRecipe));
            }
        }
    }
}
