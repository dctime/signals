package github.dctime.dctimemod;

import github.dctime.dctimemod.block.BuildHelperBlock;
import github.dctime.dctimemod.block.SignalToRedstoneConverter;
import github.dctime.dctimemod.block.SignalWireBlock;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DCtimeBlockModelProvider extends BlockStateProvider {


    public DCtimeBlockModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DCtimeMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        VariantBlockStateBuilder variantBuilder = getVariantBuilder(RegisterBlocks.BUILD_HELPER_BLOCK.get());

        variantBuilder.forAllStates((state)->{
           if (state.getValue(BuildHelperBlock.DESTROY_MODE)) {
               return ConfiguredModel.builder()
                       .modelFile(models().cubeAll("build_helper_block_destroy_mode", modLoc("block/build_helper_block_destroy_mode"))
                               .renderType("minecraft:cutout")).build();
           } else {
               return ConfiguredModel.builder()
                       .modelFile(models().cubeAll("build_helper_block_non_destroy_mode", modLoc("block/build_helper_block_non_destroy_mode"))).build();
           }
        });

        MultiPartBlockStateBuilder multiPartBuilderSignal = getMultipartBuilder(RegisterBlocks.SINGAL_WIRE.get());
        multiPartBuilderSignal.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_none")))
                .addModel()
                .end();
        // north
        multiPartBuilderSignal.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .addModel()
                .condition(SignalWireBlock.NORTH, true)
                .end();
        //south
        multiPartBuilderSignal.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationY(180)
                .addModel()
                .condition(SignalWireBlock.SOUTH, true)
                .end();
        // EAST
        multiPartBuilderSignal.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationY(90)
                .addModel()
                .condition(SignalWireBlock.EAST, true)
                .end();
        // WEST
        multiPartBuilderSignal.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationY(270)
                .addModel()
                .condition(SignalWireBlock.WEST, true)
                .end();
        // UP
        multiPartBuilderSignal.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationX(270)
                .addModel()
                .condition(SignalWireBlock.UP, true)
                .end();
        // DOWN
        multiPartBuilderSignal.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationX(90)
                .addModel()
                .condition(SignalWireBlock.DOWN, true)
                .end();

        ModelFile constSignalModel = models().orientableVertical(
                "const_signal_block",
                modLoc("block/const_signal_block_side"),
                modLoc("block/const_signal_block_front")
        );


        directionalBlock(RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get(), constSignalModel);

        MultiPartBlockStateBuilder signalToRedstoneBuilder = getMultipartBuilder(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER.get());
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_none")))
                .addModel()
                .end();
        // north
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .addModel()
                .condition(SignalWireBlock.NORTH, true)
                .condition(SignalToRedstoneConverter.NORTH, false)
                .end();
        //south
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationY(180)
                .addModel()
                .condition(SignalWireBlock.SOUTH, true)
                .condition(SignalToRedstoneConverter.SOUTH, false)
                .end();
        // EAST
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationY(90)
                .addModel()
                .condition(SignalWireBlock.EAST, true)
                .condition(SignalToRedstoneConverter.EAST, false)
                .end();
        // WEST
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationY(270)
                .addModel()
                .condition(SignalWireBlock.WEST, true)
                .condition(SignalToRedstoneConverter.WEST, false)
                .end();
        // UP
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationX(270)
                .addModel()
                .condition(SignalWireBlock.UP, true)
                .condition(SignalToRedstoneConverter.UP, false)
                .end();
        // DOWN
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_wire_side_north")))
                .rotationX(90)
                .addModel()
                .condition(SignalWireBlock.DOWN, true)
                .condition(SignalToRedstoneConverter.DOWN, false)
                .end();
        // REDSTONE OUTPUT NORTH
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_to_redstone_converter_north")))
                .addModel()
                .condition(SignalToRedstoneConverter.NORTH, true)
                .condition(SignalWireBlock.NORTH, false)
                .end();
        // REDSTONE OUTPUT SOUTH
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_to_redstone_converter_north")))
                .rotationY(180)
                .addModel()
                .condition(SignalToRedstoneConverter.SOUTH, true)
                .condition(SignalWireBlock.SOUTH, false)
                .end();
        // REDSTONE OUTPUT EAST
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_to_redstone_converter_north")))
                .rotationY(90)
                .addModel()
                .condition(SignalToRedstoneConverter.EAST, true)
                .condition(SignalWireBlock.EAST, false)
                .end();
        // REDSTONE OUTPUT WEST
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_to_redstone_converter_north")))
                .rotationY(270)
                .addModel()
                .condition(SignalToRedstoneConverter.WEST, true)
                .condition(SignalWireBlock.WEST, false)
                .end();
        // REDSTONE OUTPUT UP
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_to_redstone_converter_north")))
                .rotationX(270)
                .addModel()
                .condition(SignalToRedstoneConverter.UP, true)
                .condition(SignalWireBlock.UP, false)
                .end();
        // REDSTONE OUTPUT DOWN
        signalToRedstoneBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/signal_to_redstone_converter_north")))
                .rotationX(90)
                .addModel()
                .condition(SignalToRedstoneConverter.DOWN, true)
                .condition(SignalWireBlock.DOWN, false)
                .end();
    }
}
