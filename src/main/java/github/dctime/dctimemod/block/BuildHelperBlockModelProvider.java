package github.dctime.dctimemod.block;

import github.dctime.dctimemod.DCtimeMod;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Collections;

public class BuildHelperBlockModelProvider extends BlockStateProvider {


    public BuildHelperBlockModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DCtimeMod.MODID, exFileHelper);
    }

//    @Override
//    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        // block model
//        final ExtendedModelTemplate CUTOUT_CUBE_ALL = ModelTemplates.CUBE_ALL.extend().renderType("minecraft:cutout").build();
//        Block buildHelperBlock = RegisterBlocks.BUILD_HELPER_BLOCK.get();
//        ResourceLocation buildHelperBlockDestroyModeModelLoc = CUTOUT_CUBE_ALL.createWithSuffix(
//            RegisterBlocks.BUILD_HELPER_BLOCK.get(),
//            "_destroy_mode",
//            new TextureMapping()
//                .put(TextureSlot.ALL, TextureMapping.getBlockTexture(buildHelperBlock, "_destroy_mode")),
//            blockModels.modelOutput);
//
//        ResourceLocation buildHelperBlockNonDestroyModeModelLoc = ModelTemplates.CUBE_ALL.createWithSuffix(
//            RegisterBlocks.BUILD_HELPER_BLOCK.get(),
//            "_non_destroy_mode",
//            new TextureMapping()
//                .put(TextureSlot.ALL, TextureMapping.getBlockTexture(buildHelperBlock, "_non_destroy_mode")),
//            blockModels.modelOutput);
//
//        blockModels.blockStateOutput.accept(
//            MultiVariantGenerator.multiVariant(buildHelperBlock)
//                .with(
//                    PropertyDispatch.property(BuildHelperBlock.DESTROY_MODE)
//                        .select(
//                            true,
//                            Variant.variant().with(VariantProperties.MODEL, buildHelperBlockDestroyModeModelLoc)
//                        )
//                        .select(
//                            false,
//                            Variant.variant().with(VariantProperties.MODEL, buildHelperBlockNonDestroyModeModelLoc)
//                        )
//                )
//        );
//
//        // item model
//        itemModels.itemModelOutput.accept(
//            RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM.get(),
//            new BlockModelWrapper.Unbaked(
//                ModelLocationUtils.getModelLocation(RegisterBlocks.BUILD_HELPER_BLOCK.get(), "_non_destroy_mode"),
//                Collections.emptyList()
//            )
//        );
//    }

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
    }
}
