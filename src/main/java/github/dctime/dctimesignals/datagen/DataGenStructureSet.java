package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public class DataGenStructureSet {
    public static final ResourceKey<StructureSet> SIGNAL_WORLD_PORTAL =
            ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world_portal"));

    public static void registerStructureSet(RegistrySetBuilder builder) {
        builder.add(Registries.STRUCTURE_SET, bootStrap -> {
            HolderGetter<Structure> holdergetter = bootStrap.lookup(Registries.STRUCTURE);
            bootStrap.register(SIGNAL_WORLD_PORTAL,
                    new StructureSet(
                            holdergetter.getOrThrow(DataGenStructure.SIGNAL_WORLD_PORTAL),
                            new RandomSpreadStructurePlacement(
                                    4,
                                    1,
                                    RandomSpreadType.LINEAR,
                                    0
                            )
                    )
            );
        });
    }
}
