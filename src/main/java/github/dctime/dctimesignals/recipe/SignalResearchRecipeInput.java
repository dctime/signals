package github.dctime.dctimesignals.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;

public record SignalResearchRecipeInput(BlockState state, ItemStack stack) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        if (slot != 0) throw new IllegalArgumentException("No item for index " + slot);
        return this.stack();
    }

    // input slot size
    @Override
    public int size() {
        return 1;
    }
}
