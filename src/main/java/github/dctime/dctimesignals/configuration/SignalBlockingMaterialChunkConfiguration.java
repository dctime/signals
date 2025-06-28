package github.dctime.dctimesignals.configuration;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SignalBlockingMaterialChunkConfiguration implements FeatureConfiguration {
    public static final SignalBlockingMaterialChunkConfiguration INSTANCE = new SignalBlockingMaterialChunkConfiguration();
    public static final Codec<SignalBlockingMaterialChunkConfiguration> CODEC = Codec.unit(() -> INSTANCE);
}
