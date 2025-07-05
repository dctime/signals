package github.dctime.dctimesignals.datagen.signal_research;

import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;

public class SignalResearchRecipeBuilder extends SimpleRecipeBuilder{

    private final BlockState state;
    private final Ingredient input;

    public SignalResearchRecipeBuilder(ItemStack result, BlockState state, Ingredient input) {
        super(result);
        this.state = state;
        this.input = input;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        SignalResearchRecipe recipe = new SignalResearchRecipe(
                state, input, result
        );
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
