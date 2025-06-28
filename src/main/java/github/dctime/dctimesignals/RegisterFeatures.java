package github.dctime.dctimesignals;

import github.dctime.dctimesignals.configuration.SignalBlockingMaterialChunkConfiguration;
import github.dctime.dctimesignals.feature.SignalBlockingMaterialChunkFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, DCtimeMod.MODID);
    public static final DeferredHolder<Feature<?>, SignalBlockingMaterialChunkFeature> SIGNAL_BLOCKING_MATERIAL_CHUNK =
            FEATURES.register("signal_blocking_material_chunk", () -> new SignalBlockingMaterialChunkFeature(SignalBlockingMaterialChunkConfiguration.CODEC));

}
