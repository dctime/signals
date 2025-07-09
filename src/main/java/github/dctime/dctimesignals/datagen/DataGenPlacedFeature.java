package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class DataGenPlacedFeature {
    public static final ResourceKey<PlacedFeature> SIGNAL_DRIPSTONE_CLUSTER_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_dripstone_cluster"));
    public static final ResourceKey<PlacedFeature> SIGNAL_REDSTONE_ORE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world_redstone_ore"));
    public static final ResourceKey<PlacedFeature> SIGNAL_BLOCKING_MATERIAL_CHUNK_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_blocking_material_chunk"));
    public static final ResourceKey<PlacedFeature> AETHERITE_CERAMIC_CHUNK_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "aetherite_ceramic_chunk"));

    public static void registerSignalPlacedFeature(RegistrySetBuilder builder) {
        // Register your configured features here
        // Example: builder.add(RegistryKeys.CONFIGURED_FEATURE, MyConfiguredFeatures.MY_ORE);
        builder.add(Registries.PLACED_FEATURE, bootStrap -> {
            registerSignalDripstonePlace(bootStrap);
            registerSignalRedstoneOrePlace(bootStrap);
            registerSignalBlockingChunkPlace(bootStrap);
            registerAetheriteCeramicChunkPlace(bootStrap);
        });
    }

    public static void registerSignalDripstonePlace(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(DataGenConfiguredFeature.SIGNAL_DRIPSTONE_CLUSTER);
        PlacementUtils.register(context, SIGNAL_DRIPSTONE_CLUSTER_PLACED, holder, new PlacementModifier[]{CountPlacement.of(UniformInt.of(48, 96)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()});
    }

    public static void registerSignalRedstoneOrePlace(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(DataGenConfiguredFeature.SIGNAL_REDSTONE_ORE);
        PlacementUtils.register(context, SIGNAL_REDSTONE_ORE_PLACED, holder, new PlacementModifier[]{CountPlacement.of(UniformInt.of(96, 192)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()});
    }

    public static void registerSignalBlockingChunkPlace(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(DataGenConfiguredFeature.SIGNAL_BLOCKING_CHUNK);
        PlacementUtils.register(context, SIGNAL_BLOCKING_MATERIAL_CHUNK_PLACED, holder, new PlacementModifier[]{CountPlacement.of(UniformInt.of(48, 96)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()});
    }

    public static void registerAetheriteCeramicChunkPlace(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder = holdergetter.getOrThrow(DataGenConfiguredFeature.AETHERITE_CERAMIC_CHUNK);
        PlacementUtils.register(
            context,
            AETHERITE_CERAMIC_CHUNK_PLACED,
            holder,
            new PlacementModifier[]{
                CountPlacement.of(UniformInt.of(64, 128)), // much more common
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                    net.minecraft.world.level.levelgen.VerticalAnchor.absolute(-64),
                    net.minecraft.world.level.levelgen.VerticalAnchor.absolute(0)
                ),
                BiomeFilter.biome()
            }
        );
    }
}
