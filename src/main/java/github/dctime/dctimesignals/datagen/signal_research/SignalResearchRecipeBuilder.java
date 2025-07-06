package github.dctime.dctimesignals.datagen.signal_research;

import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;

public class SignalResearchRecipeBuilder extends SimpleRecipeBuilder{

    private final BlockState state;
    private final ItemStack inputStack1;
    private final ItemStack inputStack2;
    private final ItemStack inputStack3;

    public SignalResearchRecipeBuilder(ItemStack result, BlockState state, ItemStack inputStack1, ItemStack inputStack2, ItemStack inputStack3) {
        super(result);
        this.state = state;

        if (inputStack1.isEmpty()) this.inputStack1 = new ItemStack(Items.BARRIER);
        else this.inputStack1 = inputStack1;

        if (inputStack2.isEmpty()) this.inputStack2 = new ItemStack(Items.BARRIER);
        else this.inputStack2 = inputStack2;

        if (inputStack3.isEmpty()) this.inputStack3 = new ItemStack(Items.BARRIER);
        else this.inputStack3 = inputStack3;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        SignalResearchRecipe recipe = new SignalResearchRecipe(
                state, inputStack1, inputStack2, inputStack3, result
        );
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
