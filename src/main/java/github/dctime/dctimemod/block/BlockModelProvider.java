package github.dctime.dctimemod.block;

import com.nimbusds.jose.util.Resource;
import github.dctime.dctimemod.DCtimeMod;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplate;

import java.util.function.BiConsumer;

public class BlockModelProvider extends ModelProvider {
    public BlockModelProvider(PackOutput output) {
        super(output, "dctimemod");
    }


    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        final ExtendedModelTemplate CUTOUT_CUBE_ALL = ModelTemplates.CUBE_ALL.extend().renderType("minecraft:cutout").build();
        Block buildHelperBlock = RegisterBlocks.BUILD_HELPER_BLOCK.get();
        ResourceLocation buildHelperBlockDestroyModeModelLoc = CUTOUT_CUBE_ALL.createWithSuffix(
            RegisterBlocks.BUILD_HELPER_BLOCK.get(),
                "_destroy_mode",
                new TextureMapping()
                        .put(TextureSlot.ALL, TextureMapping.getBlockTexture(buildHelperBlock, "_destroy_mode")),
                blockModels.modelOutput);

        ResourceLocation buildHelperBlockNonDestroyModeModelLoc = ModelTemplates.CUBE_ALL.createWithSuffix(
                RegisterBlocks.BUILD_HELPER_BLOCK.get(),
                "_non_destroy_mode",
                new TextureMapping()
                        .put(TextureSlot.ALL, TextureMapping.getBlockTexture(buildHelperBlock, "_non_destroy_mode")),
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(buildHelperBlock)
                        .with(
                                PropertyDispatch.property(BuildHelperBlock.DESTROY_MODE)
                                        .select(
                                                true,
                                                Variant.variant().with(VariantProperties.MODEL, buildHelperBlockDestroyModeModelLoc)
                                        )
                                        .select(
                                                false,
                                                Variant.variant().with(VariantProperties.MODEL, buildHelperBlockNonDestroyModeModelLoc)
                                        )
                        )
        );
    }
}
