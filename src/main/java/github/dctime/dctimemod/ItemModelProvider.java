package github.dctime.dctimemod;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DCtimeMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        multiModelBlock(RegisterBlocks.BUILD_HELPER_BLOCK.get(), "build_helper_block_non_destroy_mode");
        multiModelBlock(RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get(), "const_signal_block");
        multiModelBlock(RegisterBlocks.SINGAL_WIRE.get(), "signal_wire_none");
        multiModelBlock(RegisterBlocks.SIGNAL_OPERATION_BLOCK.get(), "operation_block_none");
        multiModelBlock(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER.get(), "signal_wire_none");
        basicItem(RegisterItems.SIGNAL_DETECTOR.get());
        basicItem(RegisterItems.AND_CARD.get());
        basicItem(RegisterItems.OR_CARD.get());
        basicItem(RegisterItems.NOT_CARD.get());
    }

    private String getBlockKey(Block block) {
        return ((ResourceLocation) Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block))).toString();
    }

    private void multiModelBlock(Block block, String modelName) {
        withExistingParent(getBlockKey(block), modLoc("block/" + modelName));
    }
}
