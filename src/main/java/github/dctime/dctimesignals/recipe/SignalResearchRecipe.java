package github.dctime.dctimesignals.recipe;

import github.dctime.dctimesignals.RegisterRecipeSerializer;
import github.dctime.dctimesignals.RegisterRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SignalResearchRecipe implements Recipe<SignalResearchRecipeInput> {
    private final BlockState inputState;
    private final ItemStack result;
    private final ItemStack input1ItemStack;
    private final ItemStack input2ItemStack;
    private final ItemStack input3ItemStack;

    public SignalResearchRecipe(BlockState inputState, ItemStack input1ItemStack, ItemStack input2ItemStack, ItemStack input3ItemStack, ItemStack result) {
        this.result = result;
        this.inputState = inputState;
        this.input1ItemStack = input1ItemStack;
        this.input2ItemStack = input2ItemStack;
        this.input3ItemStack = input3ItemStack;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.of(input1ItemStack, input2ItemStack, input3ItemStack));
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    // check if recipeInput matches the recipe
    @Override
    public boolean matches(SignalResearchRecipeInput signalResearchRecipeInput, Level level) {
        boolean itemInInput;
        for (ItemStack stack : getIngredients().getFirst().getItems()) {
            itemInInput = false;
            if (stack.is(Items.BARRIER)) continue;
            for (int i = 0; i < signalResearchRecipeInput.size(); i++) {
                if (signalResearchRecipeInput.getItem(i).is(stack.getItem()) && signalResearchRecipeInput.getItem(i).getCount() >= stack.getCount()) {
                    itemInInput = true;
                    break;
                }
            }

            if (!itemInInput) {
                return false;
            }
        }

        return true;
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

    public ItemStack getInput1ItemStack() {
        return this.input1ItemStack;
    }

    public ItemStack getInput2ItemStack() {
        return this.input2ItemStack;
    }

    public ItemStack getInput3ItemStack() {
        return this.input3ItemStack;
    }

    public ItemStack getResult() {
        return this.result;
    }
}
