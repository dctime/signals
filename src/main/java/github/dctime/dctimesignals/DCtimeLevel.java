package github.dctime.dctimesignals;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class DCtimeLevel {
    public static final ResourceKey<Level> SIGNAL_WORLD = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_world"));
}
