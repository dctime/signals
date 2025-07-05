package github.dctime.dctimesignals;

import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import github.dctime.dctimesignals.recipe.SignalResearchRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, DCtimeMod.MODID);

    public static final Supplier<RecipeSerializer<SignalResearchRecipe>> SIGNAL_RESEARCH_RECIPE =
            RECIPE_SERIALIZERS.register("signal_research", SignalResearchRecipeSerializer::new);
}
