package github.dctime.dctimesignals.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record SignalPickaxeHudDataComponent(boolean found, String oreName, int x, int y, int z, int maxDistance) {

    @Override
    public int hashCode() {
        return Objects.hash(this.found, this.oreName, this.x, this.y, this.z, this.maxDistance);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof SignalPickaxeHudDataComponent(boolean found, String oreName, int x, int y, int z, int maxDistance)
                    && this.found == found
                    && this.oreName.equals(oreName)
                    && this.x == x
                    && this.y == y
                    && this.z == z
                    && this.maxDistance == maxDistance;
        }
    }

    public static final Codec<SignalPickaxeHudDataComponent> BASIC_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("found").forGetter(SignalPickaxeHudDataComponent::found),
                    Codec.STRING.fieldOf("oreName").forGetter(SignalPickaxeHudDataComponent::oreName),
                    Codec.INT.fieldOf("x").forGetter(SignalPickaxeHudDataComponent::x),
                    Codec.INT.fieldOf("y").forGetter(SignalPickaxeHudDataComponent::y),
                    Codec.INT.fieldOf("z").forGetter(SignalPickaxeHudDataComponent::z),
                    Codec.INT.fieldOf("maxDistance").forGetter(SignalPickaxeHudDataComponent::maxDistance)
            ).apply(instance, SignalPickaxeHudDataComponent::new)
    );
    public static final StreamCodec<ByteBuf, SignalPickaxeHudDataComponent> BASIC_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SignalPickaxeHudDataComponent::found,
            ByteBufCodecs.STRING_UTF8, SignalPickaxeHudDataComponent::oreName,
            ByteBufCodecs.INT, SignalPickaxeHudDataComponent::x,
            ByteBufCodecs.INT, SignalPickaxeHudDataComponent::y,
            ByteBufCodecs.INT, SignalPickaxeHudDataComponent::z,
            ByteBufCodecs.INT, SignalPickaxeHudDataComponent::maxDistance,
            SignalPickaxeHudDataComponent::new
    );

    // Unit stream codec if nothing should be sent across the network
    public static final StreamCodec<ByteBuf, SignalPickaxeHudDataComponent> UNIT_STREAM_CODEC = StreamCodec.unit(new SignalPickaxeHudDataComponent(false, "", 0, 0, 0, 0));


}
