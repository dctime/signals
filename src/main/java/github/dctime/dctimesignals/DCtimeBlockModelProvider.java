package github.dctime.dctimesignals;

import github.dctime.dctimesignals.block.*;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class DCtimeBlockModelProvider extends BlockStateProvider {


    public DCtimeBlockModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DCtimeMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        buildHelperModel();
        signalWireModel();
        constSignalModel();
        signalToRedstoneModel();
        operationSignalModel();
        redstoneToSignalModel();
        simpleBlock(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK.get());
        portalBlockModel();
    }

    private void signalWireModel() {
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
    }

    private void constSignalModel() {
        ModelFile constSignalModel = models().orientableVertical(
                "const_signal_block",
                modLoc("block/const_signal_block_side"),
                modLoc("block/const_signal_block_front")
        );
        Block constSignalBlock = RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get();

        Function<BlockState, ModelFile> constModelFunc = (Function) (($) -> constSignalModel);
        this.getVariantBuilder(constSignalBlock).forAllStates((state) -> {
            Direction dir = (Direction) state.getValue(ConstSignalBlock.OUTPUT_DIRECTION);
            return ConfiguredModel.builder()
                    .modelFile((ModelFile) constModelFunc.apply(state))
                    .rotationX(dir == Direction.DOWN ? 180 : (dir.getAxis().isHorizontal() ? 90 : 0))
                    .rotationY(dir.getAxis().isVertical() ? 0 : ((int) dir.toYRot() + 180) % 360)
                    .build();
        });
    }

    private void signalToRedstoneModel() {
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

    private void operationSignalModel() {
        MultiPartBlockStateBuilder operationBlockBuilder = getMultipartBuilder(RegisterBlocks.SIGNAL_OPERATION_BLOCK.get());
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_block_none")))
                .addModel()
                .end();

        // inputs
        // north
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north")))
                .addModel()
                .condition(SignalOperationBlock.NORTH_SIDE_MODE, SignalOperationBlock.SideMode.INPUT)
                .end();
        //south
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north")))
                .rotationY(180)
                .addModel()
                .condition(SignalOperationBlock.SOUTH_SIDE_MODE, SignalOperationBlock.SideMode.INPUT)
                .end();
        // EAST
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north")))
                .rotationY(90)
                .addModel()
                .condition(SignalOperationBlock.EAST_SIDE_MODE, SignalOperationBlock.SideMode.INPUT)
                .end();
        // WEST
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north")))
                .rotationY(270)
                .addModel()
                .condition(SignalOperationBlock.WEST_SIDE_MODE, SignalOperationBlock.SideMode.INPUT)
                .end();
        // UP
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north")))
                .rotationX(270)
                .addModel()
                .condition(SignalOperationBlock.UP_SIDE_MODE, SignalOperationBlock.SideMode.INPUT)
                .end();
        // DOWN
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north")))
                .rotationX(90)
                .addModel()
                .condition(SignalOperationBlock.DOWN_SIDE_MODE, SignalOperationBlock.SideMode.INPUT)
                .end();

        // outputs
        // north
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_output_north")))
                .addModel()
                .condition(SignalOperationBlock.NORTH_SIDE_MODE, SignalOperationBlock.SideMode.OUTPUT)
                .end();
        //south
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_output_north")))
                .rotationY(180)
                .addModel()
                .condition(SignalOperationBlock.SOUTH_SIDE_MODE, SignalOperationBlock.SideMode.OUTPUT)
                .end();
        // EAST
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_output_north")))
                .rotationY(90)
                .addModel()
                .condition(SignalOperationBlock.EAST_SIDE_MODE, SignalOperationBlock.SideMode.OUTPUT)
                .end();
        // WEST
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_output_north")))
                .rotationY(270)
                .addModel()
                .condition(SignalOperationBlock.WEST_SIDE_MODE, SignalOperationBlock.SideMode.OUTPUT)
                .end();
        // UP
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_output_north")))
                .rotationX(270)
                .addModel()
                .condition(SignalOperationBlock.UP_SIDE_MODE, SignalOperationBlock.SideMode.OUTPUT)
                .end();
        // DOWN
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_output_north")))
                .rotationX(90)
                .addModel()
                .condition(SignalOperationBlock.DOWN_SIDE_MODE, SignalOperationBlock.SideMode.OUTPUT)
                .end();

        // outputs
        // north
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north_2")))
                .addModel()
                .condition(SignalOperationBlock.NORTH_SIDE_MODE, SignalOperationBlock.SideMode.INPUT2)
                .end();
        //south
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north_2")))
                .rotationY(180)
                .addModel()
                .condition(SignalOperationBlock.SOUTH_SIDE_MODE, SignalOperationBlock.SideMode.INPUT2)
                .end();
        // EAST
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north_2")))
                .rotationY(90)
                .addModel()
                .condition(SignalOperationBlock.EAST_SIDE_MODE, SignalOperationBlock.SideMode.INPUT2)
                .end();
        // WEST
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north_2")))
                .rotationY(270)
                .addModel()
                .condition(SignalOperationBlock.WEST_SIDE_MODE, SignalOperationBlock.SideMode.INPUT2)
                .end();
        // UP
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north_2")))
                .rotationX(270)
                .addModel()
                .condition(SignalOperationBlock.UP_SIDE_MODE, SignalOperationBlock.SideMode.INPUT2)
                .end();
        // DOWN
        operationBlockBuilder.part()
                .modelFile(models().getExistingFile(modLoc("block/operation_input_north_2")))
                .rotationX(90)
                .addModel()
                .condition(SignalOperationBlock.DOWN_SIDE_MODE, SignalOperationBlock.SideMode.INPUT2)
                .end();
    }

    private void buildHelperModel() {
        VariantBlockStateBuilder variantBuilder = getVariantBuilder(RegisterBlocks.BUILD_HELPER_BLOCK.get());

        variantBuilder.forAllStates((state) -> {
            if (state.getValue(BuildHelperBlock.DESTROY_MODE)) {
                return ConfiguredModel.builder()
                        .modelFile(models().cubeAll("build_helper_block_destroy_mode", modLoc("block/build_helper_block_destroy_mode"))
                                .renderType("minecraft:cutout")).build();
            } else {
                return ConfiguredModel.builder()
                        .modelFile(models().cubeAll("build_helper_block_non_destroy_mode", modLoc("block/build_helper_block_non_destroy_mode"))).build();
            }
        });
    }

    private void redstoneToSignalModel() {
        ModelFile restoneToSignalModel = models().orientableVertical(
                "redstone_to_signal_converter",
                modLoc("block/redstone_to_signal_block_side"),
                modLoc("block/const_signal_block_front")
        );
        Block redstoneToSignalBlock = RegisterBlocks.REDSTONE_TO_SIGNAL_CONVERTER.get();

        Function<BlockState, ModelFile> redstoneToSignalModelFunc = (Function) (($) -> restoneToSignalModel);
        this.getVariantBuilder(redstoneToSignalBlock).forAllStates((state) -> {
            Direction dir = (Direction) state.getValue(ConstSignalBlock.OUTPUT_DIRECTION);
            return ConfiguredModel.builder()
                    .modelFile((ModelFile) redstoneToSignalModelFunc.apply(state))
                    .rotationX(dir == Direction.DOWN ? 180 : (dir.getAxis().isHorizontal() ? 90 : 0))
                    .rotationY(dir.getAxis().isVertical() ? 0 : ((int) dir.toYRot() + 180) % 360)
                    .build();
        });
    }

    private void portalBlockModel() {
        VariantBlockStateBuilder variantBuilder = getVariantBuilder(RegisterBlocks.SINGAL_WORLD_PORTAL.get());
        variantBuilder.forAllStates(state -> {
            // Return a ConfiguredModel depending on the state's properties.
            // For example, the following code will rotate the model depending on the horizontal rotation of the block.
            return ConfiguredModel.builder()
                    .modelFile(models().cubeAll("signal_world_portal", modLoc("block/signal_world_portal")).renderType("minecraft:translucent"))
                    .build();
        });
    }
}
