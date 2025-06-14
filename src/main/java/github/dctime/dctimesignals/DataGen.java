package github.dctime.dctimesignals;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.OptionalLong;
import java.util.Set;

@EventBusSubscriber(modid=DCtimeMod.MODID, bus= EventBusSubscriber.Bus.MOD)
public class DataGen {
    public static final ResourceKey<DimensionType> SIGNAL_WORLD_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world"));
    public static final ResourceKey<LevelStem> SIGNAL_WORLD = ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world"));
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

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



        // new dimension types
        generator.addProvider(
            event.includeServer(),
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) packOutput -> new DatapackBuiltinEntriesProvider(
                    packOutput,
                    event.getLookupProvider(),
                    new RegistrySetBuilder().add(
                            Registries.DIMENSION_TYPE,
                            bootstrapContext -> {
                                bootstrapContext.register(SIGNAL_WORLD_TYPE, new DimensionType(OptionalLong.empty(), true, false, false, true, (double)1.0F, true, false, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0F, new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)));
                            }
                    ).add(
                            Registries.LEVEL_STEM,
                            bootstrapContext -> {
                                FlatLevelGeneratorSettings settings = FlatLevelGeneratorSettings.getDefault(bootstrapContext.lookup(Registries.BIOME), bootstrapContext.lookup(Registries.STRUCTURE_SET), bootstrapContext.lookup(Registries.PLACED_FEATURE));
                                bootstrapContext.register(SIGNAL_WORLD, new LevelStem(bootstrapContext.lookup(Registries.DIMENSION_TYPE).getOrThrow(SIGNAL_WORLD_TYPE), new FlatLevelSource(settings)));
                            }
                    ),
                    Set.of(DCtimeMod.MODID)
                )
        );



    }
}
