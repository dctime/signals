package github.dctime.dctimesignals;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DataGenStructureTemplatePool {
    public static final ResourceKey<StructureTemplatePool> ORE_CHUNK_PORTAL =
            ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world_portal"));


    public static void registerStructureTemplatePool(RegistrySetBuilder builder) {
        builder.add(Registries.TEMPLATE_POOL, bootStrap -> {
            HolderGetter<StructureTemplatePool> pools = bootStrap.lookup(Registries.TEMPLATE_POOL);
            Holder<StructureTemplatePool> emptyPool = pools.getOrThrow(Pools.EMPTY);
            bootStrap.register(ORE_CHUNK_PORTAL, new StructureTemplatePool(emptyPool, ImmutableList.of(Pair.of(
                    StructurePoolElement.single(DCtimeMod.MODID + ":" + "signal_world_portal"), 1)
            ), StructureTemplatePool.Projection.RIGID));
        });


    }
}
