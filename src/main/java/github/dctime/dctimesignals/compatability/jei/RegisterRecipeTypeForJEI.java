package github.dctime.dctimesignals.compatability.jei;

import github.dctime.dctimesignals.RegisterRecipeTypes;
import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class RegisterRecipeTypeForJEI {
    public static final RecipeType<SignalResearchRecipe> SIGNAL_RESEARCH_RECIPE_TYPE =
            createFromDeferredVanilla(RegisterRecipeTypes.SIGNAL_RESEARCH_RECIPE_TYPE.get(), SignalResearchRecipe.class);

    public static <R extends Recipe<?>> RecipeType<R> createFromDeferredVanilla(net.minecraft.world.item.crafting.RecipeType<R> recipeType, Class<R> recipeClass) {
        ResourceLocation uid = BuiltInRegistries.RECIPE_TYPE.getKey(recipeType);
        if (uid == null) {
            throw new IllegalArgumentException("Vanilla Recipe Type must be registered before using it here. %s".formatted(recipeType));
        }

        return new RecipeType<>(uid, recipeClass);
    }
}
