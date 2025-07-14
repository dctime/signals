package github.dctime.dctimesignals.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.dctime.dctimesignals.item.SignalPickaxe;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record SignalPickaxeDataComponent(boolean hasGroundEmitter, BlockPos groundPenSignalEmitterPosition, Integer mode) {
    public static final Integer ACTIVE_MODE = 0;
    public static final Integer PASSIVE_MODE = 1;
    public boolean isPassiveMode() {
        return mode.intValue() == PASSIVE_MODE.intValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.groundPenSignalEmitterPosition, this.mode);
    }

    public BlockPos groundPenSignalEmitterPosition() {
        return this.groundPenSignalEmitterPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof SignalPickaxeDataComponent(boolean hasGroundEmitter, BlockPos penSignalEmitterPosition, Integer mode1)
                    && this.hasGroundEmitter == hasGroundEmitter
                    && this.groundPenSignalEmitterPosition.getX() == penSignalEmitterPosition.getX()
                    && this.groundPenSignalEmitterPosition.getY() == penSignalEmitterPosition.getY()
                    && this.groundPenSignalEmitterPosition.getZ() == penSignalEmitterPosition.getZ()
                    && this.mode.intValue() == mode1.intValue();
        }
    }

    public static final Codec<SignalPickaxeDataComponent> BASIC_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("hasGroundEmitter").forGetter(SignalPickaxeDataComponent::hasGroundEmitter),
                    BlockPos.CODEC.fieldOf("groundPenSignalEmitterPosition").forGetter(SignalPickaxeDataComponent::groundPenSignalEmitterPosition),
                    Codec.INT.fieldOf("mode").forGetter(SignalPickaxeDataComponent::mode)
            ).apply(instance, SignalPickaxeDataComponent::new)
    );
    public static final StreamCodec<ByteBuf, SignalPickaxeDataComponent> BASIC_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SignalPickaxeDataComponent::hasGroundEmitter,
            BlockPos.STREAM_CODEC, SignalPickaxeDataComponent::groundPenSignalEmitterPosition,
            ByteBufCodecs.INT, SignalPickaxeDataComponent::mode,
            SignalPickaxeDataComponent::new
    );

    // Unit stream codec if nothing should be sent across the network
    public static final StreamCodec<ByteBuf, SignalPickaxeDataComponent> UNIT_STREAM_CODEC = StreamCodec.unit(new SignalPickaxeDataComponent(false, new BlockPos(0, 0, 0), SignalPickaxeDataComponent.ACTIVE_MODE));


}
