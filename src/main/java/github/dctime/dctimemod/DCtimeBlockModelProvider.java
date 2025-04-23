package github.dctime.dctimemod;

import github.dctime.dctimemod.block.BuildHelperBlock;
import github.dctime.dctimemod.block.RegisterBlocks;
import github.dctime.dctimemod.block.SignalWireBlock;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
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


        VariantBlockStateBuilder variantBuilderSignalWire = getVariantBuilder(RegisterBlocks.SINGAL_WIRE.get());

        variantBuilderSignalWire.forAllStates((state)-> {
            if (state.getValue(SignalWireBlock.EAST) && state.getValue(SignalWireBlock.WEST))
                return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("signal_wire_north_south"))).rotationY(90).build();
            else if (state.getValue(SignalWireBlock.NORTH) && state.getValue(SignalWireBlock.SOUTH))
                return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("signal_wire_north_south"))).build();
            else
                return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("signal_wire_north_south"))).rotationX(90).build();
        });
    }
}
