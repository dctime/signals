package github.dctime.dctimesignals.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record SignalResearchRecipeInput(BlockState state, Ingredient ingredient) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        if (slot < 0 || slot >= 3) throw new IllegalArgumentException("No item for index " + slot);
        if (slot >= ingredient.getItems().length) {
            return ItemStack.EMPTY;
        }
        return this.ingredient.getItems()[slot];
    }

    // input slot size
    @Override
    public int size() {
        return ingredient.getItems().length;
    }
}
