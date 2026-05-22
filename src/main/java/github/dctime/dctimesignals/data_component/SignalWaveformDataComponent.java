package github.dctime.dctimesignals.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

    public record SignalWaveformDataComponent(String label, List<Double> times, List<Double> values) {
    public static final SignalWaveformDataComponent EMPTY =
            new SignalWaveformDataComponent("", List.of(), List.of());

    static StreamCodec<FriendlyByteBuf, List<Double>> DOUBLE_LIST =
            new StreamCodec<>() {

                @Override
                public List<Double> decode(FriendlyByteBuf buf) {
                    int size = buf.readVarInt();
                    List<Double> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(buf.readDouble());
                    }
                    return list;
                }

                @Override
                public void encode(FriendlyByteBuf buf, List<Double> value) {
                    buf.writeVarInt(value.size());
                    for (double d : value) {
                        buf.writeDouble(d);
                    }
                }
            };

    // -----------------------------------------------------------------------
    // Codec（存檔用）
    // -----------------------------------------------------------------------

    public static final Codec<SignalWaveformDataComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("label").forGetter(SignalWaveformDataComponent::label),
                    Codec.DOUBLE.listOf().fieldOf("times").forGetter(SignalWaveformDataComponent::times),
                    Codec.DOUBLE.listOf().fieldOf("values").forGetter(SignalWaveformDataComponent::values)
            ).apply(instance, SignalWaveformDataComponent::new)
    );

    // -----------------------------------------------------------------------
    // StreamCodec（網路傳輸用）
    // -----------------------------------------------------------------------

    public static final StreamCodec<FriendlyByteBuf, SignalWaveformDataComponent> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,  SignalWaveformDataComponent::label,
                    DOUBLE_LIST, SignalWaveformDataComponent::times,
                    DOUBLE_LIST, SignalWaveformDataComponent::values,
                    SignalWaveformDataComponent::new
            );

    // -----------------------------------------------------------------------
    // 工具方法
    // -----------------------------------------------------------------------

    public boolean isEmpty() {
        return times.isEmpty() || values.isEmpty();
    }

    /** 方便轉成 double[] 給渲染用 */
    public double[] timesArray() {
        return times.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double[] valuesArray() {
        return values.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
