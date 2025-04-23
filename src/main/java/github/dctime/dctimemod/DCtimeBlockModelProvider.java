package github.dctime.dctimemod;

import github.dctime.dctimemod.block.BuildHelperBlock;
import github.dctime.dctimemod.block.RegisterBlocks;
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
            return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("signal_wire_east_west"))).build();
        });
    }
}
