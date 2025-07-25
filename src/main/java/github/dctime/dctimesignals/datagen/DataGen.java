package github.dctime.dctimesignals.datagen;

import com.google.common.collect.ImmutableMap;
import github.dctime.dctimesignals.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.OptionalLong;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid= DCtimeMod.MODID, bus= EventBusSubscriber.Bus.MOD)
public class DataGen {
    public static final ResourceKey<DimensionType> SIGNAL_WORLD_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world"));
    public static final ResourceKey<LevelStem> SIGNAL_WORLD = ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world"));
    public static final ResourceKey<NoiseGeneratorSettings> SIGNAL_WORLD_NOISE_GENERATOR_SETTINGS = ResourceKey.create(
            Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world")
    );
    protected static final NoiseSettings OVERWORLD_NOISE_SETTINGS = NoiseSettings.create(-64, 384, 1, 2);
    public static final ResourceKey<Biome> SINGAL_WORLD_BIOME = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world_biome"));


    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        // crafting recipes
        generator.addProvider(
                event.includeServer(),
                new DCtimeRecipeProvider(output, lookupProvider)
        );

        // tags
        event.getGenerator().addProvider(
                event.includeServer(),
                new DCtimeBlockTagsProvider(output, lookupProvider, existingFileHelper)
        );

        // block loot tables
        event.createProvider(((packOutput, completableFuture) -> new LootTableProvider(
                packOutput,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        BlockLootSubProvider::new,
                        LootContextParamSets.BLOCK
                )),
                completableFuture
        )));

        // block models
        generator.addProvider(
                event.includeClient(),
                new DCtimeBlockModelProvider(output, existingFileHelper)
        );

        //item models
        generator.addProvider(
                event.includeClient(),
                new DCtimeItemModelProvider(output, existingFileHelper)
        );
        // language provider
        event.createProvider(DCtimeLanguageProvider::new);

        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();
        DataGenConfiguredFeature.registerSignalConfiguredFeature(registrySetBuilder);
        DataGenPlacedFeature.registerSignalPlacedFeature(registrySetBuilder);
        DataGenStructureTemplatePool.registerStructureTemplatePool(registrySetBuilder);
        DataGenStructure.registerStructure(registrySetBuilder);
        DataGenStructureSet.registerStructureSet(registrySetBuilder);

        // new dimension types
        generator.addProvider(
            event.includeServer(),
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) packOutput -> new DatapackBuiltinEntriesProvider(
                    packOutput,
                    event.getLookupProvider(),
                    registrySetBuilder.add(
                            Registries.DIMENSION_TYPE,
                            bootstrapContext -> {
                                bootstrapContext.register(SIGNAL_WORLD_TYPE, new DimensionType(OptionalLong.empty(), true, false, false, true, (double)1.0F, false, false, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0f, new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));
                            }
                    ).add(
                            Registries.LEVEL_STEM,
                            bootstrapContext -> {
                                NoiseBasedChunkGenerator chunkGen = new NoiseBasedChunkGenerator(new FixedBiomeSource(bootstrapContext.lookup(Registries.BIOME).getOrThrow(SINGAL_WORLD_BIOME)), bootstrapContext.lookup(Registries.NOISE_SETTINGS).getOrThrow(SIGNAL_WORLD_NOISE_GENERATOR_SETTINGS));
                                bootstrapContext.register(SIGNAL_WORLD, new LevelStem(bootstrapContext.lookup(Registries.DIMENSION_TYPE).getOrThrow(SIGNAL_WORLD_TYPE), chunkGen));
                            }
                    ).add(
                            Registries.NOISE_SETTINGS,
                            bootstrapContext -> {
                                bootstrapContext.register(SIGNAL_WORLD_NOISE_GENERATOR_SETTINGS,
                                        new NoiseGeneratorSettings(
                                                OVERWORLD_NOISE_SETTINGS,
                                                Blocks.STONE.defaultBlockState(),
                                                Blocks.WATER.defaultBlockState(),
                                                // NoiseRouterDataGetter.overworldGetter(bootstrapContext.lookup(Registries.DENSITY_FUNCTION), bootstrapContext.lookup(Registries.NOISE), false, false),
                                                new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(),
                                                        DensityFunctions.add(
                                                                DensityFunctions.mul(
                                                                        DensityFunctions.yClampedGradient(50, 75, -1, 1),
                                                                        DensityFunctions.noise(bootstrapContext.lookup(Registries.NOISE).getOrThrow(Noises.CALCITE), 10, 20)
                                                                ),
                                                                DensityFunctions.mul(
                                                                        DensityFunctions.yClampedGradient(25, 50, 1, -1),
                                                                        DensityFunctions.noise(bootstrapContext.lookup(Registries.NOISE).getOrThrow(Noises.NOODLE), 10, 20)
                                                                )
                                                        ),
                                                        DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero()),
                                                SurfaceRules.sequence(SurfaceRules.ifTrue(
                                                        SurfaceRules.stoneDepthCheck(3, true, CaveSurface.FLOOR),
                                                        SurfaceRules.state(Blocks.STONE.defaultBlockState())
                                                ), SurfaceRules.ifTrue(
                                                        SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.aboveBottom(4), 1)),
                                                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState()
                                                        )
                                                )),
                                                List.of(),
                                                0,
                                                true,
                                                true,
                                                false,
                                                false
                                        )
                                );
                            }
                    ).add(
                            Registries.BIOME,
                            bootstrapContext -> {
                                Holder.Reference<PlacedFeature> dripstoneFeature = bootstrapContext.lookup(Registries.PLACED_FEATURE).getOrThrow(DataGenPlacedFeature.SIGNAL_DRIPSTONE_CLUSTER_PLACED);
                                Holder.Reference<PlacedFeature> redstoneFeature = bootstrapContext.lookup(Registries.PLACED_FEATURE).getOrThrow(DataGenPlacedFeature.SIGNAL_REDSTONE_ORE_PLACED);
                                Holder.Reference<PlacedFeature> blockingChunkFeature = bootstrapContext.lookup(Registries.PLACED_FEATURE).getOrThrow(DataGenPlacedFeature.SIGNAL_BLOCKING_MATERIAL_CHUNK_PLACED);
                                Holder.Reference<PlacedFeature> aetheriteCeramicFeature = bootstrapContext.lookup(Registries.PLACED_FEATURE).getOrThrow(DataGenPlacedFeature.AETHERITE_CERAMIC_CHUNK_PLACED);
                                bootstrapContext.register(SINGAL_WORLD_BIOME, new Biome.BiomeBuilder()
                                        .hasPrecipitation(true)
                                        .temperature(0.8F)
                                        .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                                        .downfall(0.4F)
                                        .specialEffects(new BiomeSpecialEffects.Builder().skyColor(16761261).fogColor(16761261).waterColor(16761261).waterFogColor(16761261).grassColorOverride(16761261).foliageColorOverride(16761261).ambientParticle(new AmbientParticleSettings(DustParticleOptions.REDSTONE, 0.01f)).build())
                                        .generationSettings(new BiomeGenerationSettings(ImmutableMap.of(), List.of(HolderSet.direct(List.of(dripstoneFeature, redstoneFeature, blockingChunkFeature, aetheriteCeramicFeature)))))
                                        .mobSpawnSettings(MobSpawnSettings.EMPTY)
                                        .build()
                                );
                            }
                    ),
                    Set.of(DCtimeMod.MODID)
                )
        );

        // particles
        generator.addProvider(
                event.includeClient(),
                new DCtimeParticleDescriptionProvider(output, existingFileHelper)
        );

        // sounds
        generator.addProvider(
                event.includeClient(),
                new DCtimeSoundDefinitionsProvider(output, existingFileHelper)
        );


    }
}
