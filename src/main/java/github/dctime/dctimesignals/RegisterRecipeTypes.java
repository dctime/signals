package github.dctime.dctimesignals;

import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, DCtimeMod.MODID);

    public static final Supplier<RecipeType<SignalResearchRecipe>> SIGNAL_RESEARCH_RECIPE_TYPE =
            RECIPE_TYPES.register(
                    "signal_research",
                    () -> RecipeType.<SignalResearchRecipe>simple(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_research"))
            );
}
