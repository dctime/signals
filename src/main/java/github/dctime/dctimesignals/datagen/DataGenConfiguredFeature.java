package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterFeatures;
import github.dctime.dctimesignals.configuration.SignalBlockingMaterialChunkConfiguration;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

public class DataGenConfiguredFeature {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SIGNAL_REDSTONE_ORE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world_redstone_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SIGNAL_DRIPSTONE_CLUSTER =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_dripstone_cluster"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SIGNAL_BLOCKING_CHUNK =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_blocking_material_chunk"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHERITE_CERAMIC_CHUNK =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "aetherite_ceramic_chunk"));

    public static void registerSignalConfiguredFeature(RegistrySetBuilder builder) {
        // Register your configured features here
        // Example: builder.add(RegistryKeys.CONFIGURED_FEATURE, MyConfiguredFeatures.MY_ORE);
        builder.add(Registries.CONFIGURED_FEATURE, bootStrap -> {
            registerSignalRedstoneOre(bootStrap);
            registerSignalDripstone(bootStrap);
            registerSignalBlockingChunk(bootStrap);
            registerAetheriteCeramicChunk(bootStrap);
        });
    }

    private static void registerSignalRedstoneOre(BootstrapContext<ConfiguredFeature<?,?>> context) {
        context.register(
                SIGNAL_REDSTONE_ORE,
                new ConfiguredFeature<>(
                        Feature.ORE,
                        new OreConfiguration(
                                new BlockMatchTest(Blocks.STONE),
                                Blocks.REDSTONE_ORE.defaultBlockState(),
                                20
                        )
                )
        );
    }
    private static void registerSignalDripstone(BootstrapContext<ConfiguredFeature<?,?>> context) {
        FeatureUtils.register(
                context,
                SIGNAL_DRIPSTONE_CLUSTER,
                Feature.DRIPSTONE_CLUSTER,
                new DripstoneClusterConfiguration(
                        12,
                        UniformInt.of(3, 6),
                        UniformInt.of(2, 8),
                        1,
                        3,
                        UniformInt.of(0, 1),
                        UniformFloat.of(0.3F, 0.7F),
                        ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F),
                        0.1F,
                        3,
                        8
                )
        );
    }

    private static void registerSignalBlockingChunk(BootstrapContext<ConfiguredFeature<?,?>> context) {
        FeatureUtils.register(
                context,
                SIGNAL_BLOCKING_CHUNK,
                RegisterFeatures.SIGNAL_BLOCKING_MATERIAL_CHUNK.get(),
                new SignalBlockingMaterialChunkConfiguration()
        );
    }

    private static void registerAetheriteCeramicChunk(BootstrapContext<ConfiguredFeature<?,?>> context) {
        context.register(
            AETHERITE_CERAMIC_CHUNK,
            new ConfiguredFeature<>(
                Feature.ORE, // If you have a custom chunk feature, use it here
                new OreConfiguration(
                    new BlockMatchTest(Blocks.STONE),
                    github.dctime.dctimesignals.RegisterBlocks.AETHERITE_CERAMIC_BLOCK.get().defaultBlockState(),
                    12 // vein size or chunk size
                )
            )
        );
    }
}
