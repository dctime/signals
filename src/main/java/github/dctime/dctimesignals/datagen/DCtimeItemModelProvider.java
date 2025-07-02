package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class DCtimeItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public DCtimeItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DCtimeMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        multiModelBlock(RegisterBlocks.BUILD_HELPER_BLOCK.get(), "build_helper_block_non_destroy_mode");
        multiModelBlock(RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get(), "const_signal_block");
        multiModelBlock(RegisterBlocks.REDSTONE_TO_SIGNAL_CONVERTER.get(), "redstone_to_signal_converter");
        multiModelBlock(RegisterBlocks.SINGAL_WIRE.get(), "signal_wire_none");
        multiModelBlock(RegisterBlocks.SIGNAL_OPERATION_BLOCK.get(), "operation_block_none");
        multiModelBlock(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER.get(), "signal_wire_none");
        simpleBlockItem(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK.get());
//        withExistingParent(getBlockKey(RegisterBlocks.SINGAL_WIRE.get()), mcLoc("item/generated")).texture("layer0", modLoc("block/signal_wire"));
        basicItem(RegisterItems.SIGNAL_DETECTOR.get());
        basicItem(RegisterItems.AND_CARD.get());
        basicItem(RegisterItems.OR_CARD.get());
        basicItem(RegisterItems.NOT_CARD.get());
        basicItem(RegisterItems.PLUS_CARD.get());
        basicItem(RegisterItems.SIGNAL_CONFIGURATOR.get());
        basicItem(RegisterItems.SIGNAL_BLOCKING_MATERIAL.get());
    }

    private String getBlockKey(Block block) {
        return ((ResourceLocation) Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block))).toString();
    }

    private void multiModelBlock(Block block, String modelName) {
        withExistingParent(getBlockKey(block), modLoc("block/" + modelName));
    }
}
