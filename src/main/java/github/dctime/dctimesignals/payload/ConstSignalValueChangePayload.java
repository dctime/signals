package github.dctime.dctimesignals.payload;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.block.ConstSignalBlock;
import github.dctime.dctimesignals.block.ConstSignalBlockEntity;
import github.dctime.dctimesignals.menu.ConstSignalMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ConstSignalValueChangePayload(int signalValue) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ConstSignalValueChangePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "const_signal_value_change"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, ConstSignalValueChangePayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT,
        ConstSignalValueChangePayload::signalValue,
        ConstSignalValueChangePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataInServer(final ConstSignalValueChangePayload data, final IPayloadContext context) {
        System.out.println("Calling Server to do something");
        if (context.player().containerMenu instanceof ConstSignalMenu menu) {
            menu.getData().set(ConstSignalBlockEntity.OUTPUT_SIGNAL_VALUE_INDEX, data.signalValue());
            ConstSignalBlockEntity entity = menu.getBlockEntity();
            if (entity.getBlockState().getBlock() instanceof ConstSignalBlock block) {
                // forcefully: changing signal value from high to low needs to forcefully rewrite if the wire keeps the signal the signal block's signal
                block.updateFromGui(entity, data.signalValue());
                System.out.println("Updating Signal Wire cuz ConstSignalValue changed");
            }
        }
    }

    public static void handleDataInClient(final ConstSignalValueChangePayload data, final IPayloadContext context) {
        System.out.println("Calling Client to do something Const Value Change");
    }

}
