package github.dctime.dctimesignals.recipe;

import github.dctime.dctimesignals.RegisterRecipeSerializer;
import github.dctime.dctimesignals.RegisterRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SignalResearchRecipe implements Recipe<SignalResearchRecipeInput> {
    private final BlockState inputState;
    private final Ingredient inputItem;
    private final ItemStack result;

    public SignalResearchRecipe(BlockState inputState, Ingredient inputItem, ItemStack result) {
        this.inputItem = inputItem;
        this.result = result;
        this.inputState = inputState;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    // check if recipeInput matches the recipe
    // FIXME:  Assume that the recipeInput only takes one item at a time
    @Override
    public boolean matches(SignalResearchRecipeInput signalResearchRecipeInput, Level level) {
        return this.inputState == signalResearchRecipeInput.state() && this.inputItem.test(signalResearchRecipeInput.stack());
    }

    // real result. modifiable
    @Override
    public ItemStack assemble(SignalResearchRecipeInput signalResearchRecipeInput, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    // Return an UNMODIFIABLE version of your result here. The result of this method is mainly intended (private final)
    // for the recipe book, and commonly used by JEI and other recipe viewers as well.
    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    @Override
    public RecipeType<?> getType() {
        return RegisterRecipeTypes.SIGNAL_RESEARCH_RECIPE_TYPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RegisterRecipeSerializer.SIGNAL_RESEARCH_RECIPE.get();
    }


    public static BlockState getInputState(SignalResearchRecipe recipe) {
        return recipe.inputState;
    }

    public static Ingredient getInputItem(SignalResearchRecipe recipe) {
        return recipe.inputItem;
    }

    public ItemStack getResult() {
        return this.result;
    }
}
