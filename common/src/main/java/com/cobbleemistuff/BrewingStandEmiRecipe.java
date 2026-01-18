package com.cobbleemistuff;

import com.cobblemon.mod.common.item.crafting.brewingstand.BrewingStandRecipe;
//import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrewingStandEmiRecipe implements EmiRecipe {
    private static final ResourceLocation BACKGROUND = ResourceLocation.tryBuild("minecraft", "textures/gui/container/brewing_stand.png");
    private static final EmiStack BLAZE_POWDER = EmiStack.of(Items.BLAZE_POWDER);
//    private final EmiIngredient input, ingredient;
//    private final EmiStack output, input3, output3;
//    private final ResourceLocation id;
    BrewingStandRecipe recipe;

    BrewingStandEmiRecipe(BrewingStandRecipe recipe){
        this.recipe = recipe;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return VanillaEmiRecipeCategories.BREWING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return null;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiStack.of(recipe.getBottle().getItems()[0]).setAmount(3));
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(EmiStack.of(recipe.getResult()).setAmount(3));
    }

    @Override
    public int getDisplayWidth() {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 61;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addTexture(BACKGROUND, 0, 0, 103, 61, 16, 14);
        widgetHolder.addAnimatedTexture(BACKGROUND, 81, 2, 9, 28, 176, 0, 1000 * 20, false, false, false).tooltip((mx, my) -> {
            return List.of(ClientTooltipComponent.create(Component.translatable("emi.cooking.time", 20).getVisualOrderText()));
//            return asOrderedText()
//            return List.of(ClientTooltipComponent.create(EmiPort.ordered(EmiPort.translatable())));
        });
        widgetHolder.addAnimatedTexture(BACKGROUND, 47, 0, 12, 29, 185, 0, 700, false, true, false);
        widgetHolder.addTexture(BACKGROUND, 44, 30, 18, 4, 176, 29);
        widgetHolder.addSlot(BLAZE_POWDER, 0, 2).drawBack(false);
        widgetHolder.addSlot(EmiIngredient.of(recipe.getBottle()), 39, 36).drawBack(false);
        widgetHolder.addSlot(EmiIngredient.of(recipe.getInput()), 62, 2).drawBack(false);
        widgetHolder.addSlot(EmiStack.of(recipe.getResult()), 85, 36).drawBack(false).recipeContext(this);
    }
}
