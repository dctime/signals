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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public class DataGenStructure {
    public static final ResourceKey<Structure> SIGNAL_WORLD_PORTAL =
            ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world_portal"));

    public static void registerStructure(RegistrySetBuilder builder) {
        builder.add(Registries.STRUCTURE, bootStrap -> {
            HolderGetter<Biome> biomeGetter = bootStrap.lookup(Registries.BIOME);
            Holder<StructureTemplatePool> pool = bootStrap.lookup(Registries.TEMPLATE_POOL).getOrThrow(DataGenStructureTemplatePool.SIGNAL_WORLD_PORTAL);
            bootStrap.register(SIGNAL_WORLD_PORTAL,
                    new JigsawStructure(
                            new Structure.StructureSettings.Builder(biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD)).build(),
                            pool,
                            2,
                            UniformHeight.of(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(-20)),
                            false,
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES
                    )
            );
        });
    }
}
